package com.example.a10crmbank;

public class History {
    String myid;
    String mytype;
    String myamount;
    String mydate;

    public History() {

    }

    public History(String myid, String mytype, String myamount, String mydate) {
        this.myid = myid;
        this.mytype = mytype;
        this.myamount = myamount;
        this.mydate = mydate;
    }

    public String getMyid() {
        return myid;
    }

    public void setMyid(String myid) {
        this.myid = myid;
    }

    public String getMytype() {
        return mytype;
    }

    public void setMytype(String mytype) {
        this.mytype = mytype;
    }

    public String getMyamount() {
        return myamount;
    }

    public void setMyamount(String myamount) {
        this.myamount = myamount;
    }

    public String getMydate() {
        return mydate;
    }

    public void setMydate(String mydate) {
        this.mydate = mydate;
    }
}
