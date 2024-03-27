package com.example.youtube.repository;


import com.example.youtube.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVideoRepository extends JpaRepository<Video, Long> {
    Iterable<Video> findAllByTitleContaining(String title);

}
