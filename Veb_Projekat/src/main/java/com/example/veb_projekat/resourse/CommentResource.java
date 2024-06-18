package com.example.veb_projekat.resourse;

import com.example.veb_projekat.entities.Comment;
import com.example.veb_projekat.service.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment comment){
        System.out.println(comment);
        return commentService.insert(comment);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment update(@PathParam("id") Integer id, Comment comment){
        comment.setId(id);
        return commentService.update(comment);
    }
    @PUT
    @Path("/like/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addLike(@PathParam("id") Integer id){
         commentService.like(id);
    }
    @PUT
    @Path("/dislike/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addDislike(@PathParam("id") Integer id){
         commentService.dislike(id);
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment delete(@PathParam("id") Integer id){ return commentService.delete(id); }

    @GET
    @Path("/by-news/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getAllByNewsId(@PathParam("newsId") Integer newsId){ return commentService.findAllByNewsId(newsId); }
}
