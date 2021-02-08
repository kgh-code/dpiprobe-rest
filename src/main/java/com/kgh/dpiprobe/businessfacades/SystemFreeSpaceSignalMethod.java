package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpisignals;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class SystemFreeSpaceSignalMethod extends AbstractCalculatePercentilelMethod {

    public SystemFreeSpaceSignalMethod(List<Dpisignals> all) {
        super.all = all;
    }

    @Override
    public void sortlist() {

        System.out.println("sortlist implementation of method for "+this.getClass());

        try {

            Comparator<Dpisignals> comparator = Comparator.comparingDouble(Dpisignals::getSystemFreeSpace);
            all.sort(comparator);

            List<Long>  mu_list = all.stream().map(Dpisignals::getSystemFreeSpace).collect(Collectors.toList());

            Set<Long> mu_set = new LinkedHashSet<Long>();

            mu_set.addAll(mu_list);

            mu_list.clear();

            mu_list.addAll(mu_set);

            sortedList = mu_list;
            metricname = "systemFreeSpace";

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
