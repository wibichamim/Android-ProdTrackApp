package com.ptsji.productioninfo.APIService.GetterSetter;

public class TrackListGetSet {
    public TrackListGetSet(String title, String date, String time, String scan) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.scan = scan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    String title;
    String date;
    String time;
    String scan;
}
