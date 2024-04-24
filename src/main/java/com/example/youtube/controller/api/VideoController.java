package com.example.youtube.controller.api;

import com.example.youtube.dto.PaginateRequest;
import com.example.youtube.dto.VideoForm;
import com.example.youtube.dto.VideoRequest;
import com.example.youtube.model.Video;
import com.example.youtube.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("apiStudentController")
@RequestMapping("/api/v1")
@CrossOrigin(origins="*")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @GetMapping("")
    public ResponseEntity<Iterable<Video>> findAll(@RequestParam(required = false) String title) {
        List<Video> videos;
        if (title != null && !title.isEmpty()) {
            videos = (List<Video>) videoService.findAllByTitleContaining(title);
        } else {
            videos = (List<Video>) videoService.findAll();
        }
        if (videos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Video> findVideoById(@PathVariable Long id) {
        Optional<Video> videoOptional = videoService.findById(id);
        if (!videoOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(videoOptional.get(), HttpStatus.OK);
    }




}
