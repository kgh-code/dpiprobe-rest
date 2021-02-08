package com.kgh.dpiprobe.businessfacades;

import java.util.Map;

public class DpiGeometricMeanMethod extends AbstractCalculateDPIMethod{

    public DpiGeometricMeanMethod() {}

    @Override
    protected void buildNormalValues() {

        for (Map.Entry<String, Map<String,Number>> entry : dpisignalsmap.entrySet()) {

                    Map<String,Number> dpisignals_metric_calculation = entry.getValue();

                    Double xi = dpisignals_metric_calculation.get("rawvalue").doubleValue();
                    Double xmin = dpisignals_metric_calculation.get("minvalue").doubleValue();
                    Double diff = dpisignals_metric_calculation.get("difference").doubleValue();

                    Double xtop = Double.valueOf(0);
                    Double xnorm = Double.valueOf(0);

                    xtop = xi - xmin;
                    xnorm = xtop / diff;

                    if (xnorm.isNaN()) {
                        xnorm = Double.valueOf(0);
                    }
                    if (xnorm < 0 == true) {
                        xnorm = Double.valueOf(0);
                    }

                    if (xnorm > 1 == true) {
                        xnorm = Double.valueOf(1);
                    }

                    dpisignals_metric_calculation.put("xnorm", xnorm);

                }
            }

    @Override
    protected void invertNormalValues() {
        for (Map.Entry<String, Map<String,Number>> entry : dpisignalsmap.entrySet()) {

            Map<String,Number> dpisignals_metric_calculation = entry.getValue();

            Double xnorm = dpisignals_metric_calculation.get("xnorm").doubleValue();

            // this is a hack, would need an extra bit of information 'invert or not'
            if (entry.getKey().compareTo("systemFreeSpace") == 0) {
                dpisignals_metric_calculation.put("upsidedown", xnorm);
            } else {
                dpisignals_metric_calculation.put("upsidedown", 1 - xnorm);
            }
        }
    }

    @Override
    protected void calculateDPIValue() {

        Double total = Double.valueOf(0);
        for (Map.Entry<String, Map<String,Number>> entry : dpisignalsmap.entrySet()) {

            Map<String,Number> dpisignals_metric_calculation = entry.getValue();

            Double xnormupsidedown = dpisignals_metric_calculation.get("upsidedown").doubleValue();

            total = total + xnormupsidedown;


        }
        Double d7 = Double.valueOf(7);
        Double d10 = Double.valueOf(10);

        total = total * (d10 / d7);

        Integer dpi = (int) Math.round(total);

        this.dpi = dpi;
    }
}