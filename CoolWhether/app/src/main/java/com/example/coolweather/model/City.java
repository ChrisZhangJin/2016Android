package com.example.coolweather.model;

/**
 * Created by Administrator on 2016/2/18.
 */
public class City {

    private int ID;
    private String cityName;
    private String cityCode;
    private int provinceID;

    public int getID() {
        return ID;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }
}
