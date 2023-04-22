package com.iflytek.stumanager.servlet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.iflytek.stumanager.sdk.MyWXPayConfig;
import com.iflytek.stumanager.sdk.WXPay;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/wxpay")
public class WXPayController extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* 允许跨域的主机地址 */
        response.setHeader("Access-Control-Allow-Origin", "*");//解决浏览器的同源策略问题
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        response.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        response.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        response.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        response.setHeader("Access-Control-Allow-Credentials", "true");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
        String orderIDPrefix = sdf.format(date);//订单编号的前缀
        String pID = "2011";//商品ID
        String orderID = orderIDPrefix+pID;//订购id


        MyWXPayConfig config = new MyWXPayConfig();
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(config);//通过商家的配置信息得到一个微信支付对象
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "微信支付学习案例");
            data.put("out_trade_no", orderID);//订单编号
            data.put("device_info", "");//设备信息
            data.put("fee_type", "CNY");//金额单位
            data.put("total_fee", "1");//1分
            data.put("spbill_create_ip", "123.12.12.123");//过滤ip
            data.put("notify_url", "http://3nrpm9.natappfree.cc/stu_manager/notify_url");//回调url
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", pID);//商品ID
            //发起支付请求
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);//发起支付请求后返回code_url等信息
            String code_url = resp.get("code_url");
            //Zxing二维码生成
            HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, 2);
            try {
                BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE,
                        200, 200, hints);
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", response.getOutputStream());//输出二维码
                System.out.println("创建二维码完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
