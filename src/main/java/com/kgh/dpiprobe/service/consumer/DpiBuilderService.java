package com.kgh.dpiprobe.service.consumer;

import com.kgh.dpiprobe.models.Dpibasevalues;
import com.kgh.dpiprobe.models.Dpisignals;
import com.kgh.dpiprobe.models.Dpitreatedsignals;

import java.util.List;

/**
 * Implement a startup service to simulate retrieving and pre-processing signal data
 * Calculate the base metrics for each set of metrics
 * Insert the base metrics into a data store (currently implemented in mongodb)
 * <p>
 */
public interface DpiBuilderService {
    public abstract void init();
    /**
     * Retrieve raw signal data
     * @return      list<Dpisignals>    the list off raw signal data
     */
    public abstract List<Dpisignals> findAllDpisignals();
    public abstract List<Dpibasevalues> findAllDpibasevalues();
    public abstract boolean createDpitreatedsignals(Dpitreatedsignals dpitreatedsignals);
}
