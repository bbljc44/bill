package com.example.mvp_bill.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_bill.R;
import com.example.mvp_bill.presenter.BillPresenterImpl;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OutComeFragment extends Fragment implements View.OnClickListener,BillView{
    private View view;
    private TextView text_sum;
    private EditText popup_et;
    private Button confirm;
    private PopupWindow popupWindow;
    private StringBuilder oldValue = new StringBuilder();
    private StringBuilder newValue = new StringBuilder();
    private String mark = null;
    private boolean oldPointFlag = true;
    private boolean newPointFlag = true;
    private boolean markFlag = false;
    private String type;
    private String userName;
    private DecimalFormat df;

    private BillPresenterImpl presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.outcome_fragment, container, false);
        initView();
        return view;
    }

    private void initView(){
        if(getArguments() != null) {
            userName = getArguments().getString("userName");
        }
        if(presenter == null){
            presenter = new BillPresenterImpl(this);
        }
        //fragment控件初始化
        view.findViewById(R.id.food).setOnClickListener(this);
        view.findViewById(R.id.fruits).setOnClickListener(this);
        view.findViewById(R.id.clothes).setOnClickListener(this);
        view.findViewById(R.id.shop).setOnClickListener(this);
        view.findViewById(R.id.snacks).setOnClickListener(this);
        view.findViewById(R.id.book).setOnClickListener(this);
        view.findViewById(R.id.traffic).setOnClickListener(this);
        view.findViewById(R.id.sports).setOnClickListener(this);
        view.findViewById(R.id.vegetables).setOnClickListener(this);
        view.findViewById(R.id.beauty).setOnClickListener(this);
        view.findViewById(R.id.entertainment).setOnClickListener(this);
        view.findViewById(R.id.house).setOnClickListener(this);
        view.findViewById(R.id.travel).setOnClickListener(this);
        view.findViewById(R.id.communication).setOnClickListener(this);
        view.findViewById(R.id.car).setOnClickListener(this);
        view.findViewById(R.id.medicine).setOnClickListener(this);
        view.findViewById(R.id.elder).setOnClickListener(this);
        view.findViewById(R.id.baby).setOnClickListener(this);
        view.findViewById(R.id.digital).setOnClickListener(this);
        view.findViewById(R.id.donate).setOnClickListener(this);
        view.findViewById(R.id.gifts).setOnClickListener(this);
        view.findViewById(R.id.learn).setOnClickListener(this);
        view.findViewById(R.id.pets).setOnClickListener(this);
        view.findViewById(R.id.others).setOnClickListener(this);

        //初始化popupWindow
        View contentView = LayoutInflater.from(view.getContext()).inflate(R.layout.popup_window,null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setContentView(contentView);

        //初始化popupWindow里面的控件
        text_sum = contentView.findViewById(R.id.popup_sum);
        popup_et = contentView.findViewById(R.id.popup_et);
        contentView.findViewById(R.id.popup_zero).setOnClickListener(this);
        contentView.findViewById(R.id.popup_one).setOnClickListener(this);
        contentView.findViewById(R.id.popup_two).setOnClickListener(this);
        contentView.findViewById(R.id.popup_three).setOnClickListener(this);
        contentView.findViewById(R.id.popup_four).setOnClickListener(this);
        contentView.findViewById(R.id.popup_five).setOnClickListener(this);
        contentView.findViewById(R.id.popup_six).setOnClickListener(this);
        contentView.findViewById(R.id.popup_seven).setOnClickListener(this);
        contentView.findViewById(R.id.popup_eight).setOnClickListener(this);
        contentView.findViewById(R.id.popup_nine).setOnClickListener(this);
        contentView.findViewById(R.id.popup_add).setOnClickListener(this);
        contentView.findViewById(R.id.popup_point).setOnClickListener(this);
        contentView.findViewById(R.id.popup_reduce).setOnClickListener(this);
        contentView.findViewById(R.id.popup_clear).setOnClickListener(this);
        contentView.findViewById(R.id.popup_back).setOnClickListener(this);
        confirm = contentView.findViewById(R.id.popup_confirm);
        confirm.setOnClickListener(this);
        if(df == null){
            df = new DecimalFormat(".00");
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.food:
                type = "餐饮";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.fruits:
                type = "水果";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.clothes:
                type = "衣服";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.shop:
                type = "购物";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.snacks:
                type = "零食";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.book:
                type = "书籍";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.traffic:
                type = "交通";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.sports:
                type = "运动";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.vegetables:
                type = "蔬菜";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.beauty:
                type = "美容";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.entertainment:
                type = "娱乐";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.house:
                type = "住房";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.travel:
                type = "旅游";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.communication:
                type = "通讯";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.car:
                type = "汽车";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.medicine:
                type = "医疗";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.elder:
                type = "长辈";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.baby:
                type = "孩子";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.digital:
                type = "数码";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.donate:
                type = "捐款";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.gifts:
                type = "礼物";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.learn:
                type = "学习";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.pets:
                type = "宠物";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.others:
                type = "其他";
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            //根据键盘输入实现对应功能
            case R.id.popup_zero:
                if (markFlag){
                    newValue.append("0");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("0");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_one:
                if (markFlag){
                    newValue.append("1");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("1");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_two:
                if (markFlag){
                    newValue.append("2");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("2");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_three:
                if (markFlag){
                    newValue.append("3");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("3");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_four:
                if (markFlag){
                    newValue.append("4");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("4");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_five:
                if (markFlag){
                    newValue.append("5");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("5");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_six:
                if (markFlag){
                    newValue.append("6");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("6");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_seven:
                if (markFlag){
                    newValue.append("7");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("7");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_eight:
                if (markFlag){
                    newValue.append("8");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("8");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_nine:
                if (markFlag){
                    newValue.append("9");
                    text_sum.setText(oldValue.toString() + mark + newValue.toString());
                }else {
                    oldValue.append("9");
                    text_sum.setText(oldValue.toString());
                }
                break;
            case R.id.popup_point:
                if(markFlag){
                    if (newPointFlag) {
                        if (newValue.length() == 0) {
                            newValue.append("0.");
                            text_sum.setText(oldValue.toString() + mark + newValue.toString());
                        } else {
                            newValue.append(".");
                            text_sum.setText(oldValue.toString() + mark + newValue.toString());
                        }
                        //输入一次"."之后不能再输
                        newPointFlag = false;
                    }
                }else {
                    if (oldPointFlag) {
                        if (oldValue.length() == 0) {
                            oldValue.append("0.");
                            text_sum.setText(oldValue.toString());
                        } else {
                            oldValue.append(".");
                            text_sum.setText(oldValue.toString());
                        }
                        //输入一次"."之后不能再输
                        oldPointFlag = false;
                    }
                }
                break;
            case R.id.popup_back:
                if(markFlag){
                    if (newValue.length() == 1) {
                        newValue.deleteCharAt(newValue.length() - 1);
                        text_sum.setText(oldValue + mark);
                    } else if (newValue.length() > 1) {
                        if (newValue.toString().endsWith(".")) {
                            //如果以"."结尾，删除后可以再次输".",将flag改为true
                            newPointFlag = true;
                        }
                        newValue.deleteCharAt(newValue.length() - 1);
                        text_sum.setText(oldValue + mark + newValue.toString());
                    }else if(newValue.length() == 0){
                        mark = null;
                        markFlag = false;
                        text_sum.setText(oldValue);
                    }
                }else {
                    if (oldValue.length() == 1) {
                        oldValue.deleteCharAt(oldValue.length() - 1);
                        text_sum.setText("0.00");
                    } else if (oldValue.length() > 1) {
                        if (oldValue.toString().endsWith(".")) {
                            //如果以"."结尾，删除后可以再次输".",将flag改为true
                            oldPointFlag = true;
                        }
                        oldValue.deleteCharAt(oldValue.length() - 1);
                        text_sum.setText(oldValue.toString());
                    }
                }
                break;
            case R.id.popup_add:
                //如果是第二次以上按加号
                if(markFlag && newValue.length() > 0){
                    //判断按加号以前的运算符号是什么
                    if( mark.equals("+")){
                        String total  = df.format(Float.parseFloat(newValue.toString()) + Float.parseFloat(oldValue.toString()));
                        oldValue.delete(0,oldValue.length());
                        newValue.delete(0,newValue.length());
                        oldValue.append(total);
                        newPointFlag = true;
                        mark = "+";
                        text_sum.setText(oldValue.toString() + mark);
                    }else if(mark.equals("-")){
                        String total  = df.format(Float.parseFloat(oldValue.toString()) - Float.parseFloat(newValue.toString()));
                        oldValue.delete(0,oldValue.length());
                        newValue.delete(0,newValue.length());
                        oldValue.append(total);
                        newPointFlag = true;
                        mark = "+";
                        text_sum.setText(oldValue.toString() + mark);
                    }
                }else {
                    //确认按钮换成“=”按钮
                    confirm.setText("=");
                    mark = "+";
                    //表明有符号运算
                    markFlag = true;
                    text_sum.setText(oldValue.toString() + mark);
                }
                break;
            case R.id.popup_reduce:
                //如果是第二次以上按减号
                if(markFlag && newValue.length() > 0){
                    //判断按减号以前的运算符号是什么
                    if( mark.equals("+")){
                        String total  = df.format(Float.parseFloat(newValue.toString()) + Float.parseFloat(oldValue.toString()));
                        oldValue.delete(0,oldValue.length());
                        newValue.delete(0,newValue.length());
                        oldValue.append(total);
                        newPointFlag = true;
                        mark = "-";
                        text_sum.setText(oldValue.toString() + mark);
                    }else if(mark.equals("-")){
                        String total  = df.format(Float.parseFloat(oldValue.toString()) - Float.parseFloat(newValue.toString()));
                        oldValue.delete(0,oldValue.length());
                        newValue.delete(0,newValue.length());
                        oldValue.append(total);
                        newPointFlag = true;
                        mark = "-";
                        text_sum.setText(oldValue.toString() + mark);
                    }
                }else {
                    //确认按钮换成“=”按钮
                    confirm.setText("=");
                    mark = "-";
                    //表明有符号运算
                    markFlag = true;
                    text_sum.setText(oldValue.toString() + mark);
                }
                break;
            case R.id.popup_clear:
                //重置所有标志位和数值
                oldPointFlag = true;
                newPointFlag = true;
                markFlag = false;
                mark = null;
                oldValue.delete(0,oldValue.length());
                newValue.delete(0,newValue.length());
                text_sum.setText("0.00");
                break;
            case R.id.popup_confirm:
                if(confirm.getText().equals("确定")){
                    String remark = popup_et.getText().toString();
                    //todo 通知Model进行数据存储操作
                    presenter.sendSetRequest(type,df.format(Float.parseFloat(text_sum.getText().toString())),userName,"outCome",remark);
                    //重置所有标志和数值
                    oldPointFlag = true;
                    newPointFlag = true;
                    markFlag = false;
                    mark = null;
                    oldValue.delete(0,oldValue.length());
                    newValue.delete(0,newValue.length());
                    text_sum.setText("0.00");
                    //关闭popupWindow
                    popupWindow.dismiss();
                }else if(confirm.getText().equals("=")){
                    if(newValue.length() == 0) {
                        mark = "";
                        markFlag = false;
                        newPointFlag = true;
                        String total = df.format(Float.parseFloat(oldValue.toString()));
                        text_sum.setText(total);
                    }else {
                        //计算出结果并展示出来
                        if (mark.equals("+")) {
                            String total = df.format(Float.parseFloat(newValue.toString()) + Float.parseFloat(oldValue.toString()));
                            oldValue.delete(0, oldValue.length());
                            newValue.delete(0, newValue.length());
                            oldValue.append(total);
                            mark = "";
                            markFlag = false;
                            newPointFlag = true;
                            text_sum.setText(oldValue.toString());
                        } else if (mark.equals("-")) {
                            String total = df.format(Float.parseFloat(oldValue.toString()) - Float.parseFloat(newValue.toString()));
                            oldValue.delete(0, oldValue.length());
                            newValue.delete(0, newValue.length());
                            oldValue.append(total);
                            mark = "";
                            markFlag = false;
                            newPointFlag = true;
                            text_sum.setText(oldValue.toString());
                        }
                    }
                    //按钮换成确定按钮
                    confirm.setText("确定");
                }
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();
    }
}
