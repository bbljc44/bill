package com.example.mvp_bill.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import com.example.mvp_bill.Adapter.MyRecyclerViewAdapter;
import com.example.mvp_bill.R;
import com.example.mvp_bill.model.InComeBean;
import com.example.mvp_bill.model.OutComeBean;
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

public class DayFragment extends Fragment implements AnalysisView{
    private final int[] PIE_COLORS = {
            Color.rgb(181, 194, 202), Color.rgb(129, 216, 200), Color.rgb(241, 214, 145),
            Color.rgb(108, 176, 223), Color.rgb(195, 221, 155), Color.rgb(150, 120, 200),
            Color.rgb(15, 120, 200), Color.rgb(200, 70, 130), Color.rgb(170, 20, 20),
            Color.rgb(11, 20, 20), Color.rgb(140, 37, 168), Color.rgb(251, 123, 213)};
    private View view;
    private String userName;
    private Map<String, String> map;
    private List<String> typeList;
    private DecimalFormat df;
    private ViewStub viewStub;
    private String category;

    AnalysisPresenterImpl presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.day_fragment, container, false);
        initView();
        return view;
    }

    private void initView() {
        if(presenter == null){
            presenter = new AnalysisPresenterImpl(this);
        }
        map = new HashMap<>();
        if (df == null) {
            df = new DecimalFormat(".00");
        }
        userName = getArguments().getString("userName");
        viewStub = view.findViewById(R.id.day_vs);
        viewStub.inflate();
        viewStub.setVisibility(View.GONE);
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.day_refresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(() -> {
            presenter.sendGetDuration("day",userName,category);
            Toast.makeText(view.getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            refreshLayout.setRefreshing(false);
        });
    }

    private void init(){
        //初始化数据，然后更新数据
        List<Float> dataList = new ArrayList<>();
        for (String key : map.keySet()) {
            dataList.add(Float.parseFloat(map.get(key)));
        }
        PieChart pieChart = view.findViewById(R.id.day_pc);
        pieChart.setUsePercentValues(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("消费分析");
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
        PieDataSet pieDataSet = new PieDataSet(pc_data, "种类");
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setSliceSpace(0f);
        pieDataSet.setColors(PIE_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(pieData);
        RecyclerView recyclerView = view.findViewById(R.id.day_rv);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(map, typeList);
        adapter.setOnItemClickListener((view, holder) -> {
            Intent intent = new Intent(view.getContext(),DeleteViewImpl.class);
            intent.putExtra("type", holder.iv_text.getText());
            intent.putExtra("userName", userName);
            intent.putExtra("category", category);
            view.getContext().startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        viewStub.setVisibility(View.GONE);
        //模拟点击，不点击不刷新
        pieChart.performClick();
    }

    @Override
    public void updateView(List<?> list) {
        map.clear();
        if (category.equals("支出")) {
            //获取种类名称
            typeList = new ArrayList<>();
            for (OutComeBean i : (List<OutComeBean>) list) {
                if (!typeList.contains(i.getType())) {
                    typeList.add(i.getType());
                }
            }
            //把重复种类的数据合并起来
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
            //获取种类名称
            typeList = new ArrayList<>();
            for (InComeBean i : (List<InComeBean>) list) {
                if (!typeList.contains(i.getType())) {
                    typeList.add(i.getType());
                }
            }
            //把重复种类的数据合并起来
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

    @Override
    public void updateNoView() {
        viewStub.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }
}


