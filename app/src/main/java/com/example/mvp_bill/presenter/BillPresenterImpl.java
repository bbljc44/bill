package com.example.mvp_bill.presenter;

import com.example.mvp_bill.model.ModelImpl;
import com.example.mvp_bill.model.OnDataListener;
import com.example.mvp_bill.view.BillView;

import java.util.List;

public class BillPresenterImpl implements BillPresenter, OnDataListener {

    private ModelImpl model;
    private BillView view;

    public BillPresenterImpl(BillView view){
        this.view = view;
        if(model == null){
            model = new ModelImpl();
        }
    }

    @Override
    public void sendSetRequest(String type, String money, String userName, String category, String remark) {
        model.setData(type, money, userName, category, remark, this);
    }

    @Override
    public void onSuccess(String type, String msg) {
        view.showToast(msg);
    }

    @Override
    public void onSuccess(List<?> list, String msg) {

    }

    @Override
    public void onError(String msg) {

    }

}
