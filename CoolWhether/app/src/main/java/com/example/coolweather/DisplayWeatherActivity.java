package com.example.coolweather;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolweather.util.FakeWeatherUtil;

public class DisplayWeatherActivity extends Activity {

    private ImageView imageView;
    private TextView textWeather;
    private TextView textTemperature;
    private TextView textPM25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);

        String cityName = getIntent().getStringExtra("city");
        setTitle(cityName);

        initUI();

    }

    private void initUI(){
        int weather = FakeWeatherUtil.getWeather();
        textWeather = (TextView)findViewById(R.id.text_weather);
        textWeather.setText(weather);
        textTemperature = (TextView)findViewById(R.id.text_temperature);
        textTemperature.setText("温度："+FakeWeatherUtil.getTemperature()+"C");
        textPM25 = (TextView)findViewById(R.id.text_pm25);
        textPM25.setText("PM2.5：" + FakeWeatherUtil.getPM25());
        imageView = (ImageView)findViewById(R.id.image_weather);
        imageView.setImageDrawable(getResources().getDrawable(FakeWeatherUtil.getAssociatedWeatherImage(weather)));
    }

}
