package com.saas.api.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by kangs on 2018/10/15.
 */
@Data
@NoArgsConstructor
@ToString 
@EqualsAndHashCode(callSuper=false)
public class SaasException extends RuntimeException{

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	public SaasException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

	
    private Integer code;

    private String msg;
    
    
}
