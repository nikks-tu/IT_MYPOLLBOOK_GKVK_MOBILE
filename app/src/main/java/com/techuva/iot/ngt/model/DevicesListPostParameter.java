package com.techuva.iot.ngt.model;

public class DevicesListPostParameter {

    private String userId;
    private int companyId;
    private String refType;


    public DevicesListPostParameter(String userId, int companyId, String refType) {
        this.userId = userId;
        this.companyId = companyId;
        this.refType = refType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }


}
