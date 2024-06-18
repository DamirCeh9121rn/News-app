package com.example.veb_projekat.repository;

import com.example.veb_projekat.entities.NewsTag;

import java.util.List;

public interface NewsTagRepository {

    NewsTag insert(NewsTag newsTag);
    void deleteByNewsId(Integer newsId);
    List<NewsTag> findAllByNewsId(Integer newsId);
    List<NewsTag> findAllByTagId(Integer tagId);
}
