package com.iflytek.stumanager.sdk;

import java.io.InputStream;

public class MyWXPayConfig extends WXPayConfig{
    String getAppID() {//应用ID
        return "wx632c8f211f8122c6";
    }

    String getMchID() {//商户ID
        return "1497984412";
    }

    String getKey() {//键值
        return "sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC";
    }

    InputStream getCertStream() {//证书
        return null;
    }

    IWXPayDomain getWXPayDomain() {//域名
        MyWXPayDoMain myWXPayDoMain = new MyWXPayDoMain();
        return myWXPayDoMain;
    }
}
