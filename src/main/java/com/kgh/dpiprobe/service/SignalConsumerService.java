package com.kgh.dpiprobe.service;

import com.kgh.dpiprobe.models.Dpibasevalues;
import com.kgh.dpiprobe.models.Dpisignals;

import java.util.List;

public interface SignalConsumerService {
    public abstract void init();
    public abstract List<Dpisignals> findAll();
    public abstract boolean createBaseMetric(Dpibasevalues dpibasevalues);

}
