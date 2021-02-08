package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpibasevalues;
import com.kgh.dpiprobe.models.Dpisignals;

import java.util.Date;
import java.util.List;

/**
 * Abstract method temmplate for calculating the DPI of each device
 * Assume there can be more than one way of calculating the DPI for a device, so need a template method implementation
 * <p>
 */
public abstract class CalculateDPIMethod {

    protected List<Dpisignals> all;
    protected List<? extends Number> sortedList;
    protected Number minval;
    protected Number maxval;
    protected String metricname;

    /**
     * Template frame for implementing subclasses
     * Sort a list of signals, calculate the percentils, return a candidate Dpibasevalues construct
     *
     * @return      A candidate Dpibasevalues
     */
    public final Dpibasevalues calculateMetrics() {

        get_raw_signals();
        build_score();
        write_score();

    }
    private void get_raw_signals() {

    }
    protected abstract void build_score();

    private void write_score() {

    }

}