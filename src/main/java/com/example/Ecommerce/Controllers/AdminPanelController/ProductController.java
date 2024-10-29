package com.example.Ecommerce.Controllers.AdminPanelController;

import com.example.Ecommerce.Entities.Category;
import com.example.Ecommerce.Entities.Product;
import com.example.Ecommerce.Service.Category.CategoryServiceImpl;
import com.example.Ecommerce.Service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryServiceImpl categoryService;

    private static final String IMAGE_UPLOAD_DIR = "src/main/resources/static/imgs/";


    @Autowired
    public ProductController(ProductService productService,CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "imageFile") MultipartFile imageFile) throws IOException {

        Category category = categoryService.getById(categoryId);
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);

            String fileName = saveImage(imageFile);
            product.setImageUrl("/imgs/" + fileName);


        Product createdProduct = productService.saveProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "imageFile") MultipartFile imageFile) throws IOException {

        Category category = categoryService.getById(categoryId);
        Product existingProduct = productService.findById(id);

        existingProduct.setName(name);
        existingProduct.setDescription(description);
        existingProduct.setPrice(price);
        existingProduct.setQuantity(quantity);
        existingProduct.setCategory(category);


            String fileName = saveImage(imageFile);
            existingProduct.setImageUrl("/imgs/" + fileName);


        Product updatedProduct = productService.saveProduct(existingProduct);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        String fileName = imageFile.getOriginalFilename();
        Path filePath = Paths.get(IMAGE_UPLOAD_DIR + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageFile.getBytes());
        return fileName;
    }
}
