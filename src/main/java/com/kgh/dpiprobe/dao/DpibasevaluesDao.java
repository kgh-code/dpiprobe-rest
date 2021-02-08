package com.kgh.dpiprobe.dao;

import com.kgh.dpiprobe.models.Dpibasevalues;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Physical interface to persistence layer, service does not need to know about implementation
 */
@Repository
public interface DpibasevaluesDao extends MongoRepository<Dpibasevalues, String> {
    List<Dpibasevalues> findAll();
}
