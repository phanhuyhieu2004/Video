package com.example.youtube.controller;

import com.example.youtube.PaginateRequest;
import com.example.youtube.VideoRequest;
import com.example.youtube.model.Category;
import com.example.youtube.model.Video;


import com.example.youtube.model.VideoForm;
import com.example.youtube.service.CategoryService;
import com.example.youtube.service.VideoService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Optional;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private CategoryService categoryService;
    @Value("${file-upload}")
    private String fileUpload;

    @ModelAttribute("categorys")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/videos")

    public ModelAndView ShowList(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                 @RequestParam(name = "size", defaultValue = "5", required = false) int size,
                                 @RequestParam(name = "title", required = false) String title,
                                 @RequestParam(name = "upload_date", required = false) String uploadDate,
                                 @RequestParam(name = "category", required = false) Long category) {
        Page<Video> videoPage = videoService.findAll(new PaginateRequest(page, size), new VideoRequest(title, uploadDate, category));

        ModelAndView modelAndView = new ModelAndView("lists");
        modelAndView.addObject("videos", videoPage.getContent());
        modelAndView.addObject("videoForm", new VideoForm());
        modelAndView.addObject("number", page);
        modelAndView.addObject("keyWord", title);
        modelAndView.addObject("totalPage", videoPage.getTotalPages());
        modelAndView.addObject("hasPrevious", page > 0);
        modelAndView.addObject("hasNext", page < videoPage.getTotalPages() - 1);
        return modelAndView;
    }


    @PostMapping("/create-video")
    public ModelAndView createVideo(@ModelAttribute VideoForm videoForm) throws Exception {

        ModelAndView modelAndView = new ModelAndView("redirect:videos");
        MultipartFile multipartFile = videoForm.getUrl();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(videoForm.getUrl().getBytes(), new File(fileUpload + fileName));
//        Sao chép nội dung của  video  thư mục  với tên được trích xuất từ tệp gốc
        MultipartFile multipartFiles = videoForm.getThumbnail();
        String fileNames = multipartFiles.getOriginalFilename();
        FileCopyUtils.copy(videoForm.getThumbnail().getBytes(), new File(fileUpload + fileNames));
        Video video = new Video(videoForm.getVideo_id(), videoForm.getTitle(), videoForm.getDescription(), fileNames, fileName, videoForm.getUpload_date(), videoForm.getCategory(), videoForm.getAccount());
        videoService.save(video);
        return modelAndView;
    }


    @GetMapping("/edit-video/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id) {
        Optional<Video> video = videoService.findById(id);


        ModelAndView modelAndView = new ModelAndView("/edits");
        modelAndView.addObject("video", video.get());
        return modelAndView;
    }

    @PostMapping("/edit-video")
    public ModelAndView updateVideo(@ModelAttribute("video") Video video) {
        videoService.save(video);

        ModelAndView modelAndView = new ModelAndView("/edits");
        modelAndView.addObject("video", video);
        modelAndView.addObject("message", "Category updated thành công");
        return modelAndView;
    }

    @GetMapping("/delete-video/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Video> video = videoService.findById(id);


        ModelAndView modelAndView = new ModelAndView("/deletes");
        modelAndView.addObject("video", video.get());
        return modelAndView;
    }

    @PostMapping("/delete-video")
    public String deleteVideo(@ModelAttribute("video") Video video) {
        videoService.remove(video.getVideo_id());

        return "redirect:/videos";
    }

    @GetMapping("/download-video/{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) throws Exception {
        Optional<Video> video = videoService.findById(id);
//        lấy đường dẫn của file
        String filePath = fileUpload + video.get().getUrl();
        File file = new File(filePath);
//*----------------------------Multipurpose Internet Mail Extensions) của phản hồi HTTP
//         thiết lập loại dữ liệu mà trình duyệt web sẽ nhận được từ máy chủ.
//         octet-stream" đề cập đến việc dữ liệu được truyền dưới dạng các byte mà không có định dạng cụ thể..
        response.setContentType("application/octet-stream");
//        "Content-Disposition" là một tiêu đề HTTP được sử dụng để định rõ cách trình duyệt web sẽ xử lý dữ liệu nhận được từ máy chủ.
//        thiết lập cho trình duyệt web hiển thị một hộp thoại tải xuống, và tên của tệp tải xuống sẽ là tên được trả về từ phương thức .getUrl()
        response.setHeader("Content-Disposition", "attachment;filename=\"" + video.get().getUrl());
//        cách để thông báo cho trình duyệt web về kích thước của nội dung dữ liệu mà máy chủ sẽ gửi
//        chuyển về kiểu int
        response.setContentLength((int) file.length());
//            Mở một luồng đầu vào (input stream) để đọc dữ liệu từ tệp tin file.

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
//            Sao chép dữ liệu từ luồng đầu vào này vào luồng đầu ra (output stream) của phản hồi HTTP, nghĩa là gửi dữ liệu từ tệp tin đến trình duyệt của người dùng.
            response.flushBuffer();
        }
    }
}