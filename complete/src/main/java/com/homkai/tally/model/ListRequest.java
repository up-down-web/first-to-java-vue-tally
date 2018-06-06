package com.homkai.tally.model;

import org.springframework.data.domain.Sort;
import java.util.List;


public class ListRequest {

    private int pageNumber;

    private int pageSize;

    private List<SortOrder> sortOrders;


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<SortOrder> getSortOrders() {
        return sortOrders;
    }

    public void setSortOrders(List<SortOrder> sortOrders) {
        this.sortOrders = sortOrders;
    }

    public Sort getSort() {
        if (sortOrders.size() == 0) {
            return null;
        }
        SortOrder sortOrder = sortOrders.get(0);
        return new Sort(sortOrder.getDirection(), sortOrder.getProperty());
    }

}