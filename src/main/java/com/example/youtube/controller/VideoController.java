package com.example.youtube.controller;

import com.example.youtube.model.Category;
import com.example.youtube.model.Video;


import com.example.youtube.service.CategoryService;
import com.example.youtube.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;
@Autowired
private CategoryService categoryService;
    @ModelAttribute("categorys")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }
//    @GetMapping("/videos")
//    public ModelAndView ShowList() {
//        Iterable<Video> videos = videoService.findAll();
//
//        ModelAndView modelAndView = new ModelAndView("lists");
//        modelAndView.addObject("videos", videos);
//        modelAndView.addObject("video", new Video());
//        return modelAndView;
//    }
    @GetMapping("/videos")
    public ModelAndView searchByTitle(@RequestParam(name = "s", required = false) String searchTerm) {
        Iterable<Video> videos;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            videos = videoService.findAllByTitleContaining(searchTerm);
        } else {
            videos = videoService.findAll();
        }

        ModelAndView modelAndView = new ModelAndView("lists");
        modelAndView.addObject("videos", videos);
        modelAndView.addObject("video", new Video());
        return modelAndView;
    }

    @GetMapping("/create-video")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("lists");
        modelAndView.addObject("video", new Video());
        return modelAndView;
    }
    @PostMapping("/create-video")
    public ModelAndView createVideo(@ModelAttribute("video") Video video) {

        videoService.save(video);
        ModelAndView modelAndView = new ModelAndView("redirect:videos");
        modelAndView.addObject("video", video);
//        modelAndView.addObject("message","thành công rồi");
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
}