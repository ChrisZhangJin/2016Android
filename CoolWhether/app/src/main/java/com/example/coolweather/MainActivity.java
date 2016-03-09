package com.example.coolweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coolweather.model.City;
import com.example.coolweather.model.Province;
import com.example.coolweather.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;

    private TextView textTitle;
    private ListView listArea;

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<String>();
    private List<Province> provinceList = new ArrayList<Province>();
    private List<City> cityList = new ArrayList<City>();

    private Province selectedProvince;
    private City selectedCity;
    private int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTitle = (TextView) findViewById(R.id.text_title);
        listArea = (ListView) findViewById(R.id.list_area);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listArea.setAdapter(adapter);
        listArea.setOnItemClickListener(this);

        queryProvince();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentLevel == LEVEL_PROVINCE) {
            selectedProvince = provinceList.get(position);

            //
            // 获取所有城市信息，并刷新listView
            //
            cityList = LocationUtil.getAllCities(this, selectedProvince.getProvinceCode());
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();

            currentLevel = LEVEL_CITY;

        } else if (currentLevel == LEVEL_CITY) {
            selectedCity = cityList.get(position);

            //
            // 跳转页面
            //
            Intent intent = new Intent(this, DisplayWeatherActivity.class);
            intent.putExtra("city", selectedCity.getCityName());
            startActivity(intent);

        }
    }

    private void queryProvince() {
        if (provinceList.isEmpty()) {
            provinceList = LocationUtil.getAllProvinces(this);
        }

        //
        // 绑定列表，刷新listView
        //
        for (Province p : provinceList) {
            dataList.add(p.getProvinceName());
        }
        adapter.notifyDataSetChanged();
        listArea.setSelection(0);

        textTitle.setText("中国");
        currentLevel = LEVEL_PROVINCE;
    }

    private void queryCities() {
        for (City c : cityList) {
            dataList.add(c.getCityName());
        }
        adapter.notifyDataSetChanged();
        listArea.setSelection(0);
        textTitle.setText(selectedProvince.getProvinceName());

    }
}

