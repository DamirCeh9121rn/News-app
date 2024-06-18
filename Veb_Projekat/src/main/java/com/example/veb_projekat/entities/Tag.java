package com.example.veb_projekat.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Tag {
    private Integer id;
    @NotNull(message = "keyword field is required")
    @NotEmpty(message = "keyword field is required")
    private String keyword;

    public Tag() {  }

    public Tag(String keyword) {
        this.keyword = keyword;
    }
    public Tag(Integer id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
