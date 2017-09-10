package com.sty.xml.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;

import com.sty.xml.bean.SmsBean;
import com.sty.xml.dao.SmsDao;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/10/0010.
 */

public class SmsUtils {

    //备份的逻辑
    public static boolean backupSms(Context context){
        //获取短信内容，list
        ArrayList<SmsBean> allSms = SmsDao.getAllSms();

        //将数据以xml格式封装到一个StringBuffer中
        StringBuffer sb = new StringBuffer();

        //封装一个声明头
        sb.append("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
        //封装根节点
        sb.append("<Smss>");
        //循环遍历list集合中封装的所有短信
        for(SmsBean smsBean : allSms){
            sb.append("<Sms id = \"" + smsBean.id + "\">");

            sb.append("<num>");
            sb.append(smsBean.num);
            sb.append("</num>");

            sb.append("<msg>");
            sb.append(smsBean.msg);
            sb.append("</msg>");

            sb.append("<date>");
            sb.append(smsBean.date);
            sb.append("</date>");

            sb.append("</Sms>");
        }
        sb.append("</Smss>");
        try {
            //将stringBuffer中的xml字符串写入私有目录中的文件
            FileOutputStream openFileOutput = context.openFileOutput("backupsms.xml", Context.MODE_PRIVATE);
            openFileOutput.write(sb.toString().getBytes());
            openFileOutput.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 使用XmlSerializer来序列化xml文件
     * @param context
     * @return
     */
    public static boolean backupSmsByXmlSerializer(Context context){
        try {
        //0.获取短信数据
            ArrayList<SmsBean> allSms = SmsDao.getAllSms();
        //1.通过Xml创建一个XmlSerializer对象
            XmlSerializer xs = Xml.newSerializer();
        //2.设置XmlSerializer的一些参数，比如：设置xml写入到哪个文件中
            //os:xml文件写入流   encoding:流的编码
            xs.setOutput(context.openFileOutput("backupsms2.xml", Context.MODE_PRIVATE), "utf-8");
        //3.序列化一个xml的声明头
            //encoding: xml文件的编码 standalone:是否独立
            xs.startDocument("utf-8", true);
        //4.序列化一个根节点的开始节点
            //namespace:命名空间 name:标签的名称
            xs.startTag(null, "Smss");
        //5.循环遍历list集合序列化一条条短信
            for(SmsBean smsBean : allSms){
                xs.startTag(null, "Sms");
                //name:属性的名称 value:属性值
                xs.attribute(null, "id", smsBean.id + "");

                xs.startTag(null, "num");
                //写一个标签的内容
                xs.text(smsBean.num);
                xs.endTag(null, "num");

                xs.startTag(null, "msg");
                xs.text(smsBean.msg);
                xs.endTag(null, "msg");

                xs.startTag(null, "date");
                xs.text(smsBean.date);
                xs.endTag(null, "date");

                xs.endTag(null, "Sms");
            }
        //6.序列化一个根节点的结束节点
            xs.endTag(null, "Smss");
        //7.将xml写入到文件中，完成xml的序列化
            xs.endDocument();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解析xml文件读取短信内容
     * @param context
     * @return 成功解析的短信条数
     */
    public static int restoreSms(Context context) {
        ArrayList<SmsBean> arrayList = null;
        SmsBean smsBean = null;
        try {
            //1.通过Xml获取一个XmlPullParser对象
            XmlPullParser xpp = Xml.newPullParser();
            //2.设置XmlPullParser对象的参数，需要解析的是哪个xml文件，设置一个文件读取流

            AssetManager assets = context.getAssets(); //通过context获取一个资产管理对象
            InputStream inputStream = assets.open("backupsms.xml"); //通过资产管理者对象获取一个文件读取流
            xpp.setInput(inputStream, "utf-8");
            //xpp.setInput(context.openFileInput("backupsms2.xml"), "utf-8");

            //3.获取当前xml行的事件类型
            int type = xpp.getEventType();
            //4.判断当前事件类型是否为文档结束的事件类型
            while (type != XmlPullParser.END_DOCUMENT) {
                //5.如果不是，循环遍历解析每一行的数据，解析一行后，获取下一行的事件类型
                String currentTagName = xpp.getName();
                //判断当前行的事件类型是开始标签还是结束标签
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if (currentTagName.equals("Smss")) {
                            arrayList = new ArrayList<SmsBean>();
                        } else if (currentTagName.equals("Sms")) {
                            smsBean = new SmsBean();
                            smsBean.id = Integer.valueOf(xpp.getAttributeValue(null, "id"));
                        } else if (currentTagName.equals("num")) {
                            smsBean.num = xpp.nextText();
                        } else if (currentTagName.equals("msg")) {
                            smsBean.msg = xpp.nextText();
                        } else if (currentTagName.equals("date")) {
                            smsBean.date = xpp.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //6.将解析的数据封装到list中
                        //当前结束标签是Sms的话，一条短信封装完成，可以加入list中
                        if (currentTagName.equals("Sms")) {
                            arrayList.add(smsBean);
                        }
                        break;
                    default:
                        break;
                }
                type = xpp.next();
            }
            return arrayList.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
