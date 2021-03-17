package com.example.mvp_bill.presenter;

public interface DeletePresenter {
    void sendGetDeleteData(String type, String userName,String category);
    void sendChangeData(String objectId, String type, String money, String remark,String category);
    void sendDeleteData(String objectId,String category);
}
