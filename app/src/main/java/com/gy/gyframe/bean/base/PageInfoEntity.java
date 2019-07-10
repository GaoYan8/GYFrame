package com.gy.gyframe.bean.base;

import java.util.List;


/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/11/6
 * @Describe
 */
public class PageInfoEntity<T> extends BaseEntity {

    private List<T> records;

    private int total;//": 1,
    private int size;//": 10,
    private int current;//": 1,
    private int pages;//": 1

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
