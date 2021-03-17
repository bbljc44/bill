package com.example.mvp_bill.view;

import java.util.List;

public interface HistoryView extends IView{
    void updateView(List<?> list);
    void updateNoDataView();
}
