package com.saas.api.common.dto;

import lombok.Data;

/**
 * Created by Jacky on 2018/10/19.
 */
@Data
public class Page {

    private int pageNo;

    private int pageSize;

    private int startIndex;

    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo - 1;
        this.pageSize = pageSize;
        this.startIndex = this.pageNo * this.pageSize;
    }
}