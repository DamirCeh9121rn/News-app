package com.example.veb_projekat.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class News {
    private Integer id;
    @NotNull(message = "categoryName field is required")
    @NotEmpty(message = "categoryName field is required")
    private Integer categoryId;
    @NotNull(message = "title field is required")
    @NotEmpty(message = "title field is required")
    private String title;
    @NotNull(message = "content field is required")
    @NotEmpty(message = "content field is required")
    private String content;
    @NotNull(message = "author field is required")
    @NotEmpty(message = "author field is required")
    private String author;

    private int like;
    private int dislike;
    private Long createdAt;
    private int visits;
    private String tags;
    //private List<Tag> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();


    public News() { }
    public News(Integer id) { this.id = id; }

    public News(Integer id, Integer categoryId, String title, String content, String author, Long createdAt, String tags) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.tags = tags;
    }

    public News(Integer id, Integer categoryId, String title, String content, String author, Long createdAt, int visits) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.visits = visits;
    }

    public News(Integer id, Integer categoryId, String title, String content, String author, int like, int dislike, Long createdAt, int visits, String tags) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.like = like;
        this.dislike = dislike;
        this.createdAt = createdAt;
        this.visits = visits;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

//    public String getTags() {
//        return tags;
//    }
//
//    public void setTags(String tags) {
//        this.tags = tags;
//    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createdAt=" + createdAt +
                ", visits=" + visits +
                ", tags='" + tags + '\'' +
                ", comments=" + comments +
                '}';
    }

    private Long getLocalDateTimeLong(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
}