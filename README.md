# @Deprecated 
# wutong-alipaylibrary


简化 支付宝 类库

## 功能

- 支付宝支付
- ...
- 持续更新中...


## 如何使用？


#### Step 1: Gradle  依赖

在 build.gradle 中添加:

```groovy
 compile 'com.wutong.library.alipay:Refineit-alipaylibrary:1.0.1'
```

#### Step 2: 在Application 中初始化

```java
public class ClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
         RefineitAlipayConfiguration configuration = new RefineitAlipayConfiguration.Builder()
                .configPartner("Partner")
                .configSeller("Seller")
                .configRsaPrivate("RSA_PRIVATE")
                .configNotifyUrl("notify_url")
                .build();
        RefineitAlipayLib.getInstance().init(configuration);
    }

}

```

#### Step 3: 在 AndroidManifest.xml 中添加权限

```xml
   <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```



#### Step 4: 在 AndroidManifest.xml 添加必要的Activity 注册

```xml
<!-- 支付宝 -->
   <activity
            android:name="com.wutong.library.alipay.ui.RefineitAlipayActivity"
            android:screenOrientation="portrait"
            android:theme="@style/RefineiTranslucent"
            android:launchMode="singleTop"/>


        <!-- alipay sdk begin -->
        <activity
            android:name="com.alipay.sdk.app.H5PayActivity"
            android:configChanges="orientation|keyboardHidden|navigation"
            android:exported="false"
            android:screenOrientation="behind"
            android:windowSoftInputMode="adjustResize|stateHidden"></activity>

 
```


#### Step 5: 调用方法
```java
public void testAlipay(){
    RefineitAlipayLib.getInstance().alipay(MainActivity.this, "" + System.currentTimeMillis() + "", 
    "测试支付公共类", "测试支付公共类body", "0.01");
}
```
    
#### Step 6: 获取回调
- 只需注册广播即可，如下：
- 如果不需要获取回调,step 6 可以忽略

```java
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
```  
## 更新日志

#### v1.0

- 初始导入
 




## 联系方式 QQ: 258176257
