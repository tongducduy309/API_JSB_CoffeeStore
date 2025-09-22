package com.api_coffee_store.api_coffee_store.models;

import com.api_coffee_store.api_coffee_store.enums.Region;
import com.api_coffee_store.api_coffee_store.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "blog_titles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BlogTitle {
    @Id
    private String slug;

    private String title;

    private String subtitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", length = 2, nullable = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_index")
    private Blog blog;


    @PreUpdate
    @PrePersist
    public void generateIdFromTitle() {
        if (this.title != null) {
            this.slug = StringUtil.toSlug(this.title)+"-"+this.region;
        }
    }
}
