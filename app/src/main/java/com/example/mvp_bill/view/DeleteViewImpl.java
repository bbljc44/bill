package com.example.mvp_bill.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.mvp_bill.Adapter.HistoryRecyclerViewAdapter;
import com.example.mvp_bill.R;
import com.example.mvp_bill.model.InComeBean;
import com.example.mvp_bill.model.OutComeBean;
import com.example.mvp_bill.presenter.DeletePresenterImpl;

import java.util.List;

public class DeleteViewImpl extends AppCompatActivity implements DeleteView{
    private RecyclerView recyclerView;
    private HistoryRecyclerViewAdapter adapter;
    private int pos;
    private List<?> list;
    private EditText change_type;
    private EditText change_money;
    private EditText change_remark;
    private String type;
    private String category;
    private String money;
    private String remark;

    private DeletePresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_layout);
        initView();
        String type = getIntent().getStringExtra("type");
        String userName = getIntent().getStringExtra("userName");
        category = getIntent().getStringExtra("category");
        presenter.sendGetDeleteData(type, userName,category);
    }

    private void initView(){
        if(presenter == null){
            presenter = new DeletePresenterImpl(this);
        }
        recyclerView = findViewById(R.id.del_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void updateView(List<?> list) {
        this.list = list;
        adapter = new HistoryRecyclerViewAdapter(list, category);
        if (category.equals("支出")) {
            adapter.setOnItemClickListener((holder, position) -> {
                //TODO 最好搞个动画效果（向左拖动，右边出现删除按钮，再出现对话框）
                //此处Context需要用当前Activity,使用getApplicationContext会报错。
                pos = position;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("要删除或者修改数据吗？")
                        .setCancelable(true)
                        .setNegativeButton("修改", (dialogInterface, i) -> {
                            View changeView = LayoutInflater.from(this).inflate(R.layout.change_data_layout, null);
                            change_money = changeView.findViewById(R.id.change_money);
                            change_remark = changeView.findViewById(R.id.change_remark);
                            change_type = changeView.findViewById(R.id.change_type);
                            change_type.setText(((List<OutComeBean>) list).get(position).getType());
                            change_money.setText(((List<OutComeBean>) list).get(position).getMoney());
                            change_remark.setText(((List<OutComeBean>) list).get(position).getRemark());
                            new AlertDialog.Builder(this)
                                    .setTitle("修改界面")
                                    .setView(changeView)
                                    .setPositiveButton("确定", (dialogInterface1, i1) -> {
                                        type = change_type.getText().toString();
                                        money = change_money.getText().toString();
                                        remark = change_remark.getText().toString();
                                        presenter.sendChangeData(((List<OutComeBean>) list).get(position).getObjectId(), type, money, remark, category);
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        })
                        .setPositiveButton("删除", (dialogInterface, i) -> presenter.sendDeleteData(((List<OutComeBean>) list).get(position).getObjectId(), category));
                if (((List<OutComeBean>) list).get(position).getRemark().equals("")) {
                    alertDialog.show();
                } else {
                    alertDialog.setMessage("备注：" + ((List<OutComeBean>) list).get(position).getRemark()).show();
                }
            });
        } else {
            adapter.setOnItemClickListener((holder, position) -> {
                //TODO 最好搞个动画效果（向左拖动，右边出现删除按钮，再出现对话框） 计算器还有点问题
                //此处Context需要用当前Activity,使用getApplicationContext会报错。
                pos = position;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("要删除或者修改数据吗？")
                        .setCancelable(true)
                        .setNegativeButton("修改", (dialogInterface, i) -> {
                            View changeView = LayoutInflater.from(this).inflate(R.layout.change_data_layout, null);
                            change_money = changeView.findViewById(R.id.change_money);
                            change_remark = changeView.findViewById(R.id.change_remark);
                            change_type = changeView.findViewById(R.id.change_type);
                            change_type.setText(((List<InComeBean>) list).get(position).getType());
                            change_money.setText(((List<InComeBean>) list).get(position).getMoney());
                            change_remark.setText(((List<InComeBean>) list).get(position).getRemark());
                            new AlertDialog.Builder(this)
                                    .setTitle("修改界面")
                                    .setView(changeView)
                                    .setPositiveButton("确定", (dialogInterface1, i1) -> {
                                        type = change_type.getText().toString();
                                        money = change_money.getText().toString();
                                        remark = change_remark.getText().toString();
                                        presenter.sendChangeData(((List<InComeBean>) list).get(position).getObjectId(), type, money, remark, category);
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        })
                        .setPositiveButton("删除", (dialogInterface, i) -> presenter.sendDeleteData(((List<InComeBean>) list).get(position).getObjectId(), category));
                if (((List<InComeBean>) list).get(position).getRemark().equals("")) {
                    alertDialog.show();
                } else {
                    alertDialog.setMessage("备注：" + ((List<InComeBean>) list).get(position).getRemark()).show();
                }
            });
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateDeleteView() {
        //notify需要对原来放入adapter当参数的列表进行修改之后才可以实现刷新。
        list.remove(pos);
        adapter.notifyDataSetChanged();
        //使用下面的方法第一次删除会屏幕闪退，不知道为什么
//        adapter.notifyItemChanged(pos);
    }

    @Override
    public void updateChangeView() {
        if (category.equals("支出")) {
            ((List<OutComeBean>) list).get(pos).setType(type);
            ((List<OutComeBean>) list).get(pos).setMoney(money);
            ((List<OutComeBean>) list).get(pos).setRemark(remark);
            adapter.notifyDataSetChanged();
        } else {
            ((List<InComeBean>) list).get(pos).setType(type);
            ((List<InComeBean>) list).get(pos).setMoney(money);
            ((List<InComeBean>) list).get(pos).setRemark(remark);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showToast(String msg) {

    }
}
