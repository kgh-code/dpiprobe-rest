package com.kgh.dpiprobe.dao;

import com.kgh.dpiprobe.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Physical interface to persistence layer, service does not need to know about implementation
 */
public interface ProductDao extends MongoRepository<Product, String> {
    List<Product> findByTitleContaining(String title);
    List<Product> findByPublished(boolean published);
    Optional<Product> findBySkuid(String skuid);
}