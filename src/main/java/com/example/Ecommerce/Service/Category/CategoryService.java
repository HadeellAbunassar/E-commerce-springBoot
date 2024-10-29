package com.example.Ecommerce.Service.Category;

import com.example.Ecommerce.Entities.Category;
import java.util.List;


public interface CategoryService {

    Category saveCategory(Category category);

    List<Category> fetchCategoryList();

    void deleteCategoryById(Long categoryId);

    public Category updateCategory(Category category,Long id);

    Category getById(Long categoryId);

}
