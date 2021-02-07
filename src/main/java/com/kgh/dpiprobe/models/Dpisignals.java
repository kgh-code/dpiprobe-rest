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
public class Dpisignals {

    @Id
    private String id;

/*
 "CPU_Usage" : 0.005, "Memory_Usage" : 0, "System_Free_Space" : NumberLong("180269625344") }
*/


    private Integer Device_ID;
    private Short   Client_ID;
    private Short   Office_ID;
    private Short   BSOD_count;
    private Short   Hard_reset_count;
    private Integer Boot_Speed;
    private Integer Logon_Duration;
    private Long    CPU_Usage;
    private Double  Memory_Usage;
    private Long    System_Free_Space;



    public Dpisignals() {

    }

    public Dpisignals(Integer Device_ID, Short Client_ID, Short Office_ID, Short BSOD_count, Short Hard_reset_count, Integer Boot_Speed, Integer Logon_Duration, Long CPU_Usage, Double Memory_Usage, Long System_Free_Space) {
        this.Device_ID = Device_ID;
        this.Client_ID = Client_ID;
        this.Office_ID = Office_ID;
        this.BSOD_count = BSOD_count;
        this.Hard_reset_count = Hard_reset_count;
        this.Boot_Speed = Boot_Speed;
        this.Logon_Duration = Logon_Duration;
        this.CPU_Usage = CPU_Usage;
        this.Memory_Usage = Memory_Usage;
        this.System_Free_Space = System_Free_Space;
    }

    public String getId() {
        return id;
    }

    public Integer getDevice_ID() {
        return Device_ID;
    }

    public void setDevice_ID(Integer Device_ID) {
        this.Device_ID = Device_ID;
    }

    public Short getClient_ID() {
        return Client_ID;
    }

    public void setClient_ID(Short Client_ID) {
        this.Client_ID = Client_ID;
    }

    public Short getOffice_ID() {
        return Office_ID;
    }

    public void setOffice_ID(Short Office_ID) {
        this.Office_ID = Office_ID;
    }

    public Short getBSOD_count() {
        return BSOD_count;
    }

    public void setBSOD_count(Short BSOD_count) {
        this.BSOD_count = BSOD_count;
    }

    public Short getHard_reset_count() {
        return Hard_reset_count;
    }

    public void setHard_reset_count(Short Hard_reset_count) {
        this.Hard_reset_count = Hard_reset_count;
    }

    public Integer getBoot_Speed() {
        return Boot_Speed;
    }

    public void setBoot_Speed(Integer Boot_Speed) {
        this.Boot_Speed = Boot_Speed;
    }

    public Integer getLogon_Duration() {
        return Logon_Duration;
    }

    public void setLogon_Duration(Integer Logon_Duration) {
        this.Logon_Duration = Logon_Duration;
    }

    public Long getCPU_Usage() {
        return CPU_Usage;
    }

    public void setCPU_Usage(Long CPU_Usage) {
        this.CPU_Usage = CPU_Usage;
    }

    public Double getMemory_Usage() {
        return Memory_Usage;
    }

    public void setMemory_Usage(Double Memory_Usage) {
        this.Memory_Usage = Memory_Usage;
    }

    public Long getSystem_Free_Space() {
        return System_Free_Space;
    }

    public void setSystem_Free_Space(Long System_Free_Space) {
        this.System_Free_Space = System_Free_Space;
    }

    @Override
    public String toString() {

            return "Dpsignals [id=" + id
                + ", Device_ID=" + Device_ID
                + ", Client_ID=" + Client_ID
                + ", Office_ID=" + Office_ID
                + ", BSOD_count=" + BSOD_count
                + ", Hard_reset_count=" + Hard_reset_count
                + ", Boot_Speed=" + Boot_Speed
                + ", Logon_Duration=" + Logon_Duration
                + ", CPU_Usage=" + CPU_Usage
                + ", Memory_Usage=" + Memory_Usage
                + ", System_Free_Space=" + System_Free_Space
                + "]";
    }
}