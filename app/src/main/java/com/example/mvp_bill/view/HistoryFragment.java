package com.example.mvp_bill.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mvp_bill.Adapter.HistoryRecyclerViewAdapter;
import com.example.mvp_bill.R;
import com.example.mvp_bill.presenter.HistoryPresenter;
import com.example.mvp_bill.presenter.HistoryPresenterImpl;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryFragment extends Fragment implements HistoryView{

    private View view;
    private String[] spinner_title = {"支出", "收入"};
    private String selectType;
    private RecyclerView recyclerView;
    private TextInputEditText year;
    private TextInputEditText month;
    private TextInputEditText day;
    private String startTime;
    private String userName;
    private ViewStub viewStub;
    private HistoryPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        if(presenter == null){
            presenter = new HistoryPresenterImpl(this);
        }
        day = view.findViewById(R.id.day);
        month = view.findViewById(R.id.month);
        year = view.findViewById(R.id.year);
        viewStub = view.findViewById(R.id.vs);
        viewStub.inflate();
        viewStub.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, spinner_title);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectType = spinner_title[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        view.findViewById(R.id.btn_query).setOnClickListener(view -> {
            //获取用户输入的年月日
            String dayTime = day.getText().toString().trim();
            String monthTime = month.getText().toString().trim();
            String yearTime = year.getText().toString().trim();
            if (TextUtils.isEmpty(monthTime) || TextUtils.isEmpty(yearTime) || TextUtils.isEmpty(dayTime)) {
                Toast.makeText(view.getContext(), "请填写好年月日三个值", Toast.LENGTH_SHORT).show();
            } else if (monthTime.length() == 1 || dayTime.length() == 1) {
                if (monthTime.length() == 1 && dayTime.length() == 1) {
                    startTime = yearTime + "-" + "0" + monthTime + "-" + "0" + dayTime;
                    //发起查询
                    presenter.sendGetHistory(startTime, selectType, userName);
                } else if (monthTime.length() == 1) {
                    startTime = yearTime + "-" + "0" + monthTime + "-" + dayTime;
                    //发起查询
                    presenter.sendGetHistory(startTime, selectType, userName);
                } else {
                    startTime = yearTime + "-" + monthTime + "-" + "0" + dayTime;
                    //发起查询
                    presenter.sendGetHistory(startTime, selectType, userName);
                }
            } else {
                startTime = yearTime + "-" + monthTime + "-" + dayTime;
                //发起查询
                presenter.sendGetHistory(startTime, selectType, userName);
            }
        });
    }

    @Override
    public void updateView(List<?> list) {
        viewStub.setVisibility(View.GONE);
        recyclerView.setAdapter(new HistoryRecyclerViewAdapter(list, selectType));
    }

    @Override
    public void updateNoDataView() {
        viewStub.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String msg) {

    }
}
