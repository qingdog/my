// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Regex.java

package com.iflytek.stumanager.util;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex
{

    public Regex()
    {
    }

    public static void main(String args[])
    {
        HashSet set = new HashSet();
        Pattern p = Pattern.compile("\\w+@\\w+[.](com|cn|net)");
        try
        {
            URL url = new URL("https://tieba.baidu.com/p/2954092023/");
            HttpURLConnection huc = (HttpURLConnection)url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(), "UTF-8"));
            System.out.print("\u6B63\u5728\u8BFB\u53D6");
            String line;
            while((line = br.readLine()) != null) 
            {
                for(Matcher m = p.matcher(line); m.find(); System.out.print('.'))
                    set.add(m.group());

            }
            br.close();
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
        System.out.println((new StringBuilder("\n\u90AE\u7BB1\u4E2A\u6570\u4E3A\uFF1A")).append(set.size()).toString());
        String s;
        for(Iterator iterator = set.iterator(); iterator.hasNext(); System.out.println(s.toString()))
            s = (String)iterator.next();

    }
}
