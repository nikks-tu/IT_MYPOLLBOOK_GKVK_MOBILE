package com.techuva.iot.ngt.model;

public class CurrentDataPostParameter {

    private String deviceId;
    private String userId;

    public CurrentDataPostParameter(String deviceId, String userId) {
        this.deviceId = deviceId;
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
