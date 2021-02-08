package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpisignals;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class MemoryUsageSignalMethod extends CalculatePercentilelMethod {

    public MemoryUsageSignalMethod(List<Dpisignals> all) {
        super.all = all;
    }

    @Override
    public void sortlist() {

        System.out.println("sortlist implementation of method for "+this.getClass());

        try {

            Comparator<Dpisignals> comparator = Comparator.comparingDouble(Dpisignals::getMemoryUsage);
            all.sort(comparator);

            List<Double>  mu_list = all.stream().map(Dpisignals::getMemoryUsage).collect(Collectors.toList());

            Set<Double> mu_set = new LinkedHashSet<Double>();

            mu_set.addAll(mu_list);

            mu_list.clear();

            mu_list.addAll(mu_set);

            sortedList = mu_list;
            metricname = "Memory_Usage";

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
