/*
This is our consumer implementation, obviously only for demonstration purposes here

@Assume     seperate application setup as a consumer of signal data
@Assume     consumer listens to queues defined for each metric (6 here, possibly 20)
@Assume     all devices in the signal data are similar in architecture and environment
@Assume     signal data exists for each row / metric (no nulls)
@Assume     signal data is raw, no pre-processing required.
@Assume     this 'init()' method would run every 10 minutes... so re-calculate the base metrics per client

 */

package com.kgh.dpiprobe.service.restapi;

import com.kgh.dpiprobe.dao.DpibasevaluesDao;
import com.kgh.dpiprobe.dao.DpisignalsDao;
import com.kgh.dpiprobe.models.Dpisignals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DpiRawDataServiceImpl implements DpiRawDataService {

    @Autowired
    DpisignalsDao dpisignalsDao;

    @Autowired
    DpibasevaluesDao dpibasevaluesDao;

    @Override
    public List<Dpisignals> getDpisignals(Map<String,String> dpioptions) {
        try {
            List<Dpisignals> dpisignals = new ArrayList<Dpisignals>();
            dpisignalsDao.findAll().forEach(dpisignals::add);
            return dpisignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Dpisignals> getDpisignalsForCustomer(Integer customerId) {
        try {
            List<Dpisignals> dpisignals = new ArrayList<Dpisignals>();
            dpisignalsDao.findByClientID(customerId).forEach(dpisignals::add);
            return dpisignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Dpisignals> getDpisignalsForCustomer(Integer customerId, Integer officeId) {
        try {
            List<Dpisignals> dpisignals = new ArrayList<Dpisignals>();

/*
            Criteria c = new Criteria();
            c.and("clientID").is(customerId);
            c.and("officeID").is(officeId);
            Query q = new Query().addCriteria(c);

            @Query("{'name' : ?0 , 'band' : ?1}");
*/


            dpisignalsDao.findByClientIDAndOfficeID(customerId,officeId).forEach(dpisignals::add);
            return dpisignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Dpisignals> getOneDpisignals(Integer deviceId) {
        try {
            return dpisignalsDao.findByDeviceID(deviceId);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
/*
            return dpisignalsDao.findByQuery(query);
*/
//            Optional<Dpisignals> dpisignals;

/*
            Query query = new Query();
            query.addCriteria(Criteria.where("Device_ID").is(deviceid));
*/
