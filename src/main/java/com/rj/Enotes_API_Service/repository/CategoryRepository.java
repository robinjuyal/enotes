package com.rj.Enotes_API_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rj.Enotes_API_Service.entity.Category;

public interface CategoryRepository extends JpaRepository<Category , Integer> {

    List<Category> findByIsActiveTrue();

    
}
