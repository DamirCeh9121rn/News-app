package com.example.veb_projekat.service;

import com.example.veb_projekat.entities.Tag;
import com.example.veb_projekat.repository.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {
    @Inject
    private TagRepository tagRepository;

    public TagService(){

    }

    public Tag insert(Tag tag){ return tagRepository.insert(tag); }

    public Tag findByKeyword(String keyword){ return tagRepository.findByKeyword(keyword); }

    public List<Tag> findAll(){ return tagRepository.getAll(); }
}
