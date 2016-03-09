package com.example.locationtest;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private TextView textView;
    private String provider;
    private MyLocationListener locationListener;
    private BaiduMap baiduMap;
    private MapView mapView;
    private boolean isFirstLocate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext()); // 必须在setContentView之前，否则报错
        setContentView(R.layout.activity_main);
        //
        // 初始化百度地图
        //
        isFirstLocate = true;
        mapView = (MapView) findViewById(R.id.map);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        textView = (TextView) findViewById(R.id.text_location);
        //
        // 获取手机定位服务
        //
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息

        //
        // 获取最近一次位置
        //
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            showLocation(location);
            navigateTo(location);
        }
        //
        // 启动位置定位监听
        //
        locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private void showLocation(Location location) {
        String currentPosition = "latitude is " + location.getLatitude() + "\nlongitude is " + location.getLongitude();
        textView.setText(currentPosition);
    }

    private void navigateTo(Location location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
        }

        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data = builder.build();
        baiduMap.setMyLocationData(data);
    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
            navigateTo(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
