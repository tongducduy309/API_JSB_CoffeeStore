package com.api_coffee_store.api_coffee_store.dtos.request;

import com.api_coffee_store.api_coffee_store.enums.Region;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record CreateBlogRequest(
        @NotNull(message = "Title Is Required") String title,
        @NotNull(message = "Subtitle Is Required") String subtitle,
        @NotNull(message = "Region Is Required") Region region,
        @NotNull(message = "Category Id Is Required") String categoryId,
        LocalDateTime createdAt,
        List<Box> boxes
) {


    public record Box(
            @NotNull(message = "Index Box Is Required") Integer index,
            @Length(max = 255,message = "Maximum Length Is 65.535")
            @Column(columnDefinition = "TEXT")
            String content,
            String section
    ) {}
}
