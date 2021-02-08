package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpisignals;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CPUUsageSignalMethod extends AbstractCalculatePercentilelMethod {

    public CPUUsageSignalMethod(List<Dpisignals> all) {
        super.all = all;
    }

    @Override
    public void sortlist() {

        System.out.println("sortlist implementation of method for "+this.getClass());

        try {

            Comparator<Dpisignals> comparator = Comparator.comparingDouble(Dpisignals::getCPUUsage);

            all.sort(comparator);

            List<Double>  cpu_list = all.stream().map(Dpisignals::getCPUUsage).collect(Collectors.toList());

            Set<Double> mu_set = new LinkedHashSet<Double>();

            mu_set.addAll(cpu_list);

            cpu_list.clear();

            cpu_list.addAll(mu_set);

            sortedList = cpu_list;
            metricname = "cPUUsage";

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
