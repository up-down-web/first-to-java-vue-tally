package com.homkai.tally.model;

import org.springframework.data.domain.Page;

import java.util.List;


public class ListResponse {

    private List data;

    private long totalCount;

    private Boolean last;

    private ListRequest listRequest;


    public ListResponse(ListRequest listRequest, Page page) {
        this.data = page.getContent();
        this.totalCount = page.getTotalElements();
        this.last = page.isLast();
        this.listRequest = listRequest;
    }

    public List getData() {
        return data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public Boolean getLast() {
        return last;
    }

    public long getPageNumber() {
        return listRequest.getPageNumber();
    }

    public long getPageSize() {
        return listRequest.getPageSize();
    }

    public List<SortOrder> getSortOrders() {
        return listRequest.getSortOrders();
    }
}