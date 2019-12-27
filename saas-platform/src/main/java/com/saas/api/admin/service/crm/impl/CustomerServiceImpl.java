package com.saas.api.admin.service.crm.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.saas.api.admin.res.crm.CustomerAccountResponse;
import com.saas.api.admin.res.crm.CustomerResponse;
import com.saas.api.admin.res.select.crm.CustomerSelect;
import com.saas.api.admin.service.crm.CustomerService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.common.AreaDao;
import com.saas.api.common.dao.common.AutoBrandDao;
import com.saas.api.common.dao.common.AutoModelDao;
import com.saas.api.common.dao.common.AutoSeriesDao;
import com.saas.api.common.dao.common.CityDao;
import com.saas.api.common.dao.common.ProvinceDao;
import com.saas.api.common.dao.crm.CarDao;
import com.saas.api.common.dao.crm.CustomerAccountCommodityDao;
import com.saas.api.common.dao.crm.CustomerAccountDao;
import com.saas.api.common.dao.crm.CustomerAccountProjectDao;
import com.saas.api.common.dao.crm.CustomerBindCarDao;
import com.saas.api.common.dao.crm.CustomerDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.AutoBrand;
import com.saas.api.common.entity.common.AutoModel;
import com.saas.api.common.entity.common.AutoSeries;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;
import com.saas.api.common.entity.crm.Car;
import com.saas.api.common.entity.crm.Customer;
import com.saas.api.common.entity.crm.CustomerAccount;
import com.saas.api.common.entity.crm.CustomerAccountCommodity;
import com.saas.api.common.entity.crm.CustomerAccountProject;
import com.saas.api.common.entity.crm.CustomerBindCar;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Resource
    private UserDao userDao;
	
	@Resource
    private CustomerDao customerDao;
	
	@Resource
    private CarDao carDao;

	@Resource
    private CustomerBindCarDao customerBindCarDao;
    
    @Resource
    private CustomerAccountDao accountDao;
    
    @Resource
    private CustomerAccountCommodityDao cACommodityDao;

    @Resource
    private CustomerAccountProjectDao cAProjectDao;
    
    @Resource
    private AutoBrandDao autoBrandDao;
    
    @Resource
    private AutoSeriesDao autoSeriesDao;
    
    @Resource
    private AutoModelDao autoModelDao;
    
    @Resource
    private ProvinceDao provinceDao;
    
    @Resource
    private CityDao cityDao;
    
    @Resource
    private AreaDao areaDao;
	
	@Override
    public Map<String, Object> customerList(Long adminId, String lanType, String name, String phone, Page page) {
    	User user= userDao.findById(adminId);
    	List<Customer> customerList = customerDao.customerList(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(), 
    			lanType, name, phone, DbConstant.CUSTOMER_STATUS_NORMAL, page.getStartIndex(), page.getPageSize());
    	
    	List<CustomerResponse> resultList = new ArrayList<CustomerResponse>();
    	for(Customer forTemp : customerList) {
    		CustomerResponse customerRes = new CustomerResponse();
    		BeanUtils.copyProperties(forTemp, customerRes);
    		List<CustomerBindCar>  cbcList = customerBindCarDao.selectByCustomerId(forTemp.getId());
    		
    		List<Long> carIdList = new ArrayList<Long>();
    		for(CustomerBindCar cbcTemp : cbcList) {
    			carIdList.add(cbcTemp.getCarId());
    		}
    		List<Car> carList = carDao.selectByCarIdList(carIdList);
    		customerRes.setCarList(carList);
    		resultList.add(customerRes);
    	}
    	
    	int total = customerDao.countCustomer(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(),  
    			lanType, name, phone, DbConstant.CUSTOMER_STATUS_NORMAL);
        Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
    }

    @Override
    @Transactional
    public void addCustomer(JSONObject params, Long adminId, String lanType) {
    	User user = userDao.findById(adminId);
    	int existCustomerCount = customerDao.countByPhone(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(), 
    			lanType, params.getString(RequestParamConstant.PHONE));
        if (existCustomerCount > CommonConstant.INT_ZERO) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
        
        int existCarCount = carDao.countByCarNo(params.getString(RequestParamConstant.CAR_NO), 
        		user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(), 
        		lanType);
        if (existCarCount > CommonConstant.INT_ZERO) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CAR_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_CAR_IS_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
        String[] dateFormats = new String[]{"yyyy-MM-dd"};
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
        Customer customer = (Customer) JSONObject.toBean(params, Customer.class);
        customer.setGroupId(user.getGroupId());
        customer.setCompanyId(user.getCompanyId());
        customer.setDepartmentId(user.getDepartmentId());
        customer.setAdminId(user.getAdminId());
        customer.setStatus(DbConstant.CUSTOMER_STATUS_NORMAL);
        customer.setCreateAt(new Date());
        customer.setVersion(DbConstant.INIT_VERSION);
        customer.setLanType(lanType);
        customerDao.insert(customer);
        
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        Car car = new Car();
        if(params.get(RequestParamConstant.BRAND_ID) != null) {
        	Integer brandId = params.getInt(RequestParamConstant.BRAND_ID);
        	AutoBrand brand = autoBrandDao.findById(brandId);
        	if(brand != null) {
        		car.setBrandId(brandId);
        		car.setBrandName(brand.getBrandName());
        	}
        }
        if(params.get(RequestParamConstant.SERIES_ID) != null) {
        	Integer seriesId = params.getInt(RequestParamConstant.SERIES_ID);
        	AutoSeries series = autoSeriesDao.findById(seriesId);
        	if(series != null) {
        		car.setSeriesId(seriesId);
        		car.setSeriesName(series.getSeriesName());
        	}
        }
        if(params.get(RequestParamConstant.MODEL_ID) != null) {
        	Integer modelId = params.getInt(RequestParamConstant.MODEL_ID);
        	AutoModel model = autoModelDao.findById(modelId);
        	if(model != null) {
        		car.setModelId(modelId);
        		car.setModelName(model.getModelName());
        	}
        }
        if(params.get(RequestParamConstant.YEAR) != null) {
        	car.setYear(params.getInt(RequestParamConstant.YEAR));
        }
        
        car.setCarNo(params.getString("carNo"));
        car.setEngineNo(params.getString("engineNo"));
        car.setVinCode(params.getString("vinCode"));
        car.setChassisNo(params.getString("chassisNo"));
        
        car.setCompulsoryInsurance(params.getString("compulsoryInsurance"));
        car.setBusinessInsurance(params.getString("businessInsurance"));
        
        try {
        	if(!StringUtils.isEmpty(params.getString("buyAt"))) {
        		car.setBuyAt(sdf.parse(params.getString("buyAt")));
        	}
        	if(!StringUtils.isEmpty(params.getString("nextMaintenanceAt"))) {
        		car.setNextMaintenanceAt(sdf.parse(params.getString("nextMaintenanceAt")));
        	}
        	if(!StringUtils.isEmpty(params.getString("compulsoryExpireAt"))) {
        		car.setCompulsoryExpireAt(sdf.parse(params.getString("compulsoryExpireAt")));
        	}
        	if(!StringUtils.isEmpty(params.getString("businessExpireAt"))) {
        		car.setBusinessExpireAt(sdf.parse(params.getString("businessExpireAt")));
        	}
        	if(!StringUtils.isEmpty(params.getString("verifyAt"))) {
        		car.setVerifyAt(sdf.parse(params.getString("verifyAt")));
        	}
		} catch (ParseException e) {
			e.printStackTrace();
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.DATE_FORMAT_FAILED.getCode(), 
    				ResponseCodeI18n.DATE_FORMAT_FAILED.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
        car.setRemark(params.getString("remark"));
        car.setGroupId(user.getGroupId());
        car.setCompanyId(user.getCompanyId());
        car.setDepartmentId(user.getDepartmentId());
        car.setAdminId(user.getAdminId().intValue());
        car.setStatus(DbConstant.CAR_STATUS_NORMAL);
        car.setCreateAt(new Date());
        car.setVersion(DbConstant.INIT_VERSION);
        car.setLanType(lanType);
        carDao.insert(car);
        CustomerBindCar customerBindCar = new CustomerBindCar();
        customerBindCar.setCarId(car.getId());
        customerBindCar.setCustomerId(customer.getId());
        customerBindCar.setCreateAt(new Date());
        customerBindCar.setVersion(DbConstant.INIT_VERSION);
        customerBindCar.setLanType(lanType);
        customerBindCarDao.insert(customerBindCar);
    }
    
    @Override
    @Transactional
    public void addCustomerAndCar(JSONObject params, Long adminId, String lanType) {
    	User user = userDao.findById(adminId);
    	    	
    	Long customerId = null;
        if (!StringUtils.isEmpty(params.get(RequestParamConstant.CUSTOMER_ID))){
        	customerId = params.getLong(RequestParamConstant.CUSTOMER_ID);
        }
        
        JSONArray carJsontList = params.getJSONArray("carList");
        Customer customer = (Customer) JSONObject.toBean(params, Customer.class);
        if(!StringUtils.isEmpty(customer.getProvinceId())) {
        	Province province = provinceDao.findById(customer.getProvinceId());
        	if(province != null) {
        		customer.setProvince(province.getProvinceName());
        	}
        }
        if(!StringUtils.isEmpty(customer.getCityId())) {
        	City city = cityDao.findById(customer.getCityId());
        	if(city != null) {
        		customer.setCity(city.getCityName());
        	}
        }
        if(!StringUtils.isEmpty(customer.getAreaId())) {
        	Area area = areaDao.findById(customer.getAreaId());
        	if(area != null) {
        		customer.setArea(area.getAreaName());
        	}
        }
        if(!StringUtils.isEmpty(customer.getProvinceId()) 
        		&& !StringUtils.isEmpty(customer.getCityId())
        		&& !StringUtils.isEmpty(customer.getAreaId())
        		&& !StringUtils.isEmpty(customer.getAddress())) {
			customer.setFullAddress(new StringBuffer(customer.getProvince()).append(customer.getCity())
					.append(customer.getArea()).append(customer.getAddress()).toString());
        }
        
        //新增客户
    	if(customerId == null) {
    		int existCustomerCount = customerDao.countByPhone(user.getGroupId(), user.getCompanyId(), 
            		null, null, 
        			lanType, params.getString(RequestParamConstant.PHONE));
            if (existCustomerCount > CommonConstant.INT_ZERO.intValue()) {
            	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_EXIST.getMessage(), lanType);
                throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            }
            
            String[] dateFormats = new String[]{"yyyy-MM-dd"};
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
            
            customer.setGroupId(user.getGroupId());
            customer.setCompanyId(user.getCompanyId());
            customer.setDepartmentId(user.getDepartmentId());
            customer.setAdminId(user.getAdminId());
            customer.setStatus(DbConstant.CUSTOMER_STATUS_NORMAL);
            customer.setCreateAt(new Date());
            customer.setVersion(DbConstant.INIT_VERSION);
            customer.setLanType(lanType);
            customerDao.insert(customer);
    	}else {
    		Customer checkCustomer = customerDao.selectByPrimaryKey(customerId);
    		if(checkCustomer == null) {
    			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_NOT_EXIST.getMessage(), lanType);
                throw new SaasException(exception18n.getCode(), exception18n.getMessage());
    		}
    		customer.setId(customerId);
    		customerDao.updateByPrimaryKeySelective(customer);
    	}
    	
    	//SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
    	
    	if(carJsontList == null || carJsontList.size() == 0) {
    		ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
    				ResponseCodeI18n.PARAM_ERROR.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
    	}else {
    		customerBindCarDao.deleteByCustomerId(customer.getId());
    		for (int i = 0; i < carJsontList.size(); i++) {
                Car carTemp = (Car) JSONObject.toBean(carJsontList.getJSONObject(i), Car.class);
                if(carTemp.getBrandId() != null) {
                	AutoBrand brand = autoBrandDao.findById(carTemp.getBrandId());
                	if(brand != null) {
                		carTemp.setBrandName(brand.getBrandName());
                	}
                }
                if(carTemp.getSeriesId() != null) {
                	AutoSeries series = autoSeriesDao.findById(carTemp.getSeriesId());
                	if(series != null) {
                		carTemp.setSeriesName(series.getSeriesName());
                	}
                }
                if(carTemp.getModelId() != null) {
                	AutoModel model = autoModelDao.findById(carTemp.getModelId());
                	if(model != null) {
                		carTemp.setModelName(model.getModelName());
                	}
                }
                
                if(carTemp.getCarId() == null) {
            		int existCarCount = carDao.countByCarNo(carTemp.getCarNo(), 
                    		user.getGroupId(), user.getCompanyId(), 
                    		user.getDepartmentId(), adminId.intValue(), 
                    		lanType);
                    if (existCarCount > CommonConstant.INT_ZERO) {
                    	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CAR_IS_EXIST.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_CAR_IS_EXIST.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                    }
                    
            		carTemp.setGroupId(user.getGroupId());
                	carTemp.setCompanyId(user.getCompanyId());
                	carTemp.setDepartmentId(user.getDepartmentId());
                	carTemp.setAdminId(user.getAdminId().intValue());
                	carTemp.setStatus(DbConstant.CAR_STATUS_NORMAL);
                	carTemp.setCreateAt(new Date());
                	carTemp.setVersion(DbConstant.INIT_VERSION);
                	carTemp.setLanType(lanType);
                	carDao.insert(carTemp);
            	} else {
            		Car checkCar = carDao.selectByPrimaryKey(carTemp.getCarId());
            		if(checkCar == null) {
            			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CAR_IS_NOT_EXIST.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_CAR_IS_NOT_EXIST.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            		}
            		
            		carDao.updateByPrimaryKeySelective(carTemp);
            	}
            	
            	CustomerBindCar customerBindCar = new CustomerBindCar();
                customerBindCar.setCarId(carTemp.getId());
                customerBindCar.setCustomerId(customer.getId());
                customerBindCar.setCreateAt(new Date());
                customerBindCar.setVersion(DbConstant.INIT_VERSION);
                customerBindCar.setLanType(lanType);
                customerBindCarDao.insert(customerBindCar);
        	}
    	}
        
    }

    @Override
    public void delCustomer(Long adminId, Long customerId) {
        Customer customer = customerDao.selectByPrimaryKey(customerId);
        customer.setStatus(DbConstant.CUSTOMER_STATUS_DEL);
        customer.setModifyAt(new Date());
        customerDao.updateByPrimaryKeySelective(customer);
    }
    
    @Override
	public void delCustomerCar(Long adminId, String lanType, Long customerId, Long carId) {
		Car car = new Car();
		car.setId(carId);
		car.setStatus(DbConstant.CAR_STATUS_DEL);
		car.setModifyAt(new Date());
		carDao.updateByPrimaryKey(car);
	}

	@Override
    @Transactional
    public void modifyCustomer(JSONObject params, Long adminId, String lanType) {
    	User user = userDao.findById(adminId);
    	
    	int existCustomerCount = customerDao.countByPhone(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(), 
    			lanType, params.getString(RequestParamConstant.PHONE));
        if (existCustomerCount > CommonConstant.INT_ONE) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        } else {
            Customer existCustomer = customerDao.selectByPhone(user.getGroupId(), user.getCompanyId(), 
            		user.getDepartmentId(), adminId.intValue(), 
            		lanType, params.getString(RequestParamConstant.PHONE));
            if (null != existCustomer && !existCustomer.getId().equals(params.getLong(RequestParamConstant.CUSTOMER_ID))) {
            	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_EXIST.getMessage(), lanType);
                throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            }
        }
        
        int existCarCount = carDao.countByCarNo(params.getString(RequestParamConstant.CAR_NO), 
        		user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(), lanType);
        if (existCarCount > CommonConstant.INT_ONE) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CAR_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_CAR_IS_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        } else {
            Car existCar = carDao.selectByCarNo(params.getString(RequestParamConstant.CAR_NO), 
            		user.getGroupId(), user.getCompanyId(), 
            		user.getDepartmentId(), adminId.intValue(), lanType);
            if (null != existCar && !existCar.getId().equals(params.getLong(RequestParamConstant.CAR_ID))) {
            	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CAR_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_CAR_IS_EXIST.getMessage(), lanType);
                throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            }
        }
        
        params.put(RequestParamConstant.ID, params.getLong(RequestParamConstant.CUSTOMER_ID));
        Customer customer = (Customer) JSONObject.toBean(params, Customer.class);
        if(!StringUtils.isEmpty(customer.getProvinceId())) {
        	Province province = provinceDao.findById(customer.getProvinceId());
        	if(province != null) {
        		customer.setProvince(province.getProvinceName());
        	}
        }
        if(!StringUtils.isEmpty(customer.getCityId())) {
        	City city = cityDao.findById(customer.getCityId());
        	if(city != null) {
        		customer.setCity(city.getCityName());
        	}
        }
        if(!StringUtils.isEmpty(customer.getAreaId())) {
        	Area area = areaDao.findById(customer.getAreaId());
        	if(area != null) {
        		customer.setArea(area.getAreaName());
        	}
        }
        if(!StringUtils.isEmpty(customer.getProvinceId()) 
        		&& !StringUtils.isEmpty(customer.getCityId())
        		&& !StringUtils.isEmpty(customer.getAreaId())
        		&& !StringUtils.isEmpty(customer.getAddress())) {
			customer.setFullAddress(new StringBuffer(customer.getProvince()).append(customer.getCity())
					.append(customer.getArea()).append(customer.getAddress()).toString());
        }
        customer.setModifyAt(new Date());
        customerDao.updateByPrimaryKeySelective(customer);
        //params.put(RequestParamConstant.ID, params.getLong(RequestParamConstant.CAR_ID));
        
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        Car car = new Car();
        if(params.get(RequestParamConstant.BRAND_ID) != null) {
        	Integer brandId = params.getInt(RequestParamConstant.BRAND_ID);
        	AutoBrand brand = autoBrandDao.findById(brandId);
        	if(brand != null) {
        		car.setBrandId(brandId);
        		car.setBrandName(brand.getBrandName());
        	}
        }
        if(params.get(RequestParamConstant.SERIES_ID) != null) {
        	Integer seriesId = params.getInt(RequestParamConstant.SERIES_ID);
        	AutoSeries series = autoSeriesDao.findById(seriesId);
        	if(series != null) {
        		car.setSeriesId(seriesId);
        		car.setSeriesName(series.getSeriesName());
        	}
        }
        if(params.get(RequestParamConstant.MODEL_ID) != null) {
        	Integer modelId = params.getInt(RequestParamConstant.MODEL_ID);
        	AutoModel model = autoModelDao.findById(modelId);
        	if(model != null) {
        		car.setModelId(modelId);
        		car.setModelName(model.getModelName());
        	}
        }
        if(params.get(RequestParamConstant.YEAR) != null) {
        	car.setYear(params.getInt(RequestParamConstant.YEAR));
        }
        car.setId(params.getLong(RequestParamConstant.CAR_ID));
        car.setCarNo(params.getString("carNo"));
        car.setEngineNo(params.getString("engineNo"));
        car.setVinCode(params.getString("vinCode"));
        car.setChassisNo(params.getString("chassisNo"));
        
        car.setCompulsoryInsurance(params.getString("compulsoryInsurance"));
        car.setBusinessInsurance(params.getString("businessInsurance"));
        
        try {
        	if(!StringUtils.isEmpty(params.getString("buyAt"))) {
        		car.setBuyAt(sdf.parse(params.getString("buyAt")));
        	}
        	if(!StringUtils.isEmpty(params.getString("nextMaintenanceAt"))) {
        		car.setNextMaintenanceAt(sdf.parse(params.getString("nextMaintenanceAt")));
        	}
        	if(!StringUtils.isEmpty(params.getString("compulsoryExpireAt"))) {
        		car.setCompulsoryExpireAt(sdf.parse(params.getString("compulsoryExpireAt")));
        	}
        	if(!StringUtils.isEmpty(params.getString("businessExpireAt"))) {
        		car.setBusinessExpireAt(sdf.parse(params.getString("businessExpireAt")));
        	}
        	if(!StringUtils.isEmpty(params.getString("verifyAt"))) {
        		car.setVerifyAt(sdf.parse(params.getString("verifyAt")));
        	}
		} catch (ParseException e) {
			e.printStackTrace();
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.DATE_FORMAT_FAILED.getCode(), 
    				ResponseCodeI18n.DATE_FORMAT_FAILED.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
        car.setRemark(params.getString("remark"));
        car.setModifyAt(new Date());
        carDao.updateByPrimaryKeySelective(car);
    }
	
	@Override
    public List<Car> carList4Customer(Long adminId, Long customerId) {
        List<Car> carList = new ArrayList<>();
        List<CustomerBindCar> customerBindCarList = customerBindCarDao.selectByCustomerId(customerId);
        Car car = null;
        for (CustomerBindCar eachCustomerBindCar : customerBindCarList) {
            car = carDao.selectByCarId(eachCustomerBindCar.getCarId());
            if (null != car) {
                carList.add(car);
            }
        }
        return carList;
    }

    @Override
    public List<CustomerAccountResponse> accountList4Customer(Long adminId, Long customerId, Integer accountType) {
        List<CustomerAccount> accountList = accountDao.selectByCustomerId(customerId, accountType, DbConstant.STATUS_ONE);
        
        List<CustomerAccountResponse> resultList = new ArrayList<CustomerAccountResponse>();
        
        for (CustomerAccount forTemp : accountList) {
        	CustomerAccountResponse cAResponse = new CustomerAccountResponse();
        	BeanUtils.copyProperties(forTemp, cAResponse);
        	
        	if(forTemp.getCardId() != null) {
        		cAResponse.setCommodityList(cACommodityDao.selectByAccountId(forTemp.getId()));
            	cAResponse.setProjectList(cAProjectDao.selectByAccountId(forTemp.getId()));
        	}else {
        		cAResponse.setCommodityList(new ArrayList<CustomerAccountCommodity>());
            	cAResponse.setProjectList(new ArrayList<CustomerAccountProject>());
        	}
        	
        	resultList.add(cAResponse);
        }
        return resultList;
    }

	@Override
	public Customer selectByPrimaryKey(Long id) {
		return customerDao.selectByPrimaryKey(id);
	}

	@Override
	public List<CustomerSelect> findSelectObject(Long adminId, String lanType) {
		User user= userDao.findById(adminId);
		return customerDao.findSelectObject(user.getGroupId(), user.getCompanyId(), null, null, DbConstant.STATUS_ONE, lanType);
	}

	@Override
	public Map<String, Object> queryAccountList(Long adminId, Long customerId, Integer accountType, Page page) {
		User user= userDao.findById(adminId);
		List<CustomerAccount> resultList = accountDao.listData(user.getGroupId(), user.getCompanyId(), null, null, accountType, 
				DbConstant.STATUS_ONE, page.getStartIndex(), page.getPageSize());
		int total = accountDao.countData(user.getGroupId(), user.getCompanyId(), null, null, accountType, 
				DbConstant.STATUS_ONE);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
		return result;
	}

}
