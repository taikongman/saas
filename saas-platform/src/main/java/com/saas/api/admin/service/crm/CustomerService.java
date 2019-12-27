package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.crm.CustomerAccountResponse;
import com.saas.api.admin.res.select.crm.CustomerSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.Car;
import com.saas.api.common.entity.crm.Customer;

import net.sf.json.JSONObject;

public interface CustomerService {

	Map<String, Object> customerList(Long adminId, String lanType, String name, String phone, Page page);

	void addCustomer(JSONObject params, Long adminId, String lanType);
	
	void addCustomerAndCar(JSONObject params, Long adminId, String lanType);

	void delCustomer(Long adminId, Long customerId);
	
	void delCustomerCar(Long adminId, String lanType, Long customerId, Long carId);

	void modifyCustomer(JSONObject params, Long adminId, String lanType);
	
	Customer selectByPrimaryKey(Long id);
	
	List<CustomerSelect> findSelectObject(Long adminId, String lanType);
	
	List<Car> carList4Customer(Long adminId, Long customerId);

	List<CustomerAccountResponse> accountList4Customer(Long adminId, Long customerId, Integer accountType);
	
	Map<String, Object> queryAccountList(Long adminId, Long customerId, Integer accountType, Page page);
	
}
