package com.example.veb_projekat.service;

import com.example.veb_projekat.entities.Category;
import com.example.veb_projekat.repository.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {
    @Inject
    CategoryRepository categoryRepository;

    public List<Category> getAll(Integer page){ return categoryRepository.getAll(page); }
    public Category getById(Integer id){ return categoryRepository.getById(id); }
    public Category insert(Category category){return categoryRepository.insert(category); }
    public Category update(Category category) { return categoryRepository.update(category); }
    public Category delete(Integer id) { return categoryRepository.delete(id); }
}
