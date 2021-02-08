package com.kgh.dpiprobe.dao;

import com.kgh.dpiprobe.models.Dpitreatedsignals;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Physical interface to persistence layer, service does not need to know about implementation
 *
 * @TODO    Pagination!
 */
public interface DpitreatedsignalsDao extends MongoRepository<Dpitreatedsignals, String> {
    List<Dpitreatedsignals> findAll();
    Optional<Dpitreatedsignals> findByDeviceID(Integer deviceid);
    List<Dpitreatedsignals> findByClientID(Integer clientid);
    List<Dpitreatedsignals> findByClientIDAndOfficeID(Integer clientid, Integer officeid);
    List<Dpitreatedsignals> findByDpi(Integer dpi);
}