package com.wutong.library.alipay;

/**
 * Created by Administrator on 2015/8/21 0021.
 */
public class RefineitAlipayCode {
    /**
     *alipay 的广播 action
     */
    public static class IntentAction {
        /**
         * 支付的action
         */
        public static final String ALIPAY = "intent_action_pay_alipay";

    }

    /**
     * 分享的回调code，key-value
     */
    public static class CallBackCode {
        /**
         * 广播的intent 中 数据的 key
         */
        public static final String CODE_KEY = "intent_action_alipay_errcode";
        /**
         * 广播的intent 中 数据的 value  支付成功
         */
        public static final String CODE_VALUE_OK = "intent_action_alipay_errcode_ok";
//        /**
//         * 广播的intent 中 数据的 value  支付被取消
//         */
//        public static final String CODE_VALUE_CANCEL = "intent_action_alipay_errcode_cancel";
        /**
         * 广播的intent 中 数据的 value  支付失败
         */
        public static final String CODE_VALUE_FAIL = "intent_action_alipay_errcode_fail";
        /**
         * 广播的intent 中 数据的 value  支付确认中
         */
        public static final String CODE_VALUE_COMFIRMIMG = "intent_action_alipay_errcode_comfirming";
    }
}
