package com.example.veb_projekat.repository;

import com.example.veb_projekat.entities.Tag;

import java.util.List;

public interface TagRepository {
    Tag insert(Tag tag);
    Tag findByKeyword(String keyword);
    List<Tag> getAll();
}
