package com.techuva.iot.ngt.model;

public class CurrentDataAgriPostParameter {

    private String deviceId;


    public CurrentDataAgriPostParameter(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
