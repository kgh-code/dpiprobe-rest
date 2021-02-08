package com.kgh.dpiprobe.dao;

import com.kgh.dpiprobe.models.Dpisignals;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
/**
 * Physical interface to persistence layer, service does not need to know about implementation
 *
 * @TODO    Pagination!
 */
public interface DpisignalsDao extends MongoRepository<Dpisignals, String> {
    List<Dpisignals> findAll();
    Optional<Dpisignals> findByDeviceID(Integer deviceid);
    List<Dpisignals> findByClientID(Integer clientid);
    List<Dpisignals> findByClientIDAndOfficeID(Integer clientid, Integer officeid);
}