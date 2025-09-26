package com.api_coffee_store.api_coffee_store.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(nullable = false,unique = true)
    private String fullname;
    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    private int point;
    @Column(length = 10)
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Set<String> roles;
    @Column(unique = true) private String googleSub;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
