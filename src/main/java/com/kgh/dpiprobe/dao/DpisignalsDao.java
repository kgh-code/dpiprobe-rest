package com.kgh.dpiprobe.dao;

import com.kgh.dpiprobe.models.Dpisignals;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Physical interface to persistence layer, service does not need to know about implementation
 */
public interface DpisignalsDao extends MongoRepository<Dpisignals, String> {
    List<Dpisignals> findAll();

}