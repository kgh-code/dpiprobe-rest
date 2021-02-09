package com.kgh.dpiprobe.service.restapi;

import com.kgh.dpiprobe.models.Dpitreatedsignals;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implement a startup service to simulate retrieving and pre-processing signal data
 * Calculate the base metrics for each set of metrics
 * Insert the base metrics into a data store (currently implemented in mongodb)
 * <p>
 */
public interface DpiTreatedDataService {
    public abstract List<Dpitreatedsignals> getDpitreatedsignals(Map<String,String> dpioptions);
    public abstract Optional<Dpitreatedsignals> getOneDpitreatedsignals(Integer deviceId);
    public abstract List<Dpitreatedsignals> getDpitreatedsignalsForClient(Integer clientId);

    public abstract List<Dpitreatedsignals> getDpitreatedsignalsForClient(Integer clientId, Map<String, Object> options);

    public abstract List<Dpitreatedsignals> getDpitreatedsignalsForClient(Integer clientId, Integer officeId);

    public abstract List<Dpitreatedsignals> getDpitreatedsignalsForClient(Integer clientId, Integer officeId, Map<String, Object> options);
}
