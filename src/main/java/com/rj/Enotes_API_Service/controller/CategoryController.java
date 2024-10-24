package com.rj.Enotes_API_Service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rj.Enotes_API_Service.entity.Category;
import com.rj.Enotes_API_Service.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?>saveCategory(@RequestBody  Category category ){
        Boolean saveCategory =categoryService.saveCategory(category);
        if(saveCategory){
            return new ResponseEntity<>("saved success", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }
    
    @GetMapping("/category")
    public ResponseEntity<?>getAllCategory(){
        List<Category>allCategories=categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategories)){
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(allCategories,HttpStatus.OK);
        }
    }
}
