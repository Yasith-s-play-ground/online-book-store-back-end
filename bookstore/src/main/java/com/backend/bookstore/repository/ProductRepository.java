package com.backend.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bookstore.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findByTitleContaining(String title);

    Optional<List<Product>> findByAuthorContaining(String author);

}
