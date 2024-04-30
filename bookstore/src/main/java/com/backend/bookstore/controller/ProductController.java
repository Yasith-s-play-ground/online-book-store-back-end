package com.backend.bookstore.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bookstore.entity.Product;
import com.backend.bookstore.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {

         try {

            List<Product> getProducts = productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(getProducts);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PreAuthorize("hasAuthority('ADMIN','USER')")
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {

        try {

            Product product = productService.getProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(product);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/product/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        try {

            Product productCreated = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {

        try {

            Product updateProduct = productService.updateProduct(product, id);
            return ResponseEntity.status(HttpStatus.OK).body(updateProduct);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        try {

            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/products/{title}")
    public ResponseEntity<List<Product>> getByTitle(@PathVariable(name = "title") String title) {

        List<Product> products = productService.findByTitle(title);
        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.add(" Message ", " Books found successfully! ");
        ResponseEntity<List<Product>> response = new ResponseEntity<>(products, headers, status);
        return response;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/products/{author}")
    public ResponseEntity<List<Product>> getByAuthor(@PathVariable(name = "author") String author) {

        List<Product> products = productService.findByAuthor(author);
        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.add(" Message ", " Books found successfully! ");
        ResponseEntity<List<Product>> response = new ResponseEntity<>(products, headers, status);
        return response;
    }

}
