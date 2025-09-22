package com.api_coffee_store.api_coffee_store.models;

import com.api_coffee_store.api_coffee_store.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    @Column(length = 100, nullable = false, unique = true)
    private String id;
    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @PrePersist
    @PreUpdate
    public void generateIdFromName() {
        if (this.name != null) {
            this.id = StringUtil.toSlug(this.name);
        }
    }
}
