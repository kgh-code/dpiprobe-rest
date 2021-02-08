package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpibasevalues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DpiBuilderConfiguration {

    public static volatile DpiBuilderConfiguration instance;
    private static volatile Map<String,Map<String,Number>> BASE_MAP;

    private DpiBuilderConfiguration(List<Dpibasevalues> allDpibasevalues) {
        System.out.println("in singleton");

        BASE_MAP = new HashMap<String,Map<String,Number>>();
        System.out.println(allDpibasevalues);
        System.out.println(allDpibasevalues.size());

        allDpibasevalues.forEach(dpibasevalues -> getBasevalues(dpibasevalues));

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(BASE_MAP);
        System.out.println(BASE_MAP.size());

        /*

        read the dpi signal basevalues
        put them in a hashmap, order by date
        have a key of 1 - 6, however many signals you have, so you can run historical commparisons
        against previous versions of DPI values ... ie today is better than yesterday, or 10 minutes ago.

         */
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


        } else {

            try {

                Double max = dpibasevalues.getMaxValue().doubleValue();
                Double min = dpibasevalues.getMinValue().doubleValue();

                Double diff = max - min;

/*
                if (dpibasevalues.getMaxValue() instanceof Double) {
                    n = (Double) dpibasevalues.getMaxValue() - (Double) dpibasevalues.getMinValue();
                }
                if (dpibasevalues.getMaxValue() instanceof Long) {
                    n = (Long) dpibasevalues.getMaxValue() - (Long) dpibasevalues.getMinValue();
                }
                if (dpibasevalues.getMaxValue() instanceof Integer) {
                    n = (Integer) dpibasevalues.getMaxValue() - (Integer) dpibasevalues.getMinValue();
                }
*/
                HashMap metricMap = new HashMap<String,Number>();
/*
                metricMap.put("minvalue",dpibasevalues.getMinValue());
                metricMap.put("maxvalue",dpibasevalues.getMaxValue());
                metricMap.put("difference",n);
*/

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
