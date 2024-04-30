package com.backend.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.bookstore.entity.Product;

@Service
public interface ProductService {

    Product createProduct(Product product);

    Product updateProduct(Product product, Long id);

    void deleteProduct(Long id);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> findByTitle(String title);

    List<Product> findByAuthor(String author);

}
