package com.example.coolweather.util;

import android.content.Context;

import com.example.coolweather.model.City;
import com.example.coolweather.model.Province;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/21.
 */
public class LocationUtil {

    public static List<Province>  getAllProvinces(Context context) {
        List<Province> list = new ArrayList<Province>();

        InputStream in = null;
        try {
            in = context.getAssets().open("LocList.xml");
        } catch (IOException e) {
            e.printStackTrace();
            return list;
        }
        XMLHelper helper = new XMLHelper(in);
        return helper.obtainProvinceList();
    }

    public static List<City>  getAllCities(Context context, String provinceCode) {
        List<City> list = new ArrayList<City>();

        InputStream in = null;
        try {
            in = context.getAssets().open("LocList.xml");
        } catch (IOException e) {
            e.printStackTrace();
            return list;
        }
        XMLHelper helper = new XMLHelper(in);
        return helper.obtainCityList(provinceCode);
    }
}
