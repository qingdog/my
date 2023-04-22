// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PageModel.java

package com.iflytek.stumanager.util;

import java.util.List;

public class PageModel
{

    public PageModel()
    {
        pageSize = 5;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public List getStuList()
    {
        return stuList;
    }

    public void setStuList(List stuList)
    {
        this.stuList = stuList;
    }

    public int getTotalRecords()
    {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords)
    {
        this.totalRecords = totalRecords;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    public int getTopPage()
    {
        return 1;
    }

    public int getLastPage()
    {
        return totalPages;
    }

    public int getPreviousPage()
    {
        return pageNo > 1 ? pageNo - 1 : 1;
    }

    public int getNextPage()
    {
        return pageNo < getTotalPages() ? pageNo + 1 : getTotalPages();
    }

    private List stuList;
    private int totalRecords;
    private int pageSize;
    private int totalPages;
    private int pageNo;
}
