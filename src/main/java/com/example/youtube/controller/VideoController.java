package com.example.youtube.controller;

import com.example.youtube.dto.PaginateRequest;
import com.example.youtube.dto.VideoRequest;
import com.example.youtube.model.Category;
import com.example.youtube.model.Video;


import com.example.youtube.dto.VideoForm;
import com.example.youtube.service.CategoryService;
import com.example.youtube.service.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                                 @RequestParam(name = "category", required = false) String category

     ) {
        Page<Video> videoPage = videoService.findAll(  new VideoRequest(title, uploadDate, category),new PaginateRequest(page, size));

        ModelAndView modelAndView = new ModelAndView("list1");
        modelAndView.addObject("pageBegin", Math.max(1, page));
        modelAndView.addObject("pageEnd", Math.min(page + 2, videoPage.getTotalPages()));

        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("size", size);
        modelAndView.addObject("videos", videoPage.getContent());


        modelAndView.addObject("totalPages", videoPage.getTotalPages());

        return modelAndView;
    }
    @GetMapping("/create-video")
    public String createForm(Model model) {
        model.addAttribute("videoForm", new VideoForm());
        return "add";
    }


    @PostMapping("/create-video")
    public String createVideo(@ModelAttribute VideoForm videoForm,RedirectAttributes redirectAttributes,Model model) throws IOException {



           MultipartFile multipartFile = videoForm.getUrl();
           String fileName = multipartFile.getOriginalFilename();
           FileCopyUtils.copy(videoForm.getUrl().getBytes(), new File(fileUpload + fileName));
//        Sao chép nội dung của  video  thư mục  với tên được trích xuất từ tệp gốc
           MultipartFile multipartFiles = videoForm.getThumbnail();
           String fileNames = multipartFiles.getOriginalFilename();
           FileCopyUtils.copy(videoForm.getThumbnail().getBytes(), new File(fileUpload + fileNames));
           Video video = new Video(videoForm.getVideo_id(), videoForm.getTitle(), videoForm.getDescription(), fileNames, fileName, videoForm.getUpload_date(), videoForm.getCategory(), videoForm.getAccount());
           videoService.save(video);
        model.addAttribute("videoForm", videoForm);
        redirectAttributes.addFlashAttribute("msg", "Thêm thành công");

        return "redirect:/videos";




    }


    @GetMapping("/edit-video/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id) {
        Optional<Video> video = videoService.findById(id);


        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("video", video.get());
        return modelAndView;
    }

    @PostMapping("/edit-video")
    public String updateVideo(@ModelAttribute("video") Video video, RedirectAttributes redirectAttributes,Model model) {
        videoService.save(video);


        model.addAttribute("video", video);
        redirectAttributes.addFlashAttribute("msg", "Sửa thành công");

        return "redirect:/videos";

    }


    @GetMapping("/delete-video/{id}")
    public String deleteVideo(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        Optional<Video> video = videoService.findById(id);
        videoService.remove(video.get().getVideo_id());
        redirectAttributes.addFlashAttribute("msg", "Xóa thành công");

        return "redirect:/videos";
    }

    @GetMapping("/download-video/{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) throws Exception {
        Optional<Video> video = videoService.findById(id);
//        lấy đường dẫn của file
        String filePath = fileUpload + video.get().getUrl();
        File file = new File(filePath);
//*Multipurpose Internet Mail Extensions) của phản hồi HTTP
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