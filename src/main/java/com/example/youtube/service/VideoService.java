package com.example.youtube.service;


import com.example.youtube.PaginateRequest;
import com.example.youtube.VideoRequest;
import com.example.youtube.VideoSpec;
import com.example.youtube.model.Video;

import com.example.youtube.repository.IVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class VideoService implements IVideoService{
    @Autowired
    private IVideoRepository iVideoRepository;


    public Page<Video> findAll(PaginateRequest paginateRequest, VideoRequest videoRequest) {
        return iVideoRepository.findAll(new VideoSpec(videoRequest),PageRequest.of(paginateRequest.getPage(), paginateRequest.getSize()));
    }


    @Override
    public Iterable<Video> findAll() {
        return null;
    }

    @Override
    public Optional<Video> findById(Long id) {
        return iVideoRepository.findById(id);
    }

    @Override
    public Video save(Video video) {
        return iVideoRepository.save(video);
    }

    @Override
    public void remove(Long id) {
        iVideoRepository.deleteById(id);
    }
  


}
