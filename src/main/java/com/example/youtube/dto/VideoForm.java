package com.example.youtube.dto;

import com.example.youtube.model.Account;
import com.example.youtube.model.Category;
import com.example.youtube.model.Video;
import com.example.youtube.service.VideoService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

public class VideoForm {
    private Long video_id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
//    đại diện cho tệp dữ liệu mà máy chủ đã nhận được từ biểu mẫu HTML.
@NotEmpty
    private MultipartFile thumbnail;
    @NotEmpty
    private MultipartFile url;
    private String upload_date;

    private Category
            category;

private Account account;


    public VideoForm() {
    }


    public Long getVideo_id() {
        return video_id;
    }

    public VideoForm setVideo_id(Long video_id) {
        this.video_id = video_id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public VideoForm setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public VideoForm setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public VideoForm setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public MultipartFile getUrl() {
        return url;
    }

    public VideoForm setUrl(MultipartFile url) {
        this.url = url;
        return this;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public VideoForm setUpload_date(String upload_date) {
        this.upload_date = upload_date;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public VideoForm setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public VideoForm setAccount(Account account) {
        this.account = account;
        return this;
    }

}
