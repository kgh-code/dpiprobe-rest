package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpibasevalues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DpiBuilderConfiguration {

    public static volatile DpiBuilderConfiguration instance;
    private static volatile Map<String,Map<String,Number>> BASE_MAP;

    private DpiBuilderConfiguration(List<Dpibasevalues> allDpibasevalues) {

        BASE_MAP = new HashMap<String,Map<String,Number>>();

        allDpibasevalues.forEach(dpibasevalues -> getBasevalues(dpibasevalues));

    }

    public static DpiBuilderConfiguration getInstance(List<Dpibasevalues> allDpibasevalues) {
        if (instance == null) {
            synchronized (DpiBuilderConfiguration.class) {
                if (instance == null) {
                    instance = new DpiBuilderConfiguration(allDpibasevalues);
                }
            }
        }
        return instance;
    }
    public static Map<String,Map<String,Number>> getBASE_MAP() {
        return BASE_MAP;
    }
    private void getBasevalues(Dpibasevalues dpibasevalues){

        if (BASE_MAP.containsKey(dpibasevalues.getMetricName())) {
            // has multiple entries - histories - but assume just one
        } else {

            try {

                Double max = dpibasevalues.getMaxValue().doubleValue();
                Double min = dpibasevalues.getMinValue().doubleValue();

                Double diff = max - min;

                HashMap metricMap = new HashMap<String,Number>();

                metricMap.put("minvalue",min);
                metricMap.put("maxvalue",max);
                metricMap.put("difference",diff);
                BASE_MAP.put(dpibasevalues.getMetricName(),metricMap);

            } catch (ArithmeticException e) {
                e.printStackTrace();
            }

        }
    }
}
