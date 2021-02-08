package com.kgh.dpiprobe.models;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document(collection = "dpitreatedsignals")
@ApiModel(value="Dpitreatedsignals", description="The set of treated  dpi signal data")
public class Dpitreatedsignals {

    @Id
    private String id;

    private Integer deviceID;
    private Short   clientID;
    private Short   officeID;
    private Integer dpi;

    public Dpitreatedsignals() {

    }

    public Dpitreatedsignals(Integer deviceID, Short clientID, Short officeID, Integer dpi) {
        this.deviceID = deviceID;
        this.clientID = clientID;
        this.officeID = officeID;
        this.dpi = dpi;
    }

    public String getId() {
        return id;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Short getClientID() {
        return clientID;
    }

    public void setClientID(Short clientID) {
        this.clientID = clientID;
    }

    public Short getOfficeID() {
        return officeID;
    }

    public void setOfficeID(Short officeID) {
        this.officeID = officeID;
    }

    public Integer getDpi() {
        return dpi;
    }

    public void setDpi(Integer dpi) {
        this.dpi = dpi;
    }

    @Override
    public String toString() {

            return "Dpitreeatedsignals [id=" + id
                + ", Device_ID=" + deviceID
                + ", Client_ID=" + clientID
                + ", Office_ID=" + officeID
                + ", DPI=" + dpi
                + "]";
    }
}