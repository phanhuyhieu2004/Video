package com.example.youtube.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "video")
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long video_id;
    private String title;
    private String description;
    private String thumbnail;

    private String url;
    private String upload_date;



    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
@ManyToOne
@JoinColumn(name="account_id")
private Account account;
    public Video() {
    }

    public Video(Long video_id, String title, String description, String thumbnail, String url, String upload_date, Category category) {
        this.video_id = video_id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.url = url;
        this.upload_date = upload_date;
        this.category = category;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
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
}
