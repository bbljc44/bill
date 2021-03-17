package com.example.mvp_bill.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.bmob.v3.BmobUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvp_bill.Adapter.MyAdapter;
import com.example.mvp_bill.R;
import com.example.mvp_bill.model.ModelImpl;
import com.example.mvp_bill.model.OnDataListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDataListener {

    private final static int SUCCESS_GET_URI = 1;
    private DrawerLayout drawerLayout;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = {"消费统计", "记账", "历史查询"};
    private ModelImpl model;
    private String userName;
    private String chooseFlag;
    private TextView drawer_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (model == null) {
            model = new ModelImpl();
        }
        userName = getIntent().getStringExtra("userName");
        AnalysisFragment analysisFragment = new AnalysisFragment();
        BillFragment billFragment = new BillFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        //传递userName给fragment
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        analysisFragment.setArguments(bundle);
        billFragment.setArguments(bundle);
        historyFragment.setArguments(bundle);

        drawerLayout = findViewById(R.id.drawer);
        ViewPager vp = findViewById(R.id.main_vp);
        TabLayout tabLayout = findViewById(R.id.main_tb);
        drawer_userName = findViewById(R.id.drawer_userName);
        drawer_userName.setText(userName);
        Button drawer_change_user = findViewById(R.id.drawer_change_user);

        drawer_change_user.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
            BmobUser.logOut();
            finish();
        });
        Button drawer_get_excel = findViewById(R.id.drawer_get_excel);
        drawer_get_excel.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setMessage("请选择导出收入或支出表")
                    .setPositiveButton("支出", (dialogInterface, i) -> {
                        chooseFlag = "支出";
                        model.getDurationData(MainActivity.this, "year", userName, chooseFlag);
                    })
                    .setNegativeButton("收入", (dialogInterface, i) -> {
                        chooseFlag = "收入";
                        model.getDurationData(MainActivity.this, "year", userName, chooseFlag);
                    }).show();
            chooseFlag = null;
        });
        Button drawer_read_excel = findViewById(R.id.drawer_read_excel);
        drawer_read_excel.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            new AlertDialog.Builder(this)
                    .setMessage("请选择导入收入或支出表")
                    .setPositiveButton("支出", (dialogInterface, i) -> {
                        chooseFlag = "outCome";
                        startActivityForResult(intent, SUCCESS_GET_URI);
                    })
                    .setNegativeButton("收入", (dialogInterface, i) -> {
                        chooseFlag = "inCome";
                        startActivityForResult(intent, SUCCESS_GET_URI);
                    }).show();
        });
        fragmentList.add(analysisFragment);
        fragmentList.add(billFragment);
        fragmentList.add(historyFragment);
        tabLayout.setupWithViewPager(vp);
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(new MyAdapter(getSupportFragmentManager(),fragmentList,titles));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //toolbar 布局
        getMenuInflater().inflate(R.menu.top_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //toolbar 点击事件
        if (item.getItemId() == R.id.user_setting) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onSuccess(String type,String msg) {

    }

    @Override
    public void onSuccess(List<?> list,String msg) {

    }

    @Override
    public void onError(String msg) {

    }
}
