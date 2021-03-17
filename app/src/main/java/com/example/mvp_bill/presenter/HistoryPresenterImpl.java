package com.example.mvp_bill.presenter;

import com.example.mvp_bill.model.ModelImpl;
import com.example.mvp_bill.model.OnDataListener;
import com.example.mvp_bill.view.HistoryView;


import java.util.List;

public class HistoryPresenterImpl implements HistoryPresenter, OnDataListener {


    private ModelImpl model;
    private HistoryView view;

    public HistoryPresenterImpl(HistoryView view){
        this.view = view;
        if(model == null){
            model = new ModelImpl();
        }
    }
    @Override
    public void sendGetHistory(String startTime, String type, String userName) {
        model.getHistory(startTime, type, userName, this);
    }

    @Override
    public void onSuccess(String type, String msg) {

    }

    @Override
    public void onSuccess(List<?> list, String msg) {
        view.updateView(list);
    }

    @Override
    public void onError(String msg) {
        view.updateNoDataView();
    }
}
