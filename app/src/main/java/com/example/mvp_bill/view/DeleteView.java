package com.example.mvp_bill.view;

import java.util.List;

public interface DeleteView extends IView{
    void updateView(List<?> list);
    void updateDeleteView();
    void updateChangeView();
}
