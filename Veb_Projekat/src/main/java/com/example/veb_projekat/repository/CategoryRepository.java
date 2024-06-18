package com.example.veb_projekat.repository;

import com.example.veb_projekat.entities.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAll(Integer page);
    Category getById(Integer id);
    Category insert(Category category);
    Category update(Category category);
    Category delete(Integer id);
}
