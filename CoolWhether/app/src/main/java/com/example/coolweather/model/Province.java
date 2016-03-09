package com.example.coolweather.model;

/**
 * Created by Administrator on 2016/2/18.
 */
public class Province {

    private int ID;
    private String provinceName;
    private String provinceCode;

    public int getID() {
        return ID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
