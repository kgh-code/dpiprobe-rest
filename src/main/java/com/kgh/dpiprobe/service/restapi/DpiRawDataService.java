package com.kgh.dpiprobe.service.restapi;

import com.kgh.dpiprobe.models.Dpisignals;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implement a startup service to simulate retrieving and pre-processing signal data
 * Calculate the base metrics for each set of metrics
 * Insert the base metrics into a data store (currently implemented in mongodb)
 * <p>
 */
public interface DpiRawDataService {
    public abstract List<Dpisignals> getDpisignals(Map<String,String> dpioptions);
    public abstract Optional<Dpisignals> getOneDpisignals(Integer deviceId);
    public abstract List<Dpisignals> getDpisignalsForCustomer(Integer customerId);
    public abstract List<Dpisignals> getDpisignalsForCustomer(Integer customerId, Integer officeId);
}
