package com.kgh.dpiprobe.service;

import com.kgh.dpiprobe.dao.DpisignalsDao;
import com.kgh.dpiprobe.models.Dpibasevalues;
import com.kgh.dpiprobe.models.Dpisignals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/*

 */
@Service
@EnableMongoAuditing
public class SignalConsumerServiceImpl implements SignalConsumerService {

    @Autowired
    DpisignalsDao dpisignalsDao;
    /*
    This is our consumer implementation, obviously only for demonstration purposes here

    @Assume     seperate application setup as a consumer of signal data
    @Assume     consumer listens to queues defined for each metric (5 here, possibly 20)
    @Assume     all devices in the signal data are similar in architecture and environment
    @Assume     signal data exists for each row / metric (no nulls)
    @Assume     signal data is raw, no pre-porocessing required.
    @Assume     this 'init()' method would run every 10 minutes... so re-calculate teh base metrics per client

        private Short   BSOD_count;
        private Short   Hard_reset_count;
        private Integer Boot_Speed;
        private Integer Logon_Duration;
        private Long    CPU_Usage;
        private Long    Memory_Usage;

     */
    @Override
    public void init() {
        final List<Dpisignals> all = this.findAll();

        System.out.println(all.size());

        setPercentils_Memory_Usage(all);
/*
        setPercentils_CPU_Usage(all)
        setPercentils_Logon_Duration(all)
        setPercentils_Boot_Speed(all)
        setPercentils_Hard_reset_count(all)
        setPercentils_BSOD_count(all)
*/



        Comparator<Dpisignals> c0 = Comparator.comparing(Dpisignals::getBSOD_count);
        Comparator<Dpisignals> c1 = Comparator.comparing(Dpisignals::getHard_reset_count);
        Comparator<Dpisignals> c2 = Comparator.comparing(Dpisignals::getBoot_Speed);
        Comparator<Dpisignals> c3 = Comparator.comparing(Dpisignals::getLogon_Duration);
        Comparator<Dpisignals> c4 = Comparator.comparingLong(Dpisignals::getCPU_Usage);
        Comparator<Dpisignals> c5 = Comparator.comparingDouble(Dpisignals::getMemory_Usage);
        /*
        Memmory usage sort first
        Then extract into a list
        Then remove duplicates
         */
        all.sort(c5);

        //all.forEach(System.out::println);

        List<Double> field1List = all.stream().map(Dpisignals::getMemory_Usage).collect(Collectors.toList());

        Set<Double> set = new LinkedHashSet<Double>();

        // Add the elements to set
        set.addAll(field1List);

        // Clear the list
        field1List.clear();

        // add the elements of set
        // with no duplicates to the list
        field1List.addAll(set);
        field1List.forEach(System.out::println);

        System.out.println("kkk " + percentile(2.0, field1List));
        System.out.println("kkk " + percentile(98.0, field1List));

        System.out.printf("2nd percentil %n, 98th percentile %n",
                percentile(2.0, field1List),
                percentile(98.0, field1List));
    }



    @Override
    public List<Dpisignals> findAll() {
        try {
            List<Dpisignals> dpisignals = new ArrayList<Dpisignals>();
            dpisignalsDao.findAll().forEach(dpisignals::add);
            return dpisignals;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void  setPercentils_Memory_Usage(List<Dpisignals> signals) {
        try {

            Comparator<Dpisignals> comparator = Comparator.comparingDouble(Dpisignals::getMemory_Usage);
            signals.sort(comparator);

            List<Double>  mu_list = signals.stream().map(Dpisignals::getMemory_Usage).collect(Collectors.toList());

            Set<Double> mu_set = new LinkedHashSet<Double>();

            mu_set.addAll(mu_list);

            mu_list.clear();

            mu_list.addAll(mu_set);

            mu_list.forEach(System.out::println);

            System.out.println("kkk " + percentile(2.0, mu_list));
            System.out.println("kkk " + percentile(98.0, mu_list));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private double percentile(double percentile, List<Double> items) {
        try {
            return items.get((int) Math.round(percentile / 100.0 * (items.size() - 1)));
        } catch (ArithmeticException e) {
            e.printStackTrace();
            return percentile  > 50 ? items.get(items.size()) : items.get(0);
        }
    }

    @Override
    public boolean createBaseMetric(Dpibasevalues dpibasevalues) {
        return true;
    }
/*

    @Override
    public boolean createBaseMetric(Product product) {
        try {
            product.setPublished(true);
            product.setSkuid(getNewSkuid(product));
            productDao.save(product);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
*/
}