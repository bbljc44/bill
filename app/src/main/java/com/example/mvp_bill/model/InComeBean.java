package com.example.mvp_bill.model;

import cn.bmob.v3.BmobObject;

public class InComeBean extends BmobObject {
    private String userName;
    private String type;
    private String money;
    private String remark = "";

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
