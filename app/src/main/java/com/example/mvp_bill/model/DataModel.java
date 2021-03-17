package com.example.mvp_bill.model;

import java.util.HashMap;
import java.util.List;

public interface DataModel {
    void setData(String type,String money,String userName,String category,String remark,OnDataListener listener);
    void getDurationData(OnDataListener listener,String data_type,String userName,String category);
    void getHistory(String startTime,String type,String userName,OnDataListener listener);
    void deleteData(String objectId,String category,OnDataListener listener);
    void changeData(String objectId,String category,String type,String money,String remark,OnDataListener listener);
    void saveExcelData(List<HashMap<String,String>> list, String category, String userName, OnDataListener listener);
}
