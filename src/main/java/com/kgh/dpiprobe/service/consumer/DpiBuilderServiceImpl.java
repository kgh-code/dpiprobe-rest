/*
This is our consumer implementation, obviously only for demonstration purposes here

@Assume     seperate application setup as a consumer of signal data
@Assume     consumer listens to queues defined for each metric (6 here, possibly 20)
@Assume     all devices in the signal data are similar in architecture and environment
@Assume     signal data exists for each row / metric (no nulls)
@Assume     signal data is raw, no pre-processing required.
@Assume     this 'init()' method would run every 10 minutes... so re-calculate the base metrics per client

 */

package com.kgh.dpiprobe.service.consumer;

import com.kgh.dpiprobe.businessfacades.DpiBuilderConfiguration;
import com.kgh.dpiprobe.businessfacades.DpiGeometricMeanMethod;
import com.kgh.dpiprobe.dao.DpibasevaluesDao;
import com.kgh.dpiprobe.dao.DpisignalsDao;
import com.kgh.dpiprobe.dao.DpitreatedsignalsDao;
import com.kgh.dpiprobe.models.Dpibasevalues;
import com.kgh.dpiprobe.models.Dpisignals;
import com.kgh.dpiprobe.models.Dpitreatedsignals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DpiBuilderServiceImpl implements DpiBuilderService {

    @Autowired
    DpisignalsDao dpisignalsDao;

    @Autowired
    DpibasevaluesDao dpibasevaluesDao;

    @Autowired
    DpitreatedsignalsDao dpitreatedsignalsDao;

    @Override
    public void init() {
        try {

            System.out.println("DpiBuilderService");
            /*
            1. get the base data and send into the singleton
             */
            final List<Dpibasevalues> allDpibasevalues = this.findAllDpibasevalues();

            DpiBuilderConfiguration dpiBuilderConfiguration = DpiBuilderConfiguration.getInstance(allDpibasevalues);
            /*
            2. get the sginal data and loop on this, pass each row into the template method
             */
            final List<Dpisignals> allDpisignals = this.findAllDpisignals();

            allDpisignals.forEach(dpisignals ->
                    createDpitreatedsignals(new DpiGeometricMeanMethod().calculateDpi(dpisignals))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    get the raw untreated signal data
     */
    @Override
    public List<Dpisignals> findAllDpisignals() {
        try {
            List<Dpisignals> dpisignals = new ArrayList<Dpisignals>();
            dpisignalsDao.findAll().forEach(dpisignals::add);
            return dpisignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
    get the basevalues for the signal data
     */
    @Override
    public List<Dpibasevalues> findAllDpibasevalues() {
        try {
            List<Dpibasevalues> dpibasevalues = new ArrayList<Dpibasevalues>();
            dpibasevaluesDao.findAll().forEach(dpibasevalues::add);
            return dpibasevalues;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
    write the treated signal document with a dpi set
     */
    @Override
    public boolean createDpitreatedsignals(Dpitreatedsignals dpitreatedsignals) {
        try {
            System.out.println(dpitreatedsignals);
            dpitreatedsignalsDao.save(dpitreatedsignals);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}