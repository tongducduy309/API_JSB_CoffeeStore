package com.api_coffee_store.api_coffee_store.models;

import com.api_coffee_store.api_coffee_store.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @Column(length = 100, nullable = false, unique = true)
    private String id;
    @Column(length = 100, nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ProductVariant> variants = new ArrayList<>();
    private String description;
    private String img;
    @Column(name = "status")
    @Builder.Default
    private boolean status=true;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    public void generateIdFromName() {
        if (this.name != null) {
            this.id = StringUtil.toSlug(this.name);
        }
    }

    @PreUpdate
    public void up()
    {
        updatedAt = LocalDateTime.now();
        generateIdFromName();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        generateIdFromName();
    }



}
