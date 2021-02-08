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
import com.kgh.dpiprobe.models.Dpibasevalues;
import com.kgh.dpiprobe.models.Dpisignals;
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
            2. get the sginal data and loop on this, pass each row and singleton ref inot the template method
             */
            final List<Dpisignals> allDpisignals = this.findAllDpisignals();

            //AbstractCalculateDPIMethod dpiGeometricMeanMethod = new DpiGeometricMeanMethod();
            allDpisignals.forEach(dpisignals -> new DpiGeometricMeanMethod().calculateDpi(dpisignals));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
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


}