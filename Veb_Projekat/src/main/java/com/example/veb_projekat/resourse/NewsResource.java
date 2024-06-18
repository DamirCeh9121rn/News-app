package com.example.veb_projekat.resourse;

import com.example.veb_projekat.entities.News;
import com.example.veb_projekat.service.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/news")
public class NewsResource {

    @Inject
    private NewsService newsService;

    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    public News create(@Valid News news){ return newsService.insert(news); }

    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News update(@PathParam("id") Integer id, News news){
        news.setId(id);
        return newsService.update(news);
    }

    @PUT
    @Path("/like/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void like(@PathParam("id") Integer id){
        System.out.println("usao u like");
         newsService.addLike(id);
    }

    @PUT
    @Path("/dislike/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") Integer id){
        newsService.addDislike(id);
    }

    @PUT
    @Path("/visited/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public News visited(@PathParam("newsId") Integer newsId){ return newsService.newsVisited(newsId); }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News delete(@PathParam("id") Integer id){ return newsService.delete(id); }

    @GET
    @Path("/most-read")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getMostRead(){ return newsService.mostRead(); }

    @GET
    @Path("/by-category/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getByCategoryName(@PathParam("categoryId") Integer categoryId, @QueryParam("page") Integer page){ return newsService.findAllByCategory(categoryId, page); }

    @GET
    @Path("/by-tag/{tagName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getByTagName(@PathParam("tagName") String tagName, @QueryParam("page") int page){ return newsService.findAllByTag(tagName, page); }

    @GET
    @Path("/by-word/{word}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getByWord(@PathParam("word") String word, @QueryParam("page") int page){ return newsService.findNewsByWord(word, page); }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findById(@PathParam("id") Integer id) { return newsService.findById(id); }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> findAll(@QueryParam("page") int page){ return newsService.findAll(page); }

}
