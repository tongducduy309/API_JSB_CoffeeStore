package com.api_coffee_store.api_coffee_store.models;

import com.api_coffee_store.api_coffee_store.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_variants")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductVariant {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String size;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;
    private boolean status;


    @PreUpdate
    public void generateIdFromProductId() {
        if (this.product.getId() != null) {
            this.id = this.product.getId()+"-"+this.size;
        }
    }

    @PrePersist
    public void prePersist() {
        this.status = true;
        generateIdFromProductId();
    }

}
