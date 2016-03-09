package com.example.coolweather.util;

import com.example.coolweather.R;

import java.util.Random;

/**
 * Created by Administrator on 2016/2/23.
 */
public class FakeWeatherUtil {

    public static Random random;

    public static int getWeather() {
        random = new Random(System.currentTimeMillis());
        int type = random.nextInt(7);

        switch (type) {
            case 1:
                return R.string.weather_cloudy;
            case 2:
                return R.string.weather_flurry;
            case 3:
                return R.string.weather_overcast_sky;
            case 4:
                return R.string.weather_middle_rain;
            case 5:
                return R.string.weather_heavy_rain;
            case 6:
                return R.string.weather_mist;
            case 0:
            default:
                return R.string.weather_sunshine;
        }

    }

    public static int getAssociatedWeatherImage(int id){
        switch (id) {
            case R.string.weather_cloudy:
                return R.drawable.image1;
            case R.string.weather_flurry:
                return R.drawable.image2;
            case R.string.weather_overcast_sky:
                return R.drawable.image3;
            case R.string.weather_middle_rain:
                return R.drawable.image4;
            case R.string.weather_heavy_rain:
                return R.drawable.image5;
            case R.string.weather_mist:
                return R.drawable.image6;
            case R.string.weather_sunshine:
            default:
                return R.drawable.image0;
        }
    }

    /**
     * range is -10 ~ 40
     */
    public static int getTemperature() {
        random = new Random(System.currentTimeMillis());
        return random.nextInt(51) - 10;
    }

    /**
     * range is 0 ~ 500
     */
    public static int getPM25() {
        random = new Random(System.currentTimeMillis());
        return random.nextInt(501);
    }
}
