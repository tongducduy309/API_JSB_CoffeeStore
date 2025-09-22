package com.api_coffee_store.api_coffee_store.mapper;

import com.api_coffee_store.api_coffee_store.dtos.request.CategoryRequest;
import com.api_coffee_store.api_coffee_store.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest categoryRequest);
}
