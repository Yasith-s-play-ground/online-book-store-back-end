package com.backend.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bookstore.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
