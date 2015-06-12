package com.srii_systems.myrouteplan.model;

/**
 * Created by User on 11.6.2015.
 */
public class RoutePlan {

    private String mFromEditText;
    private String mToEditText;
    public RoutePlan(){}
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
}
