package com.example.mvp_bill.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvp_bill.Adapter.MyAdapter;
import com.example.mvp_bill.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class BillFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_fragment,container,false);
        TabLayout tabLayout = view.findViewById(R.id.bill_tb);
        ViewPager viewPager = view.findViewById(R.id.bill_vp);
        OutComeFragment outComeFragment = new OutComeFragment();
        InComeFragment inComeFragment = new InComeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userName", getArguments().getString("userName"));
        setArguments(bundle);
        outComeFragment.setArguments(bundle);
        inComeFragment.setArguments(bundle);
        List<Fragment> list = new ArrayList<>();
        list.add(outComeFragment);
        list.add(inComeFragment);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager(), list, new String[]{"支出", "收入"}));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
