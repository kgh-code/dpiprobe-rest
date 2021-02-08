package com.kgh.dpiprobe.dao;

import com.kgh.dpiprobe.models.Dpibasevalues;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Physical interface to persistence layer, service does not need to know about implementation
 */
//public interface DpisignalsDao extends MongoRepository {
@Repository
public interface DpibasevaluesDao extends MongoRepository<Dpibasevalues, String> {
}