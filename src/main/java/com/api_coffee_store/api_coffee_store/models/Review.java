package com.api_coffee_store.api_coffee_store.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Point Is Required")
    @Column(nullable = false)
    private int point;

    @NotBlank(message = "Comment Is Required")
    @Column(length = 500)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
//    private String customerId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt= LocalDateTime.now();

    @NotNull(message = "Id Product Is Required")
    @Column(nullable = false)
    private String productId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
