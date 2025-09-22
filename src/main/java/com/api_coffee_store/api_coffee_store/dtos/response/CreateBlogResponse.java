package com.api_coffee_store.api_coffee_store.dtos.response;

import com.api_coffee_store.api_coffee_store.enums.Region;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateBlogResponse(
        String slug,
        Long index,
        String title,
        String subtitle,
        Region region,
        LocalDateTime createdAt
) {

}
