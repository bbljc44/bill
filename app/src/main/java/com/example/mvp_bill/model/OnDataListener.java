package com.example.mvp_bill.model;

import java.util.List;

public interface OnDataListener {
    void onSuccess(String type,String msg);
    void onSuccess(List<?> list,String msg);
    void onError(String msg);
}
