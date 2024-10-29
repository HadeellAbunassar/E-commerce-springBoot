package com.example.Ecommerce.Service.product;

import com.example.Ecommerce.Entities.Product;

import java.util.List;

public interface ProductService {

     List<Product> findAll();

     Product saveProduct(Product product);

     Product findById(Long id);

     void deleteProduct(Long id);

     Product updateProduct(Product product, Long productId);

}
