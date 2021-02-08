package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpisignals;
import com.kgh.dpiprobe.models.Dpitreatedsignals;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract method temmplate for calculating the DPI of each device
 * Assume there can be more than one way of calculating the DPI for a device, so need a template method implementation
 * <p>
 */
public abstract class AbstractCalculateDPIMethod {

    protected Dpisignals dpisignals;
    protected Map<String, Map<String,Number>> dpisignalsmap;
    protected Field[] fields;

    public final void calculateDpi(Dpisignals dpisignals) {

        this.dpisignals = dpisignals;
        this.fields = dpisignals.getClass().getDeclaredFields();
        extractRawMetrics();
        buildNormalValues();


        System.out.println("--build normal values");
        System.out.println(dpisignalsmap);

        invertNormalValues();

        System.out.println("--invert normal values");
        System.out.println(dpisignalsmap);

        calculateNormalValues();

        //System.out.println(dpisignalsmap);
        //return build_dpi();

        //        return dpitreatedsignals;
    }
    /**
     * Abstract Sort a list of base signals based on a comparator, remove duplicate values
     */
    private void extractRawMetrics() {

        try {
                dpisignalsmap = new HashMap<String, Map<String,Number>>();

                for (Map.Entry<String, Map<String,Number>> entry : DpiBuilderConfiguration.getBASE_MAP().entrySet()) {
                    for (Field field : fields) {
                        if (field.getName().compareTo(entry.getKey()) == 0) {

                            field.setAccessible(true);
                            // deep copy the entries, bit hacky
                            Map<String,Number> basemetricvalues = entry.getValue();
                            Map<String,Number> dpisignals_metric_calculation = new HashMap<String,Number>();

                            dpisignals_metric_calculation.put("rawvalue", (Number) field.get(dpisignals));
                            dpisignals_metric_calculation.put("minvalue", basemetricvalues.get("minvalue"));
                            dpisignals_metric_calculation.put("maxvalue", basemetricvalues.get("maxvalue"));
                            dpisignals_metric_calculation.put("difference", basemetricvalues.get("difference"));

                            dpisignalsmap.put(entry.getKey(), dpisignals_metric_calculation);
/*
                            System.out.format("%n%s: %s", entry.getKey(), field.get(dpisignals));
*/
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    protected abstract void buildNormalValues();

    protected abstract void invertNormalValues();

    protected abstract void calculateNormalValues();
    /**
     * Set the working values for the minimmum and maximum values of the metric
     */

    /**
     * Buiild a candidate Dpitreatedsignals pojo
     */
    private Dpitreatedsignals build_dpi() {
        System.out.println("building metric for ");

        Dpitreatedsignals dpitreatedsignals = new Dpitreatedsignals();

        return(dpitreatedsignals);

    }
    private void buildMetricsMap(Map<String,Map<String,Number>> basevaluesmap){
        /*
        basevaluesmap contains the key values for the number of metrics you need to build
        could be any number.
         */
/*
        if (BASE_MAP.containsKey(dpibasevalues.getMetricName())) {


        } else {

            try {

                Number n = 0;

                if (dpibasevalues.getMaxValue() instanceof Double) {
                    n = (Double) dpibasevalues.getMaxValue() - (Double) dpibasevalues.getMinValue();
                }
                if (dpibasevalues.getMaxValue() instanceof Long) {
                    n = (Long) dpibasevalues.getMaxValue() - (Long) dpibasevalues.getMinValue();
                }
                if (dpibasevalues.getMaxValue() instanceof Integer) {
                    n = (Integer) dpibasevalues.getMaxValue() - (Integer) dpibasevalues.getMinValue();
                }
                HashMap metricMap = new HashMap<String,Number>();
                metricMap.put("minvalue",dpibasevalues.getMinValue());
                metricMap.put("maxvalue",dpibasevalues.getMaxValue());
                metricMap.put("difference",n);
                BASE_MAP.put(dpibasevalues.getMetricName(),metricMap);

            } catch (ArithmeticException e) {
                e.printStackTrace();
            }

        }
*/
    }


}