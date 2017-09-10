package com.sty.xml.bean;

/**
 * Created by Administrator on 2017/9/10/0010.
 * 之所以属性不使用private是因为调用时需要用getter,setter方法，里面用到反射，消耗较多内存
 * 而Android内存是有限的。google建议使用public属性，且其源码也是这样做的
 */

public class SmsBean {

    public int id;
    public String num;
    public String msg;
    public String date;
}
