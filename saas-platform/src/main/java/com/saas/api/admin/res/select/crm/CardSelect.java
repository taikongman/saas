package com.saas.api.admin.res.select.crm;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 会员卡下拉信息
 * @Author: 徐未
 * @Date: 2019/12/18
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CardSelect {
	
	private Long id;
	
	private String cardName;
	
	
}
