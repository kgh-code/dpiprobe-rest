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

import com.kgh.dpiprobe.dao.DpibasevaluesDao;
import com.kgh.dpiprobe.dao.DpisignalsDao;
import com.kgh.dpiprobe.models.Dpibasevalues;
import com.kgh.dpiprobe.models.Dpisignals;
import com.kgh.dpiprobe.businessfacades.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableMongoAuditing
public class SignalConsumerServiceImpl implements SignalConsumerService {

    @Autowired
    DpisignalsDao dpisignalsDao;

    @Autowired
    DpibasevaluesDao dpibasevaluesDao;

    @Override
    public void init() {
        try {

            final List<Dpisignals> all = this.findAllDpisignals();

            AbstractCalculatePercentilelMethod mu = new MemoryUsageSignalMethod(all);
            AbstractCalculatePercentilelMethod cpu = new CPUUsageSignalMethod(all);
            AbstractCalculatePercentilelMethod ld = new LogonDurationSignalMethod(all);
            AbstractCalculatePercentilelMethod bs /* haha */ = new BootSpeedSignalMethod(all);
            AbstractCalculatePercentilelMethod hrc = new HardResetCountSignalMethod(all);
            AbstractCalculatePercentilelMethod bc /* Bill! */= new BSODCountSignalMethod(all);
            AbstractCalculatePercentilelMethod sfs = new SystemFreeSpaceSignalMethod(all);

            if (!createBaseMetric(mu.calculateMetrics())) {
                throw new Exception("memory base metrics could not be built");
            }
            if (!createBaseMetric(cpu.calculateMetrics())) {
                throw new Exception("cpu base metrics could not be built");
            }
            if (!createBaseMetric(ld.calculateMetrics())) {
                throw new Exception("logon duration base metrics could not be built");
            }
            if (!createBaseMetric(bs.calculateMetrics())) {
                throw new Exception("boot speed base metrics could not be built");
            }
            if (!createBaseMetric(hrc.calculateMetrics())) {
                throw new Exception("hard reset count base metrics could not be built");
            }
            if (!createBaseMetric(bc.calculateMetrics())) {
                throw new Exception("BSOD count base metrics could not be built");
            }
            if (!createBaseMetric(sfs.calculateMetrics())) {
                throw new Exception("system free space metrics could not be built");
            }
            /* build DPI calculations - do this in a singleton, pass this into each calculation - observalble and observer or factory ? or facade?
            1. have a base object to contain the basevalues (max - min)
            2. this is a singleton passed into each calculation - how do you provide loose couplilng
            3. pass this singleton into each instanciated template method to be used in the for-each loop


             */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
    get the raw signal data from the subscribed listeners
    in this instance, we have the signal data populated in our mongodb
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
    write the base metrics for our set of signal data - based on a historic set of signals
     */
    @Override
    public boolean createBaseMetric(Dpibasevalues dpibasevalues) {
        try {
            dpibasevaluesDao.save(dpibasevalues);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}