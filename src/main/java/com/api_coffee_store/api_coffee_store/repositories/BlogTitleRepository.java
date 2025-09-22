package com.api_coffee_store.api_coffee_store.repositories;

import com.api_coffee_store.api_coffee_store.models.BlogTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTitleRepository extends JpaRepository<BlogTitle,String> {
}

