package com.wutong.wutong_alipaylibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wutong.library.alipay.RefineitAlipayCode;
import com.wutong.library.alipay.RefineitAlipayLib;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver myBroadCastReciver = new MyBroadCastReciver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefineitAlipayLib.getInstance().alipay(MainActivity.this, "" + System.currentTimeMillis() + "", "测试支付公共类", "测试支付公共类body", "0.01");
            }
        });


    }

    public void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(RefineitAlipayCode.IntentAction.ALIPAY);
        registerReceiver(myBroadCastReciver, filter);
    }

    private class MyBroadCastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //分享的action
            String action = intent.getAction();
            //分享的状态value
            String value = intent.getStringExtra(RefineitAlipayCode.CallBackCode.CODE_KEY);

            if (RefineitAlipayCode.IntentAction.ALIPAY.equals(action)) {
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                //TODO 此处按具体场景对value 进行判断，value有以下3个值
                //TODO RefineitAlipayCode.CallBackCode.CODE_VALUE_OK，
                //TODO RefineitAlipayCode.CallBackCode.CODE_VALUE_FAIL,
                //TODO RefineitAlipayCode.CallBackCode.CODE_VALUE_COMFIRMIMG


            }
        }
    }


}
