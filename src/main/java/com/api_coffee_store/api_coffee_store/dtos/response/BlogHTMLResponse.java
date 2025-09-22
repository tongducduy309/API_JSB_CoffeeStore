package com.api_coffee_store.api_coffee_store.dtos.response;

import com.api_coffee_store.api_coffee_store.models.Category;

import java.time.LocalDateTime;

public record BlogHTMLResponse(
        Long index,
        String title,
        String subtitle,
        LocalDateTime createdAt,
        String content,
        Category category

) {
}
