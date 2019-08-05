package com.example.csi.mAdapter;

public class PraposalItem {

    private String mEid;
    private String mName;
    private String mDate;
    private String mStatus;
    private String mExtra;

    public PraposalItem(String Eid, String date, String Name, String Status, String extra) {

        mEid = Eid;
        mDate = date;
        mName = Name;
        mStatus = Status;
        mExtra = extra;
    }

    public String getmEid() {
        return mEid;
    }

    public String getDate() {
        return mDate;
    }

    public String getmName() {
        return mName;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmExtra() {
        return mExtra;
    }
}
