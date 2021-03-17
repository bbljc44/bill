package com.example.mvp_bill.presenter;

import com.example.mvp_bill.model.ModelImpl;
import com.example.mvp_bill.model.OnDataListener;
import com.example.mvp_bill.view.DeleteView;

import java.text.DecimalFormat;
import java.util.List;

public class DeletePresenterImpl implements DeletePresenter, OnDataListener {

    private ModelImpl model;
    private DeleteView view;

    public DeletePresenterImpl(DeleteView view){
        if(model == null){
            model = new ModelImpl();
        }
        this.view = view;
    }

    @Override
    public void sendGetDeleteData(String type, String userName,String category) {
        model.getDeleteData(type, userName, category, this);
    }

    @Override
    public void sendChangeData(String objectId, String type, String money, String remark,String category) {
        DecimalFormat df = new DecimalFormat(".00");
        money = df.format(Float.parseFloat(money));
        model.changeData(objectId, category, type, money, remark, this);
    }

    @Override
    public void sendDeleteData(String objectId,String category) {
        model.deleteData(objectId, category, this);
    }

    @Override
    public void onSuccess(String type, String msg) {
        if (type.equals("delete")) {
            view.showToast("操作成功");
            view.updateDeleteView();
        } else if (type.equals("change")) {
            view.updateChangeView();
            view.showToast("修改成功");
        }
    }

    @Override
    public void onSuccess(List<?> list, String msg) {
        if (list.size() == 0) {
            view.showToast("fail");
        } else {
            view.updateView(list);
        }
    }

    @Override
    public void onError(String msg) {
        view.showToast("操作失败");
    }
}
