package com.example.mvp_bill.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import com.example.mvp_bill.Adapter.MyRecyclerViewAdapter;
import com.example.mvp_bill.R;
import com.example.mvp_bill.model.InComeBean;
import com.example.mvp_bill.model.OutComeBean;
import com.example.mvp_bill.presenter.AnalysisPresenter;
import com.example.mvp_bill.presenter.AnalysisPresenterImpl;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MonthFragment extends Fragment implements AnalysisView{

    private final int[] PIE_COLORS = {
            Color.rgb(181, 194, 202), Color.rgb(129, 216, 200), Color.rgb(241, 214, 145),
            Color.rgb(108, 176, 223), Color.rgb(195, 221, 155), Color.rgb(150, 120, 200),
            Color.rgb(15, 120, 200), Color.rgb(200, 70, 130), Color.rgb(170, 20, 20),
            Color.rgb(11, 20, 20), Color.rgb(140, 37, 168), Color.rgb(251, 123, 213)};
    private View view;
    private String userName;
    private List<String> typeList;
    private Map<String, String> map;
    private DecimalFormat df;
    private ViewStub viewStub;
    private String category;

    public AnalysisPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.month_fragment, container, false);
        initView();
        return view;
    }

    private void initView(){
        if(presenter == null){
            presenter = new AnalysisPresenterImpl(this);
        }
        map = new HashMap<>();
        if (df == null) {
            df = new DecimalFormat(".00");
        }
        userName = getArguments().getString("userName");
        viewStub = view.findViewById(R.id.month_vs);
        viewStub.inflate();
        viewStub.setVisibility(View.GONE);
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.month_refresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(() -> {
            presenter.sendGetDuration("month", userName, category);
            refreshLayout.setRefreshing(false);
            Toast.makeText(view.getContext(), "????????????", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void updateNoView() {
        viewStub.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void updateView(List<?> list) {
        map.clear();
        if (category.equals("??????")) {
            //??????????????????
            typeList = new ArrayList<>();
            for (OutComeBean i : (List<OutComeBean>) list) {
                if (!typeList.contains(i.getType())) {
                    typeList.add(i.getType());
                }
            }
            //????????????????????????????????????
            for (int i = 0; i < list.size(); i++) {
                if (map.containsKey(((List<OutComeBean>) list).get(i).getType())) {
                    String beforeMoney = map.get(((List<OutComeBean>) list).get(i).getType());
                    map.remove(((List<OutComeBean>) list).get(i).getType());
                    String nowMoney = df.format(Float.parseFloat(beforeMoney) + Float.parseFloat(((List<OutComeBean>) list).get(i).getMoney()));
                    map.put(((List<OutComeBean>) list).get(i).getType(), nowMoney);
                } else {
                    map.put(((List<OutComeBean>) list).get(i).getType(), ((List<OutComeBean>) list).get(i).getMoney());
                }
            }
            init();
        } else {
            //??????????????????
            typeList = new ArrayList<>();
            for (InComeBean i : (List<InComeBean>) list) {
                if (!typeList.contains(i.getType())) {
                    typeList.add(i.getType());
                }
            }
            //????????????????????????????????????
            for (int i = 0; i < list.size(); i++) {
                if (map.containsKey(((List<InComeBean>) list).get(i).getType())) {
                    String beforeMoney = map.get(((List<InComeBean>) list).get(i).getType());
                    map.remove(((List<InComeBean>) list).get(i).getType());
                    String nowMoney = df.format(Float.parseFloat(beforeMoney) + Float.parseFloat(((List<InComeBean>) list).get(i).getMoney()));
                    map.put(((List<InComeBean>) list).get(i).getType(), nowMoney);
                } else {
                    map.put(((List<InComeBean>) list).get(i).getType(), ((List<InComeBean>) list).get(i).getMoney());
                }
            }
            init();
        }
    }

    private void init() {
        List<Float> dataList = new ArrayList<>();
        for (String key : map.keySet()) {
            dataList.add(Float.parseFloat(map.get(key)));
        }
        PieChart pieChart = view.findViewById(R.id.month_pc);
        pieChart.setUsePercentValues(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("????????????");
        pieChart.setDescription(null);
        ArrayList<String> pc_titles = new ArrayList<>();
        int i = 0;
        for (String key : map.keySet()) {
            pc_titles.add(i++, key);
        }
        ArrayList<PieEntry> pc_data = new ArrayList<>();
        for (String key : map.keySet()) {
            pc_data.add(new PieEntry(Float.parseFloat(map.get(key)), key));
        }
        PieDataSet pieDataSet = new PieDataSet(pc_data, "??????");
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setSliceSpace(0f);
        pieDataSet.setColors(PIE_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(pieData);
        RecyclerView recyclerView = view.findViewById(R.id.month_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(map, typeList));
        viewStub.setVisibility(View.GONE);
    }

}
