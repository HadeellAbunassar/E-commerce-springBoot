package com.example.Ecommerce.Controllers.AdminPanelController;

import com.example.Ecommerce.Entities.Category;
import com.example.Ecommerce.Service.Category.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @PostMapping()
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
        return new ResponseEntity<>("Saving product done successfully",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){

        return new ResponseEntity<>(categoryService.fetchCategoryList(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category,
                                                   @PathVariable("id") long id){

        return new ResponseEntity<>(categoryService.updateCategory(category,id) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Category deleted successfully",HttpStatus.OK);
    }
}
