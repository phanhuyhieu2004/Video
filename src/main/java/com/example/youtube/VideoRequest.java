package com.example.youtube;

import com.example.youtube.model.Category;

public class VideoRequest {
    private String title;

    private String upload_date;

private Long category;


    public VideoRequest(String title, String upload_date, Long category) {
        this.title = title;
        this.upload_date = upload_date;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
