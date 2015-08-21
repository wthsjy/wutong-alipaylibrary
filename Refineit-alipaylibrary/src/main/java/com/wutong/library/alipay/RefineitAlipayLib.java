package com.wutong.library.alipay;

import android.app.Activity;
import android.util.Log;

import com.wutong.library.alipay.ui.RefineitAlipayActivity;

/**
 * 支付宝工具类
 * Created by wutong on 2015/8/21 0021.
 */
public class RefineitAlipayLib {

    private static final String TAG = "RefineitAlipayLib";
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "RefineitAlipayConfiguration 不能为 null";
    private static final String LOG_INIT_CONFIG = "RefineitAlipayConfiguration 初始化成功";
    private static final java.lang.String WARNING_RE_INIT_CONFIG = "警告：RefineitAlipayConfiguration 重复初始化";
    private static final String ERROR_CONFIG_WITH_NULL = "RefineitAlipayConfiguration 没有初始化";


    private volatile static RefineitAlipayLib instance;
    private RefineitAlipayConfiguration configuration;


    public static RefineitAlipayLib getInstance() {
        if (instance == null) {
            synchronized (RefineitAlipayLib.class) {
                if (instance == null) {
                    instance = new RefineitAlipayLib();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param configuration configuration
     */
    public void init(RefineitAlipayConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        if (this.configuration == null) {
            Log.d(TAG, LOG_INIT_CONFIG);
            this.configuration = configuration;
        } else {
            Log.d(TAG, WARNING_RE_INIT_CONFIG);
        }

    }

    public RefineitAlipayConfiguration getConfiguration() {
        if (configuration == null) {
            throw new IllegalArgumentException(ERROR_CONFIG_WITH_NULL);
        }
        return configuration;
    }

    /**
     * @param activity   activity
     * @param outTradeNo 商户后台唯一订单号
     * @param subject    标题
     * @param body       描述
     * @param price      价格
     */
    public void alipay(Activity activity, String outTradeNo, String subject, String body, String price) {
        RefineitAlipayActivity.startPay(activity, outTradeNo, subject, body, price);
    }
}
