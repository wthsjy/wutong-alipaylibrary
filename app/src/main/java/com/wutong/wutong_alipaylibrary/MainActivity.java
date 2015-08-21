package com.wutong.wutong_alipaylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wutong.library.alipay.RefineitAlipayLib;
import com.wutong.share.library.RefineitShareLib;

public class MainActivity extends AppCompatActivity {

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


}
