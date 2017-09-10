package com.sty.xml;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sty.xml.util.SmsUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_backup;
    private Button btn_restore;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initViews();
        setListeners();
    }

    private void initViews(){
        btn_backup = (Button) findViewById(R.id.btn_backup);
        btn_restore = (Button) findViewById(R.id.btn_restore);
    }

    private void setListeners(){
        btn_backup.setOnClickListener(this);
        btn_restore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_backup:
                //if(SmsUtils.backupSms(mContext)){
                if(SmsUtils.backupSmsByXmlSerializer(mContext)){
                    Toast.makeText(mContext, "短信备份成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "短信备份失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_restore:
                int result = SmsUtils.restoreSms(mContext);
                Toast.makeText(mContext, "成功恢复" + result + "条短信", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
