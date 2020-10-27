package com.swufe.test.db;


public class RateItem {
    private final String TAG = "RateItem";
    private int id;
    private String curname;
    private String currate;

    public RateItem(){
        super();
        curname = "";
        currate = "";
    }
    public RateItem(String curname,String currate){
        super();
        this.curname = curname;
        this.currate = currate;
    }
    public String getCurRate() {
        return  this.currate;
    }

    public String getCurName() {
        return this.curname;
    }

    public void setId(int id) {
        this.id = id;

    }

    public void setCurName(String curname) {
        this.curname = curname;
    }

    public void setCurRate(String currate) {
        this.currate = currate;
    }
}
