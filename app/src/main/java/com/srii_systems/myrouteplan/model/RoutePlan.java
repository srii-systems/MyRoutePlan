package com.srii_systems.myrouteplan.model;

/**
 * Created by User on 11.6.2015.
 */

public class RoutePlan {

    private String mFromEditText;
    private String mToEditText;

    private Double fromLatitude;
    private Double fromLongitude;
    private Double toLatitude;
    private Double toLongitude;
    private String requestUrl;



    public RoutePlan(){
        fromLatitude = Double.valueOf(0);
        fromLongitude = Double.valueOf(0);
        toLatitude = Double.valueOf(0);
        toLongitude = Double.valueOf(0);
    }
    public RoutePlan(String fromEditText, String toEditText){
        this.mFromEditText = fromEditText;
        this.mToEditText = toEditText;
    }
    public String getmFromEditText() {
        return mFromEditText;
    }

    public void setmFromEditText(String mFromEditText) {
        this.mFromEditText = mFromEditText;
    }

    public String getmToEditText() {
        return mToEditText;
    }

    public void setmToEditText(String mToEditText) {
        this.mToEditText = mToEditText;
    }

    public Double getFromLatitude() {
        return fromLatitude;
    }

    public void setFromLatitude(Double fromLatitude) {
        this.fromLatitude = fromLatitude;
    }

    public Double getFromLongitude() {
        return fromLongitude;
    }

    public void setFromLongitude(Double fromLongitude) {
        this.fromLongitude = fromLongitude;
    }

    public Double getToLatitude() {
        return toLatitude;
    }

    public void setToLatitude(Double toLatitude) {
        this.toLatitude = toLatitude;
    }

    public Double getToLongitude() {
        return toLongitude;
    }

    public void setToLongitude(Double toLongitude) {
        this.toLongitude = toLongitude;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
