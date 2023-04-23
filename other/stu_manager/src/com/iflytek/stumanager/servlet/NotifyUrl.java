package com.iflytek.stumanager.servlet;

import com.google.gson.Gson;
import io.goeasy.GoEasy;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

//微信支付的回调url不能携带参数
@WebServlet("/notify_url")
public class NotifyUrl extends HttpServlet {
    @Override
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

        ServletInputStream inputStream = request.getInputStream();//以字节流的方式获取微信后台返回的xml格式的数据
        byte[] b = new byte[1024];
        int len=0;
        while((len=inputStream.read(b))!=-1){
            String str = new String(b,0,len);//把一个字节数组b从0取到len,取出来之后转换成String类型
            System.out.print(str);
        }
        response.setContentType("text/html;charset=UTF-8");
        //商户处理后同步返回给微信参数
        response.getWriter().println("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
        //Goeasy实现异步支付结果
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-b232d753ee1d44fe92c249671bd87e18");
        //给前端my_channel频道推送
        goEasy.publish("my_channel", "Hello, GoEasy!");

    }
}
