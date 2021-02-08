package com.kgh.dpiprobe.businessfacades;

import com.kgh.dpiprobe.models.Dpisignals;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class BootSpeedSignalMethod extends CalculatePercentilelMethod {

    public BootSpeedSignalMethod(List<Dpisignals> all) {
        super.all = all;
    }

    @Override
    public void sortlist() {

        System.out.println("sortlist implementation of method for "+this.getClass());

        try {

            Comparator<Dpisignals> comparator = Comparator.comparingInt(Dpisignals::getBootSpeed);


            all.sort(comparator);

            List<Integer>  mu_list = all.stream().map(Dpisignals::getBootSpeed).collect(Collectors.toList());

            Set<Integer> mu_set = new LinkedHashSet<Integer>();

            mu_set.addAll(mu_list);

            mu_list.clear();

            mu_list.addAll(mu_set);

            sortedList = mu_list;
            metricname = "Boot_Speed";

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
