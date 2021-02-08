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
    protected Integer dpi;

    public final Dpitreatedsignals calculateDpi(Dpisignals dpisignals) {

        this.dpisignals = dpisignals;
        this.fields = dpisignals.getClass().getDeclaredFields();

        extractRawMetrics();

        buildNormalValues();

        invertNormalValues();

        calculateDPIValue();

        return build_dpitreatedsignals();

    }
    /**
     * For each Dpisignals, we need to get the raw metric values, store in a objecct hashmap
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
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    /**
     * For each Dpisignals, we need to calculate the normal values - geometric mean at the moment
     */
    protected abstract void buildNormalValues();
    /**
     * Invert some of the values -seperated into another function
     */
    protected abstract void invertNormalValues();
    /**
     * The DPI is the  sum of all the object normal values * 10/7
     */
    protected abstract void calculateDPIValue();
    /**
     * Buiild a candidate Dpitreatedsignals pojo
     */
    private Dpitreatedsignals build_dpitreatedsignals() {

        Dpitreatedsignals dpitreatedsignals = new Dpitreatedsignals();

        dpitreatedsignals.setDpi(this.dpi);
        dpitreatedsignals.setClientID(this.dpisignals.getClientID());
        dpitreatedsignals.setOfficeID(this.dpisignals.getOfficeID());
        dpitreatedsignals.setDeviceID(this.dpisignals.getDeviceID());

        return(dpitreatedsignals);

    }

}