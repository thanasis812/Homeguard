package com.hg.domain;

/**
 * This class represents a UI pagination object that holds data and pagination information.
 * This class is intended to be used by the client side to match their functionality
 * @param <T> The type of data to be paginated.
 */
public class UiPagination<T> {

    private int totalRecords;
    private int currentPage;
    private T data;

    public UiPagination(int totalRecords, int currentPage, T data) {
        this.totalRecords = totalRecords;
        this.currentPage = currentPage;
        this.data = data;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
