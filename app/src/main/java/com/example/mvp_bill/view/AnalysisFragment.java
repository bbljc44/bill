package com.example.mvp_bill.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.mvp_bill.Adapter.MyAdapter;
import com.example.mvp_bill.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class AnalysisFragment extends Fragment {
    private String categories[] = {"支出", "收入"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ana_fragment, container, false);
        TabLayout tabLayout = view.findViewById(R.id.ana_tb);
        Spinner spinner = view.findViewById(R.id.ana_spinner);
        String userName = getArguments().getString("userName");
        //传值
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        DayFragment dayFragment = new DayFragment();
        MonthFragment monthFragment = new MonthFragment();
        YearFragment yearFragment = new YearFragment();
        dayFragment.setArguments(bundle);
        monthFragment.setArguments(bundle);
        yearFragment.setArguments(bundle);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(dayFragment);
        fragmentList.add(monthFragment);
        fragmentList.add(yearFragment);
        ViewPager vp = view.findViewById(R.id.ana_vp);
        //viewPager缓存
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(new MyAdapter(getChildFragmentManager(), fragmentList, new String[]{"日分析", "月分析", "年分析"}));
        tabLayout.setupWithViewPager(vp);
        //下拉框
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dayFragment.presenter.sendGetDuration("day", userName, categories[i]);
                dayFragment.setCategory(categories[i]);
                monthFragment.presenter.sendGetDuration("month", userName, categories[i]);
                monthFragment.setCategory(categories[i]);
                yearFragment.presenter.sendGetDuration("year", userName, categories[i]);
                yearFragment.setCategory(categories[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
}
