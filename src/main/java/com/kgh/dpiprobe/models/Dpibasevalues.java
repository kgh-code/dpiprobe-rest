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
@ApiModel(value="Dpibasevalues", description="The collection of calculated base metrics")
public class Dpibasevalues {

    @Id
    private String id;

    private Date createdDate;

    private String metricName;

    private Double minValue;

    private Double maxValue;

    public Dpibasevalues() {
    }

    public Dpibasevalues(Date createdDate, String metricName, Double minValue, Double maxValue) {
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

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
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