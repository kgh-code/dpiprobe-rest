package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpisignals;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class HardResetCountSignalMethod extends CalculatePercentilelMethod {

    public HardResetCountSignalMethod(List<Dpisignals> all) {
        super.all = all;
    }

    @Override
    public void sortlist() {

        System.out.println("sortlist implementation of method for "+this.getClass());

        try {

            Comparator<Dpisignals> comparator = Comparator.comparingInt(Dpisignals::getHardResetCount);

            all.sort(comparator);

            List<Short>  mu_list = all.stream().map(Dpisignals::getHardResetCount).collect(Collectors.toList());

            Set<Short> mu_set = new LinkedHashSet<Short>();

            mu_set.addAll(mu_list);

            mu_list.clear();

            mu_list.addAll(mu_set);

            sortedList = mu_list;
            metricname = "Hard_Reset_Count";

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
