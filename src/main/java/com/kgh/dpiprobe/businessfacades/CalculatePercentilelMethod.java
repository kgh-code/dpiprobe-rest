package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpibasevalues;
import com.kgh.dpiprobe.models.Dpisignals;

import java.util.Date;
import java.util.List;

/**
 * Abstract method temmplate for calculating base metrics
 * Assume that the percentiles - 02 and 98 can be passed in as the historic data changes
 * <p>
 */
public abstract class CalculatePercentilelMethod {

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

        sortlist();
        getpercentiles();
        return build_metric();
    }
    /**
     * Abstract Sort a list of base signals based on a comparator, remove duplicate values
     */
    protected abstract void sortlist();

    /**
     * Set the working values for the minimmum and maximum values of the metric
     */
    private void getpercentiles() {
        System.out.println("getpercentile implementation of method for "+this.getClass());
        minval = percentile(2.0);
        maxval = percentile(98.0);
    }

    /**
     * Buiild a candidate Dbpibasevalues metric pojo
     */
    private Dpibasevalues build_metric() {
        System.out.println("building metric for ");

        Dpibasevalues dpibasevalues = new Dpibasevalues();
        dpibasevalues.setCreatedDate(new Date());
        dpibasevalues.setMetricName(metricname);
        dpibasevalues.setMinValue(minval);
        dpibasevalues.setMaxValue(maxval);

        return(dpibasevalues);

    }
    /**
     * Calculate a percentile
     * @params      Double      the percentile to retrieve from the raw data list
     * @return      Number      the calculated percentile
     */
    private Number percentile(double percentile) {
        System.out.println("getting percenties for metric");
        try {
            return sortedList.get((int) Math.round(percentile / 100.0 * (sortedList.size() - 1)));
        } catch (ArithmeticException e) {
            e.printStackTrace();
            return percentile > 50 ? sortedList.get(sortedList.size()) : sortedList.get(0);
        }
    }
}