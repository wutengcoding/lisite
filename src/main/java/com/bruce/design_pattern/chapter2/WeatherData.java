package com.bruce.design_pattern.chapter2;

import java.util.ArrayList;

/**
 * Created by wuteng1 on 2016/7/29.
 */
public class WeatherData implements Subject{
    private ArrayList observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        observers = new ArrayList();
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if(i >= 0){
            observers.remove(i);
        }
    }

    public void notifyObservers() {
        for(int i = 0; i < observers.size(); i++){
            Observer obs = (Observer) observers.get(i);
            obs.update(temperature, humidity, pressure);
        }
    }

    public void measurementChanged(){
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementChanged();
    }
}