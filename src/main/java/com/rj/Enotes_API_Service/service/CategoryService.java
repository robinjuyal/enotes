package com.rj.Enotes_API_Service.service;


import java.util.List;


import com.rj.Enotes_API_Service.entity.Category;

public interface CategoryService {

    public Boolean saveCategory(Category category);
    public List<Category>getAllCategory();

    
}
