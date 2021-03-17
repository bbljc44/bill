package com.example.mvp_bill.presenter;

import com.example.mvp_bill.model.ModelImpl;
import com.example.mvp_bill.model.OnDataListener;
import com.example.mvp_bill.view.AnalysisView;

import java.util.List;

public class AnalysisPresenterImpl implements AnalysisPresenter, OnDataListener {

    private ModelImpl model;
    private AnalysisView view;

    public AnalysisPresenterImpl(AnalysisView view){
        if(model == null){
            model = new ModelImpl();
            this.view = view;
        }
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
        view.updateNoView();
    }

    @Override
    public void sendGetDuration(String type, String userName, String category) {
        model.getDurationData(this, type, userName, category);
    }
}
