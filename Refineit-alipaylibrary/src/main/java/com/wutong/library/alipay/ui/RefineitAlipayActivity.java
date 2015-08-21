package com.wutong.library.alipay.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.wutong.library.alipay.RefineitAlipayCode;
import com.wutong.library.alipay.RefineitAlipayLib;
import com.wutong.library.alipay.internal.PayResult;
import com.wutong.library.alipay.internal.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 支付宝
 */
public class RefineitAlipayActivity extends AppCompatActivity {


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(RefineitAlipayActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        sendSharebRroadCast(RefineitAlipayCode.CallBackCode.CODE_VALUE_OK);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(RefineitAlipayActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                            sendSharebRroadCast(RefineitAlipayCode.CallBackCode.CODE_VALUE_COMFIRMIMG);

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(RefineitAlipayActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                            sendSharebRroadCast(RefineitAlipayCode.CallBackCode.CODE_VALUE_FAIL);

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(RefineitAlipayActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }


    };


    private String subject;
    private String body;
    private String price;
    private String outTradeNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window=getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.flags= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        //这句就是设置窗口里崆件的透明度的．０.０全透明．１.０不透明．
        wl.alpha=0.0f;
        window.setAttributes(wl);


        outTradeNo = getIntent().getStringExtra("outTradeNo");
        subject = getIntent().getStringExtra("subject");
        body = getIntent().getStringExtra("body");
        price = getIntent().getStringExtra("price");

        if (TextUtils.isEmpty(outTradeNo) || TextUtils.isEmpty(subject)
                || TextUtils.isEmpty(body) || TextUtils.isEmpty(price)) {
            finish();
        }
        pay();
    }

    /**
     * @param activity   activity
     * @param outTradeNo 商户后台唯一订单号
     * @param subject    标题
     * @param body       描述
     * @param price      价格
     */
    public static void startPay(Activity activity, String outTradeNo, String subject, String body, String price) {
        Intent intent = new Intent(activity, RefineitAlipayActivity.class);
        intent.putExtra("outTradeNo", outTradeNo);
        intent.putExtra("subject", subject);
        intent.putExtra("body", body);
        intent.putExtra("price", price);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    @Override
    public void finish() {
        overridePendingTransition(0, 0);
        super.finish();

    }

    /**
     * 发送分享后的回调广播
     */
    private void sendSharebRroadCast(String errCode) {
        Intent intent = new Intent();
        intent.setAction(RefineitAlipayCode.IntentAction.ALIPAY);
        intent.putExtra(RefineitAlipayCode.CallBackCode.CODE_KEY, errCode);
        sendBroadcast(intent);
        finish();
    }
    /**
     * call alipay sdk pay. 调用SDK支付
     */
    private void pay() {
        // 订单
        String orderInfo = getOrderInfo();

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RefineitAlipayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */
    public void check() {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(RefineitAlipayActivity.this);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo() {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + RefineitAlipayLib.getInstance().getConfiguration().getPartner() + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + RefineitAlipayLib.getInstance().getConfiguration().getSeller() + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + RefineitAlipayLib.getInstance().getConfiguration().getNotify_url()
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"" + RefineitAlipayLib.getInstance().getConfiguration().getLimitPayTime() + "\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"" + RefineitAlipayLib.getInstance().getConfiguration().getReturnUrl() + "\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RefineitAlipayLib.getInstance().getConfiguration().getRsa_private());
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
