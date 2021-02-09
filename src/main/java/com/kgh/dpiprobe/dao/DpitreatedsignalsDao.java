package com.kgh.dpiprobe.dao;

import com.kgh.dpiprobe.models.Dpitreatedsignals;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Physical interface to persistence layer, service does not need to know about implementation
 *
 * @TODO    Pagination!
 */
public interface DpitreatedsignalsDao extends MongoRepository<Dpitreatedsignals, String> {
    List<Dpitreatedsignals> findAll();
    Optional<Dpitreatedsignals> findByDeviceID(int deviceid);
    List<Dpitreatedsignals> findByClientID(int clientid);
    List<Dpitreatedsignals> findByClientIDAndOfficeID(int clientid, int officeid);
    List<Dpitreatedsignals> findByDpi(Integer dpi);

    @Query("{'clientID' : ?0, 'dpi' : { $gte: ?1, $lte: ?2 } }")
    List<Dpitreatedsignals> findDpitreatedsignalsByClientIDAndDpiBetween(int clientid, int low, int high);

    @Query("{'clientID' : ?0, 'officeID' : ?1,'dpi' : { $gte: ?2, $lte: ?3 } }")
    List<Dpitreatedsignals> findDpitreatedsignalsByClientIDAndOfficeIdAndDpiBetween(int clientid, int officeid, int low, int high);

    @Query("{'clientID' : ?0, 'dpi' : { $gte: ?1} }")
    List<Dpitreatedsignals> findDpitreatedsignalsByClientIDAndDpiGreaterThan(int clientid, int low);

    @Query("{'clientID' : ?0, 'dpi' : { $lte: ?1} }")
    List<Dpitreatedsignals> findDpitreatedsignalsByClientIDAndDpiLessThan(int clientid, int high);

    @Query("{'clientID' : ?0, 'officeID' : ?1,'dpi' : { $gte: ?2} }")
    List<Dpitreatedsignals> findDpitreatedsignalsByClientIDAndOfficeIdAndDpiGreaterThan(int clientid, int officeid, int low);

    @Query("{'clientID' : ?0, 'officeID' : ?1,'dpi' : { $lte: ?2} }")
    List<Dpitreatedsignals> findDpitreatedsignalsByClientIDAndOfficeIdAndDpiLessThan(int clientid, int officeid, int high);

}
