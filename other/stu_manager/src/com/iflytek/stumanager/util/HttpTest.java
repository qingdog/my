// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpTest.java

package com.iflytek.stumanager.util;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpTest
{

    public HttpTest()
    {
        set = new HashSet();
        p = Pattern.compile("\\w+@\\w+[.](com|cn|net)");
    }

    public static void main(String args[])
    {
        HttpTest client = new HttpTest();
        client.run();
    }

    public void run()
    {
        try
        {
            URL url = new URL("https://tieba.baidu.com/p/2954092023/");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line;
            while((line = br.readLine()) != null) 
            {
                for(Matcher m = p.matcher(line); m.find(); set.add(m.group()));
            }
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        System.out.println((new StringBuilder("\u90AE\u7BB1\u4E2A\u6570\u4E3A\uFF1A")).append(set.size()).toString());
        String s;
        for(Iterator iterator = set.iterator(); iterator.hasNext(); System.out.println(s.toString()))
            s = (String)iterator.next();

    }

    HashSet set;
    Pattern p;
}
