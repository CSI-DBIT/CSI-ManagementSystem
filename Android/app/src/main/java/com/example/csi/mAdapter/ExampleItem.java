package com.example.csi.mAdapter;

public class ExampleItem {

    private String mAgenda;
    private String mDate;
    private String mTime;
    private String mCreator;
    private String mPoints;

    public ExampleItem(String agenda, String date, String time, String creator, String points) {

        mAgenda = agenda;
        mDate = date;
        mTime = time;
        mCreator = creator;
        mPoints = points;
    }

    public String getAgenda() {
        return mAgenda;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getCreator() {
        return mCreator;
    }

    public String getPoints() {
        return mPoints;
    }
}
