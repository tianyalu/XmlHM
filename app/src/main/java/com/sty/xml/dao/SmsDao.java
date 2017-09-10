package com.sty.xml.dao;

import com.sty.xml.bean.SmsBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/10/0010.
 */

public class SmsDao {

    //获取短信数据，模拟一些假数据
    public static ArrayList<SmsBean> getAllSms(){
        ArrayList<SmsBean> arrayList = new ArrayList<>();

        SmsBean smsBean = new SmsBean();
        smsBean.id = 1;
        smsBean.num = "110";
        smsBean.msg = "来警局做个笔录。";
        smsBean.date = "2017-08-29";
        arrayList.add(smsBean);

        SmsBean smsBean1 = new SmsBean();
        smsBean1.id = 2;
        smsBean1.num = "120";
        smsBean1.msg = "最近咋样？";
        smsBean1.date = "2017-08-29";
        arrayList.add(smsBean1);

        SmsBean smsBean2 = new SmsBean();
        smsBean2.id = 3;
        smsBean2.num = "119";
        smsBean2.msg = "火灭了吗？";
        smsBean2.date = "2017-08-29";
        arrayList.add(smsBean2);

        return arrayList;
    }
}
