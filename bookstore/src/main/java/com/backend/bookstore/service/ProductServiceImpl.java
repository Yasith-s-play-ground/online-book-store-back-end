package com.backend.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.bookstore.entity.Product;
import com.backend.bookstore.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {

        Product existingProduct = getProductById(id);

        if (existingProduct != null) {

            existingProduct.setTitle(product.getTitle());
            existingProduct.setAuthor(product.getAuthor());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());

            productRepository.save(existingProduct);
            return existingProduct;

        } else {
            return null;
        }
    }

    @Override
    public void deleteProduct(Long id) {

        productRepository.findById(id).orElse(null);
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);

    }

    @Override
    public List<Product> findByTitle(String title) {

        Optional<List<Product>> products = productRepository.findByTitleContaining(title);

        if (products.isEmpty()) {

            String message = "Book with " + title + " not found ";

        }

        return products.get();
    }

    @Override
    public List<Product> findByAuthor(String author) {

        Optional<List<Product>> products = productRepository.findByAuthorContaining(author);

        if (products.isEmpty()) {

            String message = "Book with " + author + " not found ";

        }

        return products.get();
    }

}
