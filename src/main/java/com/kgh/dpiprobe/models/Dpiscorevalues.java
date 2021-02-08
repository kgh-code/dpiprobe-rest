package com.kgh.dpiprobe.models;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 * @Assume
 */
@Document(collection = "dpibase")
@ApiModel(value="Dpiscorevalues", description="The collection of treated raw signal data")
public class Dpiscorevalues implements Dpidata {

    @Id
    private String id;

    private Date createdDate;

    private String metricName;

    private Number minValue;

    private Number maxValue;

    public Dpiscorevalues() {
    }

    public Dpiscorevalues(Date createdDate, String metricName, Number minValue, Number maxValue) {
        this.createdDate = createdDate;
        this.metricName = metricName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    public String getId() {
        return id;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Number getMinValue() {
        return minValue;
    }

    public void setMinValue(Number minValue) {
        this.minValue = minValue;
    }

    public Number getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Number maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {

            return "Dpsignals [id=" + id
                + ", createdDate=" + createdDate
                + ", metricName=" + metricName
                + ", minValue=" + minValue
                + ", maxValue=" + maxValue
                + "]";
    }
}