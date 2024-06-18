package com.example.veb_projekat.resourse;

import com.example.veb_projekat.entities.Tag;
import com.example.veb_projekat.service.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tags")
public class TagResourse {
    @Inject
    private TagService tagService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> findAll(){ return tagService.findAll(); }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag insert(@Valid Tag tag){ return tagService.insert(tag); }

    @GET
    @Path("/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag findByKeyword(@PathParam("keyword") String keyword){ return tagService.findByKeyword(keyword); }

}
