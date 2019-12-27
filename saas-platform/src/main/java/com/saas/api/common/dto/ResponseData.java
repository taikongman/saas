package com.saas.api.common.dto;


import lombok.Data;

/**
 * Created by kangs on 2019/06/15.
 */
@Data
public class ResponseData {
	
    private String code;

    private String msg;

    private Object data;
}