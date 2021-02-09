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

import com.kgh.dpiprobe.dao.DpitreatedsignalsDao;
import com.kgh.dpiprobe.models.Dpitreatedsignals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DpiTreatedDataServiceImpl implements DpiTreatedDataService {

    private static enum FILTER  {LTE, GTE, BETWEEN}

    @Autowired
    DpitreatedsignalsDao dpitreatedsignalsDao;

    @Override
    public List<Dpitreatedsignals> getDpitreatedsignals(Map<String, String> dpioptions) {
        return null;
    }

    @Override
    public Optional<Dpitreatedsignals> getOneDpitreatedsignals(Integer deviceId) {
        try {
            return dpitreatedsignalsDao.findByDeviceID(deviceId);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Dpitreatedsignals> getDpitreatedsignalsForClient(Integer clientId) {
        try {
            List<Dpitreatedsignals> dpitreatedsignals = new ArrayList<Dpitreatedsignals>();

            dpitreatedsignalsDao.findByClientID(clientId).forEach(dpitreatedsignals::add);

            return dpitreatedsignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Dpitreatedsignals> getDpitreatedsignalsForClient(Integer clientId, Map<String, Object> options) {
        try {
            List<Dpitreatedsignals> dpitreatedsignals = new ArrayList<Dpitreatedsignals>();

            FILTER f = FILTER.valueOf((String) options.get("filter"));
            switch (f) {
                case LTE:
                    dpitreatedsignalsDao.findDpitreatedsignalsByClientIDAndDpiLessThan(
                            clientId,
                            (Integer) options.get("val1")
                    ).forEach(dpitreatedsignals::add);
                    break;
                case GTE:
                    dpitreatedsignalsDao.findDpitreatedsignalsByClientIDAndDpiGreaterThan(
                            clientId,
                            (Integer) options.get("val1")
                    ).forEach(dpitreatedsignals::add);
                    break;
                case BETWEEN:
                    dpitreatedsignalsDao.findDpitreatedsignalsByClientIDAndDpiBetween(
                            clientId,
                            (Integer) options.get("val1"),
                            (Integer) options.get("val2")
                    ).forEach(dpitreatedsignals::add);
                    break;
                default:
                    dpitreatedsignalsDao.findByClientID(clientId).forEach(dpitreatedsignals::add);
            }
            return dpitreatedsignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Dpitreatedsignals> getDpitreatedsignalsForClient(Integer clientId, Integer officeId, Map<String, Object> options) {
        try {
            List<Dpitreatedsignals> dpitreatedsignals = new ArrayList<Dpitreatedsignals>();

            FILTER f = FILTER.valueOf((String) options.get("filter"));
            switch (f) {
                case LTE:
                    dpitreatedsignalsDao.findDpitreatedsignalsByClientIDAndOfficeIdAndDpiLessThan(
                            clientId,
                            officeId,
                            (Integer) options.get("val1")
                    ).forEach(dpitreatedsignals::add);
                    break;
                case GTE:
                    dpitreatedsignalsDao.findDpitreatedsignalsByClientIDAndOfficeIdAndDpiGreaterThan(
                            clientId,
                            officeId,
                            (Integer) options.get("val1")
                    ).forEach(dpitreatedsignals::add);
                    break;
                case BETWEEN:
                    dpitreatedsignalsDao.findDpitreatedsignalsByClientIDAndOfficeIdAndDpiBetween(
                            clientId,
                            officeId,
                            (Integer) options.get("val1"),
                            (Integer) options.get("val2")
                    ).forEach(dpitreatedsignals::add);
                    break;
                default:
                    dpitreatedsignalsDao.findByClientIDAndOfficeID(clientId,officeId).forEach(dpitreatedsignals::add);
            }
            return dpitreatedsignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Dpitreatedsignals> getDpitreatedsignalsForClient(Integer clientId, Integer officeId) {
        try {
            List<Dpitreatedsignals> dpitreatedsignals = new ArrayList<Dpitreatedsignals>();

            dpitreatedsignalsDao.findByClientIDAndOfficeID(clientId,officeId).forEach(dpitreatedsignals::add);

            return dpitreatedsignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
