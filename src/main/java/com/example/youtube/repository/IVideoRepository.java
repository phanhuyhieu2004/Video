package com.example.youtube.repository;


import com.example.youtube.VideoSpec;
import com.example.youtube.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IVideoRepository extends JpaRepository<Video, Long> , JpaSpecificationExecutor<Video> {
    Iterable<Video> findAllByTitleContaining(String title);



}
