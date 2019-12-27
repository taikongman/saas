package com.saas.api.admin.service.crm.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.api.admin.service.crm.OrderService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.common.InvoiceDao;
import com.saas.api.common.dao.common.MaintainTypeDao;
import com.saas.api.common.dao.common.ProjectTypeDao;
import com.saas.api.common.dao.common.SettleModeDao;
import com.saas.api.common.dao.crm.CarDao;
import com.saas.api.common.dao.crm.CommodityDao;
import com.saas.api.common.dao.crm.CustomerBindCarDao;
import com.saas.api.common.dao.crm.CustomerDao;
import com.saas.api.common.dao.crm.CustomerPayDao;
import com.saas.api.common.dao.crm.InventoryRecordDao;
import com.saas.api.common.dao.crm.OrderCommodityDao;
import com.saas.api.common.dao.crm.OrderDao;
import com.saas.api.common.dao.crm.OrderProjectDao;
import com.saas.api.common.dao.crm.ProjectDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.entity.common.Invoice;
import com.saas.api.common.entity.common.MaintainType;
import com.saas.api.common.entity.common.ProjectType;
import com.saas.api.common.entity.common.SettleMode;
import com.saas.api.common.entity.crm.Car;
import com.saas.api.common.entity.crm.Customer;
import com.saas.api.common.entity.crm.CustomerBindCar;
import com.saas.api.common.entity.crm.CustomerPay;
import com.saas.api.common.entity.crm.InventoryRecord;
import com.saas.api.common.entity.crm.Order;
import com.saas.api.common.entity.crm.OrderCommodity;
import com.saas.api.common.entity.crm.OrderProject;
import com.saas.api.common.entity.crm.Project;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.StringDataUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Resource
    private CarDao carDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private CustomerBindCarDao customerBindCarDao;
    
	@Resource
    private OrderDao orderDao;

    @Resource
    private CommodityDao commodityDao;

    @Resource
    private OrderCommodityDao orderCommodityDao;

    @Resource
    private OrderProjectDao orderProjectDao;
    
    @Resource
    private UserDao userDao;
    
    @Resource
    private ProjectDao projectDao;
    
    @Resource
    private SettleModeDao settleModeDao;
    
    @Resource
    private ProjectTypeDao projectTypeDao;
    
    @Resource
    private MaintainTypeDao maintainTypeDao;
    
    @Resource
    private InvoiceDao invoiceDao;
    
    @Resource
    private InventoryRecordDao inventoryRecordDao;
    
    @Resource
    private CustomerPayDao payDao;
    
    @Override
    public Map<String, Object> orderList(Map<String, Object> params) {
    	log.info("params:" + params.toString());
        
        List<Order> orderList = orderDao.orderList(params);
        int total = orderDao.countOrder(params);
        
        Map<String, Object> result = new HashMap<>();
        result.put("dataList", orderList);
        result.put("total", total);
        return result;
    }

    @Override
    public Map<String, Object> orderDetail(Long orderId, Long adminId) {
        Map<String, Object> result = new HashMap<>();
        List<OrderProject> orderProjectList = orderProjectDao.selectByOrderId(orderId);
        List<OrderCommodity> orderCommodityList = orderCommodityDao.selectByOrderId(orderId);
        Order order = orderDao.selectByPrimaryKey(orderId);
        User user = userDao.findById(Long.valueOf(order.getAdminId()));
        Car car = carDao.selectByPrimaryKey(order.getCarId());
        CustomerPay pay = payDao.selectByPrimaryKey(order.getPayId());
        Customer customer = customerDao.selectByPrimaryKey(order.getCustomerId());
        
        result.put("order", order);
        result.put("orderProjectList", orderProjectList);
        result.put("orderCommodityList", orderCommodityList);
        result.put("customer", customer);
        result.put("car", car);
        result.put("pay", pay);
        result.put("realName", user.getRealName());
        return result;
    }

    @Override
    public Map<String, Object> orderList4OrCond(Map<String, Object> params) {
    	List<Order> orderList = orderDao.orderList4OrCond(params);
        int total = orderDao.countOrder4OrCond(params);
        
        Map<String, Object> result = new HashMap<>();
        result.put("orderList", orderList);
        result.put("total", total);
        return result;
    }

    @Override
    public void dispachOrder(Long doAdminId, Long orderId) {
        Order order = orderDao.selectByPrimaryKey(orderId);
        User user = userDao.findById(doAdminId);
        order.setStatus(DbConstant.ORDER_STATUS_DOING);//派工后状态为施工中
        order.setStaffId(doAdminId);
        order.setStaffName(user.getRealName());
        orderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    public void finishWork(Long orderId) {
        Order order = orderDao.selectByPrimaryKey(orderId);
        order.setStatus(DbConstant.ORDER_STATUS_UNSETTLE);//派工后状态为施工中
        order.setRealFinishTime(new Date());
        orderDao.updateByPrimaryKeySelective(order);
    }    
    
	@Override
	@Transactional
	public void operateCommodity(JSONArray commodityList, Long orderId, Long adminId, String lanType) {
		Order check = orderDao.selectByPrimaryKey(orderId);
		if(check == null) {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ORDER_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		if (null != commodityList) {
            OrderCommodity orderCommodity = null;
            for (int i = 0; i < commodityList.size(); i++) {
                orderCommodity = (OrderCommodity) JSONObject.toBean(commodityList.getJSONObject(i), OrderCommodity.class);
                OrderCommodity existRecord = orderCommodityDao.selectByPrimaryKey(orderCommodity.getId());
                if(!existRecord.getOrderId().equals(orderId)) {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_ID_IN_COMMODITY_ID_IS_DIFFERENT_FROM_ORDER_ID.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_ORDER_ID_IN_COMMODITY_ID_IS_DIFFERENT_FROM_ORDER_ID.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }
                
                InventoryRecord inventory = inventoryRecordDao.selectByPrimaryKey(existRecord.getInventoryId());
                InventoryRecord updInventory = new InventoryRecord();
                updInventory.setId(existRecord.getInventoryId());
                updInventory.setUpdateTime(new Date());
                Integer operateType = orderCommodity.getOperateType();
                if(operateType.equals(CommonConstant.INT_TWO)) {
                	if(inventory.getQuantity().compareTo(orderCommodity.getQuantity()) < 0) {
                    	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_COMMODITY_NO_LESS_THAN_NEED.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_ORDER_COMMODITY_NO_LESS_THAN_NEED.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                    }
                	updInventory.setQuantity(inventory.getQuantity() -  orderCommodity.getQuantity());                	
                	inventoryRecordDao.updateByPrimaryKeySelective(updInventory);                	
                } else if(operateType.equals(CommonConstant.INT_THREE)) {
                	updInventory.setQuantity(inventory.getQuantity() +  orderCommodity.getQuantity());
                	inventoryRecordDao.updateByPrimaryKeySelective(updInventory);
                } else {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_COMMODITY_OPERATE_TYPE_ERROR.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_ORDER_COMMODITY_OPERATE_TYPE_ERROR.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }
                
                User user = userDao.findById(orderCommodity.getOperatorId());
                orderCommodity.setOperatorName(user.getRealName());
                
                orderCommodity.setGroupId(existRecord.getGroupId());
                orderCommodity.setCompanyId(existRecord.getCompanyId());
                orderCommodity.setDepartmentId(existRecord.getDepartmentId());
                orderCommodity.setAdminId(existRecord.getAdminId());
                orderCommodity.setInventoryId(existRecord.getInventoryId());
                orderCommodity.setCommodityId(existRecord.getCommodityId());
                orderCommodity.setCommodityCode(existRecord.getCommodityCode());
                orderCommodity.setCommodityName(existRecord.getCommodityName());
                orderCommodity.setSupplierId(existRecord.getSupplierId());
                orderCommodity.setSupplierCode(existRecord.getSupplierCode());
                orderCommodity.setSupplierName(existRecord.getSupplierName());
                orderCommodity.setBarCode(existRecord.getBarCode());
                orderCommodity.setCostPrice(existRecord.getCostPrice());
                orderCommodity.setSalePrice(existRecord.getSalePrice());
                orderCommodity.setDiscount(existRecord.getDiscount());
                orderCommodity.setAmount(existRecord.getSalePrice().multiply(new BigDecimal(orderCommodity.getQuantity())));
                orderCommodity.setUnitId(existRecord.getUnitId());
                orderCommodity.setUnitName(existRecord.getUnitName());
                orderCommodity.setSettleMode(existRecord.getSettleMode());
                orderCommodity.setSettleName(existRecord.getSettleName());
                orderCommodity.setStatus(DbConstant.STATUS_ONE);
                orderCommodity.setCarNo(existRecord.getCarNo());
                orderCommodity.setOrderId(orderId);
                orderCommodity.setCreateTime(new Date());
                orderCommodity.setVersion(DbConstant.INIT_VERSION);
                orderCommodity.setLanType(lanType);
                orderCommodityDao.insert(orderCommodity);
            }
        }
    }

	@Override
	@Transactional
	public void updateData(JSONObject params, Long adminId, String lanType) {
		User user = userDao.findById(adminId);
		
		JSONArray projectList = null;
        if(null != params.get(RequestParamConstant.PROJECT_LIST)){
        	projectList = params.getJSONArray(RequestParamConstant.PROJECT_LIST);
        }
        JSONArray commodityList = null;
        if(null != params.get(RequestParamConstant.COMMODITY_LIST)){
            commodityList = params.getJSONArray(RequestParamConstant.COMMODITY_LIST);
        }
        
        // 获取CARID
        Car car = getCar(user, params, lanType);
        
        params.remove(RequestParamConstant.VIN_CODE);
        params.remove(RequestParamConstant.PROJECT_LIST);
        params.remove(RequestParamConstant.COMMODITY_LIST);
        
        Order order = (Order) JSONObject.toBean(params, Order.class);
        order.setCarId(car.getId());
        
		if(order.getId() == null) {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_ID_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ORDER_ID_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		Order check = orderDao.selectByPrimaryKey(order.getId());
		if(check == null) {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ORDER_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
        Customer customer = getCustomer(user, params, lanType);
        order.setCustomerId(customer.getId());
        processBindRelation(car.getId(), customer.getId());
        
        ProjectType projectType = projectTypeDao.findById(order.getProjectTypeId());
		if(projectType != null) {
			order.setProjectTypeName(projectType.getName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		MaintainType maintainType = maintainTypeDao.findById(order.getMaintainTypeId());
		if(maintainType != null) {
			order.setMaintainTypeName(maintainType.getName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		if(order.getInvoiceId() != null) {
			Invoice invoice = invoiceDao.findById(order.getInvoiceId());
			if(invoice != null) {
				order.setInvoiceName(invoice.getInvoiceName());
			}else {
				ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_INVOICE_IS_NOT_EXIST.getCode(), 
	    				ResponseCodeI18n.MODULE_CRM_INVOICE_IS_NOT_EXIST.getMessage(), lanType);
	            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
			}
		}
		
		order.setMemberId(adminId);
		order.setMemberName(user.getRealName());
		order.setUpdateTime(new Date());
        orderDao.updateByPrimaryKeySelective(order);
        
        addOrderProject(projectList, order.getId(), user, lanType);
        addOrderCommodity(commodityList, order, user, lanType);
	}

    @Override
    @Transactional
    public void addOrder(JSONObject params, Long adminId, String lanType) {
    	User user = userDao.findById(adminId);
        params.put(RequestParamConstant.GROUP_ID, user.getGroupId());
        params.put(RequestParamConstant.COMPANY_ID, user.getCompanyId());
        params.put(RequestParamConstant.DEPARTMENT_ID, user.getDepartmentId());
        params.put(RequestParamConstant.ADMIN_ID, adminId);
        //生成订单号，carid，等
        params.put(RequestParamConstant.ORDER_NO, StringDataUtils.genOrderNo());
        Car car = getCar(user, params, lanType);
        params.put(RequestParamConstant.CAR_ID, car.getId());

        Customer customer = getCustomer(user, params, lanType);
        params.put(RequestParamConstant.CUSTOMER_ID, customer.getId());
        processBindRelation(car.getId(), customer.getId());
        JSONArray projectList = null;
        if(null != params.get(RequestParamConstant.PROJECT_LIST)){
        	projectList = params.getJSONArray(RequestParamConstant.PROJECT_LIST);
        }
        JSONArray commodityList = null;
        if(null != params.get(RequestParamConstant.COMMODITY_LIST)){
            commodityList = params.getJSONArray(RequestParamConstant.COMMODITY_LIST);
        }
        params.remove(RequestParamConstant.VIN_CODE);
        params.remove(RequestParamConstant.PROJECT_LIST);
        params.remove(RequestParamConstant.COMMODITY_LIST);
        Order order = (Order) JSONObject.toBean(params, Order.class);
        
        ProjectType projectType = projectTypeDao.findById(order.getProjectTypeId());
		if(projectType != null) {
			order.setProjectTypeName(projectType.getName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		MaintainType maintainType = maintainTypeDao.findById(order.getMaintainTypeId());
		if(maintainType != null) {
			order.setMaintainTypeName(maintainType.getName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
//		Invoice invoice = invoiceDao.findById(order.getInvoiceId());
//		if(invoice != null) {
//			order.setInvoiceName(invoice.getInvoiceName());
//		}else {
//			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_INVOICE_IS_NOT_EXIST.getCode(), 
//    				ResponseCodeI18n.MODULE_CRM_INVOICE_IS_NOT_EXIST.getMessage(), lanType);
//            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
//		}
		
		order.setMemberId(adminId);
		order.setMemberName(user.getRealName());
        order.setGroupId(user.getGroupId());
        order.setCompanyId(user.getCompanyId());
        order.setDepartmentId(user.getDepartmentId());
        order.setAdminId(user.getAdminId());
        order.setStatus(DbConstant.ORDER_STATUS_INIT);
        order.setCreateTime(new Date());
        order.setVersion(DbConstant.INIT_VERSION);
        order.setLanType(lanType);
        orderDao.insert(order);

        addOrderProject(projectList, order.getId(), user, lanType);
        addOrderCommodity(commodityList, order, user, lanType);
    }

    private void addOrderCommodity(JSONArray commodityList, Order order, User user, String lanType) {
        if (null != commodityList) {
            OrderCommodity orderCommodity = null;
            orderCommodityDao.deleteByOrderId(order.getId());
            for (int i = 0; i < commodityList.size(); i++) {
                orderCommodity = (OrderCommodity) JSONObject.toBean(commodityList.getJSONObject(i), OrderCommodity.class);
                InventoryRecord inventory = inventoryRecordDao.selectByPrimaryKey(orderCommodity.getInventoryId());
                
                SettleMode settleMode = settleModeDao.findById(orderCommodity.getSettleMode());
                if(settleMode != null) {
                	orderCommodity.setSettleName(settleMode.getName());
                } else {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }
                orderCommodity.setGroupId(user.getGroupId());
                orderCommodity.setCompanyId(user.getCompanyId());
                orderCommodity.setDepartmentId(user.getDepartmentId());
                orderCommodity.setAdminId(user.getAdminId());
                orderCommodity.setCommodityId(inventory.getCommodityId());
                orderCommodity.setCommodityCode(inventory.getCommodityCode());
                orderCommodity.setCommodityName(inventory.getCommodityName());
                orderCommodity.setSupplierId(inventory.getSupplierId());
                orderCommodity.setSupplierCode(inventory.getSupplierCode());
                orderCommodity.setSupplierName(inventory.getSupplierName());
                orderCommodity.setBarCode(inventory.getBarCode());
                orderCommodity.setCostPrice(inventory.getCostPrice());
                orderCommodity.setUnitId(inventory.getUnitId());
                orderCommodity.setUnitName(inventory.getUnitName());
                orderCommodity.setOperateType(DbConstant.ORDER_MATERIAL_STATUS_INIT);
                orderCommodity.setStatus(DbConstant.STATUS_ONE);
                orderCommodity.setCarNo(order.getCarNo());
                orderCommodity.setOrderId(order.getId());
                orderCommodity.setCreateTime(new Date());
                orderCommodity.setVersion(DbConstant.INIT_VERSION);
                orderCommodity.setLanType(lanType);
                orderCommodityDao.insert(orderCommodity);
            }
        }
    }
    

    private void addOrderProject(JSONArray projectList, Long orderId, User user, String lanType) {
    	if (null != projectList && projectList.size() > 0) {
    		OrderProject orderProject = null;
        	orderProjectDao.deleteByOrderId(orderId);
            for (int i = 0; i < projectList.size(); i++) {
                orderProject = (OrderProject) JSONObject.toBean(projectList.getJSONObject(i), OrderProject.class);
                Project project = projectDao.selectByPrimaryKey(orderProject.getProjectId());
                if(project != null) {
                	orderProject.setProjectTypeId(project.getProjectTypeId());
                	orderProject.setProjectTypeName(project.getProjectTypeName());
                	orderProject.setCategoryId(project.getCategoryId());
                	orderProject.setCategoryName(project.getProjectName());
                	orderProject.setPriceMode(project.getPriceMode());
                	orderProject.setPriceName(project.getPriceName());
                	orderProject.setProjectCode(project.getProjectCode());
                	orderProject.setProjectName(project.getProjectName());
                	orderProject.setUnitId(project.getUnitId());
                	orderProject.setUnitName(project.getUnitName());
                }else {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_PROJECT_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }
                SettleMode settleMode = settleModeDao.findById(orderProject.getSettleMode());
                if(settleMode != null) {
                	orderProject.setSettleName(settleMode.getName());
                } else {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }
                if(orderProject.getStaffId() != null) {
                	User staff = userDao.findById(orderProject.getStaffId());
                	orderProject.setStaffName(staff.getRealName());
                }
                orderProject.setOrderId(orderId);
                orderProject.setCreateTime(new Date());
                orderProject.setVersion(DbConstant.INIT_VERSION);
                orderProjectDao.insert(orderProject);
            }
    	}
    }
    
    private void processBindRelation(Long carId, Long customerId) {
        CustomerBindCar customerBindCar = customerBindCarDao.selectByCustomerIdAndCarId(customerId, carId);
        if (null == customerBindCar) {
            customerBindCar = new CustomerBindCar();
            customerBindCar.setCustomerId(customerId);
            customerBindCar.setCarId(carId);
            customerBindCar.setCreateAt(new Date());
            customerBindCar.setVersion(DbConstant.INIT_VERSION);
            customerBindCarDao.insert(customerBindCar);
        }
    }

    private Customer getCustomer(User user, JSONObject params, String lanType) {
        Customer customer = customerDao.selectByPhone(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), user.getAdminId().intValue(), lanType, 
        		params.getString(RequestParamConstant.CUSTOMER_PHONE));
        if (null == customer) {
            customer = new Customer();
            customer.setGroupId(user.getGroupId());
            customer.setCompanyId(user.getCompanyId());
            customer.setDepartmentId(user.getDepartmentId());
            customer.setAdminId(user.getAdminId());
            customer.setPhone(params.get(RequestParamConstant.CUSTOMER_PHONE).toString());
            customer.setName(params.get(RequestParamConstant.CUSTOMER_NAME).toString());
            customer.setCreateAt(new Date());
            customer.setVersion(DbConstant.INIT_VERSION);
            customer.setLanType(lanType);
            customerDao.insert(customer);
        } else {
            if (!DbConstant.CUSTOMER_STATUS_NORMAL.equals(customer.getStatus())) {
                customer.setName(params.get(RequestParamConstant.CUSTOMER_NAME).toString());
                customer.setStatus(DbConstant.CUSTOMER_STATUS_NORMAL);
                customerDao.updateByPrimaryKeySelective(customer);
            }
        }
        return customer;
    }

    private Car getCar(User user, JSONObject params, String lanType) {
        Car car = carDao.selectByCarNo(params.getString(RequestParamConstant.CAR_NO), 
        		user.getGroupId(), user.getCompanyId(), user.getDepartmentId(), user.getAdminId().intValue(), lanType);
        if (null == car) {
            car = new Car();
            car.setGroupId(user.getGroupId());
            car.setCompanyId(user.getCompanyId());
            car.setDepartmentId(user.getDepartmentId());
            car.setAdminId(user.getAdminId().intValue());
            car.setCarNo(params.getString(RequestParamConstant.CAR_NO));
            if (null != params.get(RequestParamConstant.VIN_CODE)) {
                car.setVinCode(params.getString(RequestParamConstant.VIN_CODE));
            }
            if (null != params.get(RequestParamConstant.MILEAGE)) {
                car.setMileage(params.getInt(RequestParamConstant.MILEAGE));
            }
            car.setCreateAt(new Date());
            car.setVersion(DbConstant.INIT_VERSION);
            car.setLanType(lanType);
            carDao.insert(car);
        } else {
            boolean needUpdateCarInfo = false;
            if (!DbConstant.CAR_STATUS_NORMAL.equals(car.getStatus())) {
                car.setStatus(DbConstant.CAR_STATUS_NORMAL);
                needUpdateCarInfo = true;
            }
            if (null != params.get(RequestParamConstant.MILEAGE)) {
                car.setMileage(params.getInt(RequestParamConstant.MILEAGE));
                needUpdateCarInfo = true;
            }
            if (needUpdateCarInfo) {
                carDao.updateByPrimaryKeySelective(car);
            }
        }
        return car;
    }
    
}
