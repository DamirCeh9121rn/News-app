package com.example.veb_projekat.entities;

public class NewsTag {

    private Integer id;
    private Integer newsId;
    private Integer tagId;

    public NewsTag() {  }

    public NewsTag(Integer newsId, Integer tagId) {
        this.newsId = newsId;
        this.tagId = tagId;
    }

    public NewsTag(Integer id, Integer newsId, Integer tagId) {
        this.id = id;
        this.newsId = newsId;
        this.tagId = tagId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
