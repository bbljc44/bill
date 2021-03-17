package com.example.mvp_bill.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ModelImpl implements DataModel {
    @Override
    public void setData(String type,String money,String userName,String category,String remark,OnDataListener listener) {
        if(category.equals("outCome")) {
            OutComeBean outCome = new OutComeBean();
            outCome.setType(type);
            outCome.setMoney(money);
            outCome.setUserName(userName);
            outCome.setRemark(remark);
            outCome.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        listener.onSuccess("","记账成功");
                    } else {
                        listener.onError("记账失败");
                    }
                }
            });
        }else if(category.equals("inCome")){
            InComeBean inCome = new InComeBean();
            inCome.setType(type);
            inCome.setMoney(money);
            inCome.setUserName(userName);
            inCome.setRemark(remark);
            inCome.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e == null){
                        listener.onSuccess("","记账成功");
                    }else{
                        listener.onError("记账失败");
                    }
                }
            });
        }
    }

    @Override
    public void getDurationData(OnDataListener listener,String data_type,String userName,String category) {
        if (data_type.equals("day")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            //获得一个日历，此日历默认获得当前时间
            Calendar calendar = Calendar.getInstance();
            String createdAtStart = sdf.format(calendar.getTime());
            //获得当前时间后一天的时间
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String createdAtEnd = sdf.format(calendar.getTime());
            getDuration(createdAtStart,createdAtEnd,userName,category,sdf,listener);
        }
        else if (data_type.equals("month")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
            //获得一个日历，此日历默认获得当前时间
            Calendar calendar = Calendar.getInstance();
            String createdAtStart = sdf.format(calendar.getTime());
            //获得当前时间后一个月的时间
            calendar.add(Calendar.MONTH, 1);
            String createdAtEnd = sdf.format(calendar.getTime());
            getDuration(createdAtStart,createdAtEnd,userName,category,sdf,listener);
        }else if(data_type.equals("year")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.CHINA);
            //获得一个日历，此日历默认获得当前时间
            Calendar calendar = Calendar.getInstance();
            String createdAtStart = sdf.format(calendar.getTime());
            //获得当前时间后一年的时间
            calendar.add(Calendar.YEAR, 1);
            String createdAtEnd = sdf.format(calendar.getTime());
            getDuration(createdAtStart,createdAtEnd,userName,category,sdf,listener);
        }
    }

    @Override
    public void getHistory(String startTime, String type, String userName, OnDataListener listener) {
        Date createdAtDateStart = null;
        Date createdAtDateEnd = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            createdAtDateStart = sdf.parse(startTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createdAtDateStart);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            String endTime = sdf.format(calendar.getTime());
            createdAtDateEnd = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobQuery bmob1 = new BmobQuery();
        bmob1.addWhereEqualTo("userName",userName);
        BmobDate bmobCreatedAtDateStart = new BmobDate(createdAtDateStart);
        BmobDate bmobCreatedAtDateEnd = new BmobDate(createdAtDateEnd);
        if(type.equals("支出")) {
            BmobQuery<OutComeBean> categoryBmobQueryStart = new BmobQuery<>();
            categoryBmobQueryStart.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart);
            BmobQuery<OutComeBean> categoryBmobQueryEnd = new BmobQuery<>();
            categoryBmobQueryEnd.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd);
            List<BmobQuery<OutComeBean>> queries = new ArrayList<>();
            queries.add(categoryBmobQueryStart);
            queries.add(categoryBmobQueryEnd);
            queries.add(bmob1);
            BmobQuery<OutComeBean> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.and(queries);
            categoryBmobQuery.findObjects(new FindListener<OutComeBean>() {
                @Override
                public void done(List<OutComeBean> object, BmobException e) {
                    if (e == null) {
                        if(object.get(0) != null)
                            listener.onSuccess(object,"");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }else if(type.equals("收入")){
            BmobQuery<InComeBean> categoryBmobQueryStart = new BmobQuery<>();
            categoryBmobQueryStart.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart);
            BmobQuery<InComeBean> categoryBmobQueryEnd = new BmobQuery<>();
            categoryBmobQueryEnd.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd);
            List<BmobQuery<InComeBean>> queries = new ArrayList<>();
            queries.add(categoryBmobQueryStart);
            queries.add(categoryBmobQueryEnd);
            queries.add(bmob1);
            BmobQuery<InComeBean> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.and(queries);
            categoryBmobQuery.findObjects(new FindListener<InComeBean>() {
                @Override
                public void done(List<InComeBean> object, BmobException e) {
                    if (e == null) {
                        if(object.get(0) != null)
                            listener.onSuccess(object,"");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }
    }

    @Override
    public void deleteData(String objectId,String category,OnDataListener listener) {
        if(category.equals("支出")) {
            OutComeBean outCome = new OutComeBean();
            outCome.setObjectId(objectId);
            outCome.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        listener.onSuccess("delete","");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }else{
            InComeBean income = new InComeBean();
            income.setObjectId(objectId);
            income.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        listener.onSuccess("delete","");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }
    }

    @Override
    public void changeData(String objectId,String category,String type, String money, String remark, OnDataListener listener) {
        if(category.equals("支出")) {
            OutComeBean outCome = new OutComeBean();
            outCome.setType(type);
            outCome.setMoney(money);
            outCome.setRemark(remark);
            outCome.update(objectId, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        listener.onSuccess("change","");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }else{
            InComeBean income = new InComeBean();
            income.setType(type);
            income.setMoney(money);
            income.setRemark(remark);
            income.update(objectId, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        listener.onSuccess("change","");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }
    }

    @Override
    public void saveExcelData(List<HashMap<String, String>> list, String category,String userName, OnDataListener listener) {
        List<BmobObject> bills = new ArrayList<>();
        if(category.equals("outCome")) {
            for (int i = 0; i < list.size(); i++) {
                OutComeBean outCome = new OutComeBean();
                outCome.setType(list.get(i).get("种类"));
                outCome.setMoney(list.get(i).get("金额"));
                outCome.setUserName(userName);
                outCome.setRemark(list.get(i).get("备注"));
                bills.add(outCome);
            }
            new BmobBatch().insertBatch(bills).doBatch(new QueryListListener<BatchResult>() {
                @Override
                public void done(List<BatchResult> list, BmobException e) {
                    if(e == null){
                        listener.onSuccess("","");
                    }else{
                        listener.onError("");
                    }
                }
            });
        }else if(category.equals("inCome")){
            for (int i = 0; i < list.size(); i++) {
                InComeBean inCome = new InComeBean();
                inCome.setType(list.get(i).get("种类"));
                inCome.setMoney(list.get(i).get("金额"));
                inCome.setUserName("test");
                inCome.setRemark(list.get(i).get("备注"));
                bills.add(inCome);
            }
            new BmobBatch().insertBatch(bills).doBatch(new QueryListListener<BatchResult>() {
                @Override
                public void done(List<BatchResult> list, BmobException e) {
                    if(e != null){
                        listener.onSuccess("","");
                    }else{
                        listener.onError("");
                    }
                }
            });
        }
    }


    public void getDuration(String createdAtStart,String createdAtEnd,String userName,String category,SimpleDateFormat sdf,OnDataListener listener){
        //获取一个周期间的数据
        Date createdAtDateStart = null;
        Date createdAtDateEnd = null;
        try {
            createdAtDateStart = sdf.parse(createdAtStart);
            createdAtDateEnd = sdf.parse(createdAtEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobDate bmobCreatedAtDateStart = new BmobDate(createdAtDateStart);
        BmobDate bmobCreatedAtDateEnd = new BmobDate(createdAtDateEnd);

        if(category.equals("支出")) {
            BmobQuery<OutComeBean> bomb1 = new BmobQuery<>();
            bomb1.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart);
            BmobQuery<OutComeBean> bomb2 = new BmobQuery<>();
            bomb2.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd);
            BmobQuery<OutComeBean> bomb3 = new BmobQuery<>();
            bomb3.addWhereEqualTo("userName", userName);
            List<BmobQuery<OutComeBean>> queries = new ArrayList<>();
            queries.add(bomb1);
            queries.add(bomb2);
            queries.add(bomb3);
            BmobQuery<OutComeBean> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.and(queries);
            categoryBmobQuery.findObjects(new FindListener<OutComeBean>() {
                @Override
                public void done(List<OutComeBean> object, BmobException e) {
                    if (e == null) {
                        if (object.get(0) != null)
                            listener.onSuccess(object,"");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }else{
            BmobQuery<InComeBean> bomb1 = new BmobQuery<>();
            bomb1.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart);
            BmobQuery<InComeBean> bomb2 = new BmobQuery<>();
            bomb2.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd);
            BmobQuery<InComeBean> bomb3 = new BmobQuery<>();
            bomb3.addWhereEqualTo("userName", userName);
            List<BmobQuery<InComeBean>> queries = new ArrayList<>();
            queries.add(bomb1);
            queries.add(bomb2);
            queries.add(bomb3);
            BmobQuery<InComeBean> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.and(queries);
            categoryBmobQuery.findObjects(new FindListener<InComeBean>() {
                @Override
                public void done(List<InComeBean> object, BmobException e) {
                    if (e == null) {
                        if (object.get(0) != null)
                            listener.onSuccess(object,"");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }
    }

    public void getDeleteData(String type,String userName,String category,OnDataListener listener) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        String start = sdf.format(calendar.getTime());
        BmobDate bmobCreatedAtDateStart = null;
        BmobDate bmobCreatedAtDateEnd = null;
        try {
            bmobCreatedAtDateStart = new BmobDate(sdf.parse(start));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String end = sdf.format(calendar.getTime());
            bmobCreatedAtDateEnd = new BmobDate(sdf.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (category.equals("支出")) {
            BmobQuery<OutComeBean> query = new BmobQuery<>();
            query.addWhereEqualTo("type", type);

            BmobQuery<OutComeBean> query1 = new BmobQuery<>();
            query1.addWhereEqualTo("userName", userName);
            BmobQuery<OutComeBean> query2 = new BmobQuery<>();
            query2.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart);

            BmobQuery<OutComeBean> query3 = new BmobQuery<>();
            query3.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd);

            List<BmobQuery<OutComeBean>> andQuerys = new ArrayList();
            andQuerys.add(query);
            andQuerys.add(query1);
            andQuerys.add(query2);
            andQuerys.add(query3);
            BmobQuery<OutComeBean> queryAll = new BmobQuery<>();
            queryAll.and(andQuerys);
            queryAll.findObjects(new FindListener<OutComeBean>() {
                @Override
                public void done(List<OutComeBean> list, BmobException e) {
                    if (e == null) {
                        listener.onSuccess(list,"");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }else{
            BmobQuery<InComeBean> query = new BmobQuery<>();
            query.addWhereEqualTo("type", type);

            BmobQuery<InComeBean> query1 = new BmobQuery<>();
            query1.addWhereEqualTo("userName", userName);

            BmobQuery<InComeBean> query2 = new BmobQuery<>();
            query2.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart);

            BmobQuery<InComeBean> query3 = new BmobQuery<>();
            query3.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd);

            List<BmobQuery<InComeBean>> andQuerys = new ArrayList();
            andQuerys.add(query);
            andQuerys.add(query1);
            andQuerys.add(query2);
            andQuerys.add(query3);
            BmobQuery<InComeBean> queryAll = new BmobQuery<>();
            queryAll.and(andQuerys);
            queryAll.findObjects(new FindListener<InComeBean>() {
                @Override
                public void done(List<InComeBean> list, BmobException e) {
                    if (e == null) {
                        listener.onSuccess(list,"");
                    } else {
                        listener.onError("");
                    }
                }
            });
        }
    }
}
