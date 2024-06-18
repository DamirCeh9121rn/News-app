package com.example.veb_projekat.repository;

import com.example.veb_projekat.entities.News;

import java.util.List;

public interface NewsRepository {

    News insert(News news);
    News update(News news);
    News newsVisited(Integer newsId);
    void addNewsLike(Integer newsId);
    void addNewsDisike(Integer newsId);
    News delete(Integer id);
    List<News> mostRead();
    List<News> findAllByCategory(Integer categoryId, Integer page);
    List<News> findAllByTag(String tagName, Integer page);
    News findById(Integer id);
    List<News> findNewsByWord(String word, Integer page);
    List<News> findAll(int page);
}
