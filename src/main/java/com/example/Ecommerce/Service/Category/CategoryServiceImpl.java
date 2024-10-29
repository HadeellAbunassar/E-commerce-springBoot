package com.example.Ecommerce.Service.Category;
import com.example.Ecommerce.Entities.Category;
import com.example.Ecommerce.ExceptionHandling.CustomException.CategoryNotFoundException;
import com.example.Ecommerce.Repositries.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> fetchCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
     categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category updateCategory(Category category,Long id) {
       Category catDB = categoryRepository.findById(id).get();
       catDB.setName(category.getName());
      return categoryRepository.save(catDB);
    }

    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found exception"));
    }

}
