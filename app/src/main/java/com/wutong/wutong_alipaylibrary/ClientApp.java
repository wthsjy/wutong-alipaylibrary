package com.wutong.wutong_alipaylibrary;

import android.app.Application;

import com.wutong.library.alipay.RefineitAlipayConfiguration;
import com.wutong.library.alipay.RefineitAlipayLib;

/**
 * Created by Administrator on 2015/8/21 0021.
 */
public class ClientApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RefineitAlipayConfiguration configuration = new RefineitAlipayConfiguration.Builder()
                .configPartner("xxxxxxx")
                .configSeller("xxxxxxx")
                .configRsaPrivate(RSA_PRIVATE)
                .configNotifyUrl(notify_url)
                .build();
        RefineitAlipayLib.getInstance().init(configuration);



    }

    public static final String notify_url ="http://www.baidu.com";

    public static final String RSA_PRIVATE ="";
}
