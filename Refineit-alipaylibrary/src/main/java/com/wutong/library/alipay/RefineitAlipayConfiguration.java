package com.wutong.library.alipay;

/**
 * Created by Administrator on 2015/8/21 0021.
 */
public class RefineitAlipayConfiguration {
    //商户PID
    private String partner = "";
    //商户收款账号
    private String seller = "";
    //商户私钥，pkcs8格式
    private String rsa_private = "";
    //支付宝公钥
    private String rsa_public = "";
    //服务器异步通知地址
    private String notify_url = "";
    //有效的支付时间
    private String limitPayTime = "";
    //支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
    private String returnUrl = "";


    private RefineitAlipayConfiguration(final Builder builder) {
        this.partner = builder.partner;
        this.seller = builder.seller;
        this.rsa_private = builder.rsa_private;
        this.rsa_public = builder.rsa_public;
        this.notify_url = builder.notify_url;

        this.limitPayTime = builder.limitPayTime;
        this.returnUrl= builder.returnUrl;
    }

    public String getPartner() {
        return partner;
    }

    public String getSeller() {
        return seller;
    }

    public String getRsa_private() {
        return rsa_private;
    }

    public String getRsa_public() {
        return rsa_public;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getLimitPayTime() {
        return limitPayTime;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public static class Builder {
        //商户PID
        String partner = "";
        //商户收款账号
        String seller = "";
        //商户私钥，pkcs8格式
        String rsa_private = "";
        //支付宝公钥
        String rsa_public = "";
        //服务器异步通知地址
        String notify_url = "";
        //有效支付时间
        String limitPayTime = "30m";
        //支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        String returnUrl = "m.alipay.com";


        public Builder configPartner(String partner) {
            this.partner = partner;
            return this;
        }

        public Builder configSeller(String seller) {
            this.seller = seller;
            return this;
        }

        public Builder configRsaPrivate(String rsa_private) {
            this.rsa_private = rsa_private;
            return this;
        }

        public Builder configRsaPublic(String rsa_public) {
            this.rsa_public = rsa_public;
            return this;
        }

        public Builder configNotifyUrl(String notify_url) {
            this.notify_url = notify_url;
            return this;
        }

        public Builder configLimitPayTime(String limitPayTime) {
            this.limitPayTime = limitPayTime;
            return this;
        }

        public Builder configReturnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
            return this;
        }
        public RefineitAlipayConfiguration build() {
            return new RefineitAlipayConfiguration(Builder.this);
        }
    }
}
