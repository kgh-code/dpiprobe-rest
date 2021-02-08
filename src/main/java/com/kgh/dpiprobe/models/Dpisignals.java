package com.kgh.dpiprobe.models;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @Assume Longs for CPU_Usage, Memory_Usage; System_Free_Space as probably counting these in milliseconds
 */
@Document(collection = "dpisignals")
@ApiModel(value="Dpisignals", description="The superset of ingested dpi signal data")
public class Dpisignals implements Dpidata{

    @Id
    private String id;

    private Integer deviceID;
    private Short   clientID;
    private Short   officeID;
    private Short   bSODCount;
    private Short   hardResetCount;
    private Integer bootSpeed;
    private Integer logonDuration;
    private Double  cPUUsage;
    private Double  memoryUsage;
    private Long    systemFreeSpace;



    public Dpisignals() {

    }

    public Dpisignals(Integer deviceID, Short clientID, Short officeID, Short bSODCount, Short hardResetCount, Integer bootSpeed, Integer logonDuration, Double cPUUsage, Double memoryUsage, Long systemFreeSpace) {
        this.deviceID = deviceID;
        this.clientID = clientID;
        this.officeID = officeID;
        this.bSODCount = bSODCount;
        this.hardResetCount = hardResetCount;
        this.bootSpeed = bootSpeed;
        this.logonDuration = logonDuration;
        this.cPUUsage = cPUUsage;
        this.memoryUsage = memoryUsage;
        this.systemFreeSpace = systemFreeSpace;
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

    public Short getBSODCount() {
        return bSODCount;
    }

    public void setBSODCount(Short bSODCount) {
        this.bSODCount = bSODCount;
    }

    public Short getHardResetCount() {
        return hardResetCount;
    }

    public void setHardResetCount(Short hardResetCount) {
        this.hardResetCount = hardResetCount;
    }

    public Integer getBootSpeed() {
        return bootSpeed;
    }

    public void setBootSpeed(Integer bootSpeed) {
        this.bootSpeed = bootSpeed;
    }

    public Integer getLogonDuration() {
        return logonDuration;
    }

    public void setLogonDuration(Integer logonDuration) {
        this.logonDuration = logonDuration;
    }

    public Double getCPUUsage() {
        return cPUUsage;
    }

    public void setCPUUsage(Double cPUUsage) {
        this.cPUUsage = cPUUsage;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Long getSystemFreeSpace() {
        return systemFreeSpace;
    }

    public void setSystemFreeSpace(Long systemFreeSpace) {
        this.systemFreeSpace = systemFreeSpace;
    }

    @Override
    public String toString() {

            return "Dpsignals [id=" + id
                + ", Device_ID=" + deviceID
                + ", Client_ID=" + clientID
                + ", Office_ID=" + officeID
                + ", BSOD_count=" + bSODCount
                + ", Hard_reset_count=" + hardResetCount
                + ", Boot_Speed=" + bootSpeed
                + ", Logon_Duration=" + logonDuration
                + ", CPU_Usage=" + cPUUsage
                + ", Memory_Usage=" + memoryUsage
                + ", System_Free_Space=" + systemFreeSpace
                + "]";
    }
}