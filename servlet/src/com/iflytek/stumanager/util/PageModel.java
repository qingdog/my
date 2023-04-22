package com.iflytek.stumanager.util;

import java.util.List;

public class PageModel {

    private List<?> list;
    private int totalRecords;
    private int pageSize;
    private int totalPages;
    private int pageNo;
    public PageModel(int pageSize) {
        this.pageSize = pageSize;
    }
    public PageModel(int pageNo, int totalPages, int pageSize, int totalRecords) {
        this.pageNo = pageNo;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
    }
    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTopPage() {
        return 1;
    }

    public int getLastPage() {
        return totalPages;
    }

    public int getPreviousPage() {
        return pageNo > 1 ? pageNo - 1 : 1;
    }

    public int getNextPage() {
        return pageNo < getTotalPages() ? pageNo + 1 : getTotalPages();
    }
}
