package com.api_coffee_store.api_coffee_store.repositories;

import com.api_coffee_store.api_coffee_store.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog,Long> {
}

