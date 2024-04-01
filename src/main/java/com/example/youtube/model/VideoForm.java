package com.example.youtube.model;

import org.springframework.web.multipart.MultipartFile;

public class VideoForm {
    private Long video_id;
    private String title;
    private String description;
//    đại diện cho tệp dữ liệu mà máy chủ đã nhận được từ biểu mẫu HTML.
    private MultipartFile thumbnail;

    private MultipartFile url;
    private String upload_date;
    private Category category;
private Account account;
    public VideoForm() {
    }

    public VideoForm(Long video_id, String title, String description, MultipartFile thumbnail, MultipartFile url, String upload_date, Category category, Account account) {
        this.video_id = video_id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.url = url;
        this.upload_date = upload_date;
        this.category = category;
        this.account = account;
    }

    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Long video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public MultipartFile getUrl() {
        return url;
    }

    public void setUrl(MultipartFile url) {
        this.url = url;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
