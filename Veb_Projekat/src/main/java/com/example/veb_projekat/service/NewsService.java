package com.example.veb_projekat.service;

import com.example.veb_projekat.entities.News;
import com.example.veb_projekat.repository.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {
    @Inject
    private NewsRepository newsRepository;


    public News insert(News news){ return newsRepository.insert(news); }
    public News update(News news){ return newsRepository.update(news); }
    public News newsVisited(Integer id){ return newsRepository.newsVisited(id); }
    public void addLike(Integer id){ newsRepository.addNewsLike(id);}
    public void addDislike(Integer id){ newsRepository.addNewsDisike(id);}
    public News delete(Integer id){ return newsRepository.delete(id); }
    public List<News> mostRead(){ return newsRepository.mostRead(); }
    public List<News> findAllByCategory(Integer categoryId, Integer page){ return newsRepository.findAllByCategory(categoryId, page); }
    public List<News> findAllByTag(String tagName, Integer page){ return newsRepository.findAllByTag(tagName, page); }
    public News findById(Integer id) { return newsRepository.findById(id); }
    public List<News> findNewsByWord(String word, Integer page){ return newsRepository.findNewsByWord(word, page);}
    public List<News> findAll(int page){ return newsRepository.findAll(page); }
}
