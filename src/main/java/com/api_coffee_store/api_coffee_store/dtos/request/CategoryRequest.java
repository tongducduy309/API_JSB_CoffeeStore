package com.api_coffee_store.api_coffee_store.dtos.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    @NotBlank(message = "Category Name Is Required")
    @Size(max = 100, message = "Category Name Must Not Exceed 100 Characters")
    @Column(unique = true)
    private String name;
}
