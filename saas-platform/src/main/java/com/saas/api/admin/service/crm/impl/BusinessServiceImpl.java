package com.saas.api.admin.service.crm.impl;


import com.saas.api.admin.service.crm.BusinessService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.crm.*;
import com.saas.api.common.dao.sys.ConfigDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.*;
import com.saas.api.common.entity.sys.Config;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private CommodityDao commodityDao;

    @Resource
    private UserDao userDao;
    
    @Resource
    private CustomerPayDao payDao;

    @Resource
    private CustomerConsumeRecordDao consumeRecordDao;

    @Resource
    private CustomerAccountDao accountDao;

    @Resource
    private CardDao cardDao;

    @Resource
    private CustomerRechargeRecordDao rechargeRecordDao;
    
    @Resource
    private CardCommodityDao cardCommodityDao;

    @Resource
    private CardProjectDao cardProjectDao;
    
    @Resource
    private CustomerAccountCommodityDao cACommodityDao;

    @Resource
    private CustomerAccountProjectDao cAProjectDao;

    @Resource
    private ConfigDao configDao;

    @Resource
    private SysDataDao sysDataDao;

    @Resource
    private FeedbackDao feedbackDao;
    
    
    
    
    @Override
    public List<User> userList(User record) {
        List<User> userList = userDao.findByObject(record);
        return userList;
    }

    

    

    @Override
    public Map<String, Object> inventoryWarn(Long adminId, String lanType, String title, String code, Integer warnType, Page page) {
    	User user = userDao.findById(adminId);
        Map<String, Object> result = new HashMap<>();
        int total = 0;
        List<Commodity> commodityList = null;
        //低量报警 warnType为1，
        if (warnType.equals(DbConstant.WARN_TYPE_DOWN)) {
        	commodityList = commodityDao.warnDownList(user.getGroupId(), user.getCompanyId(), user.getDepartmentId(), adminId, 
            		lanType, code, title, DbConstant.COMM_STATUS_NORMAL, page.getStartIndex(), page.getPageSize());
            
            total = commodityDao.countWarnDown(user.getGroupId(), user.getCompanyId(), user.getDepartmentId(), adminId, 
            		lanType, code, title, DbConstant.COMM_STATUS_NORMAL);
        } else {
            commodityList = commodityDao.warnUpList(user.getGroupId(), user.getCompanyId(), user.getDepartmentId(), adminId, 
            		lanType, code, title, DbConstant.COMM_STATUS_NORMAL, page.getStartIndex(), page.getPageSize());
            
            total = commodityDao.countWarnUp(user.getGroupId(), user.getCompanyId(), user.getDepartmentId(), adminId, 
            		lanType, code, title, DbConstant.COMM_STATUS_NORMAL);
        }
        result.put("dataList", commodityList);
        result.put("total", total);
        return result;
    }

    
    @Override
    @Transactional
    public void settle(JSONObject params, Long adminId, String lanType) {
        //账单，账户，支付pay，
    	User user = userDao.findById(adminId);
        Order order = orderDao.selectByPrimaryKey(params.getLong(RequestParamConstant.ORDER_ID));
        if (null != order && order.getStatus() >= DbConstant.ORDER_STATUS_FINISH) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_ALREADY_SETTLE.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ORDER_ALREADY_SETTLE.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
        
        CustomerPay pay = new CustomerPay();
        pay.setOrderId(order.getId());
        pay.setCustomerId(order.getCustomerId());
        pay.setCustomerName(order.getCustomerName());
        pay.setCustomerPhone(order.getCustomerPhone());
        pay.setCashMode(params.getInt(RequestParamConstant.CASH_MODE));
        pay.setCarId(order.getCarId());
        pay.setCarNo(order.getCarNo());
        pay.setProjectTypeId(order.getProjectTypeId());
       
        pay.setNeedPay(new BigDecimal(params.getString(RequestParamConstant.NEED_PAY)));
        pay.setRealPay(new BigDecimal(params.getString(RequestParamConstant.REAL_PAY)));
        if (params.get(RequestParamConstant.DISCOUNT) != null && 
        		!StringUtils.isEmpty(params.get(RequestParamConstant.DISCOUNT))) {
            pay.setDiscount(new BigDecimal(params.getString(RequestParamConstant.DISCOUNT)));
        }
        pay.setAlreadyInvoice(DbConstant.INT_ZERO);
        if (params.get(RequestParamConstant.INVOICE_NO) != null && 
        		!StringUtils.isEmpty(params.getString(RequestParamConstant.INVOICE_NO))) {
            pay.setAlreadyInvoice(DbConstant.INT_ONE);
            pay.setInvoiceNo(params.getString(RequestParamConstant.INVOICE_NO));
            pay.setInvoiceId(params.getInt(RequestParamConstant.INVOICE_ID));
            order.setAlreadyInvoice(DbConstant.INT_ONE);
            order.setInvoiceNo(params.getString(RequestParamConstant.INVOICE_NO));
            order.setInvoiceId(params.getInt(RequestParamConstant.INVOICE_ID));
        }
        pay.setCreateTime(new Date());
        pay.setVersion(DbConstant.INIT_VERSION);
        pay.setStatus(DbConstant.STATUS_ONE);
        payDao.insert(pay);
        
        BigDecimal accountPay = processAccount(params, pay.getId(), lanType);
        pay.setAccountPay(accountPay);
        payDao.updateByPrimaryKeySelective(pay);
        
        order.setPayId(pay.getId());
        order.setSettleMemberId(user.getAdminId());
        order.setSettleMemberName(user.getRealName());
        order.setStatus(DbConstant.ORDER_STATUS_FINISH);
        order.setSettleTime(new Date());
        if (params.get(RequestParamConstant.SETTLE_REMARK) != null && 
        		!StringUtils.isEmpty(params.getString(RequestParamConstant.SETTLE_REMARK))) {
            order.setSettleRemark(params.getString(RequestParamConstant.SETTLE_REMARK));
        }
        orderDao.updateByPrimaryKeySelective(order);
    }
    
    @Override
    @Transactional
    public void batchSettle(JSONObject params, Long adminId, String lanType) {
    	User user = userDao.findById(adminId);
    	
    	 JSONArray orderList = null;
         if(null != params.get(RequestParamConstant.ORDER_LIST)) {
        	 orderList = params.getJSONArray(RequestParamConstant.PROJECT_LIST);
         }
         if(orderList != null ) {
         	for (int ci = 0; ci < orderList.size(); ci++) {
         		Long orderId = params.getLong(RequestParamConstant.ORDER_ID);
                Integer cashMode = params.getInt(RequestParamConstant.CASH_MODE);
                Order order = orderDao.selectByPrimaryKey(orderId);
                
                CustomerPay pay = new CustomerPay();
                pay.setOrderId(order.getId());
                pay.setCustomerId(order.getCustomerId());
                pay.setCustomerName(order.getCustomerName());
                pay.setCustomerPhone(order.getCustomerPhone());
                pay.setCashMode(cashMode);
                pay.setCarId(order.getCarId());
                pay.setCarNo(order.getCarNo());
                pay.setProjectTypeId(order.getProjectTypeId());
               
                pay.setNeedPay(new BigDecimal(params.getString(RequestParamConstant.NEED_PAY)));
                pay.setRealPay(new BigDecimal(params.getString(RequestParamConstant.REAL_PAY)));
                if (params.get(RequestParamConstant.DISCOUNT) != null && 
                		!StringUtils.isEmpty(params.get(RequestParamConstant.DISCOUNT))) {
                    pay.setDiscount(new BigDecimal(params.getString(RequestParamConstant.DISCOUNT)));
                }
                pay.setAlreadyInvoice(DbConstant.INT_ZERO);
                if (params.get(RequestParamConstant.INVOICE_NO) != null && 
                		!StringUtils.isEmpty(params.getString(RequestParamConstant.INVOICE_NO))) {
                    pay.setAlreadyInvoice(DbConstant.INT_ONE);
                    pay.setInvoiceNo(params.getString(RequestParamConstant.INVOICE_NO));
                    pay.setInvoiceId(params.getInt(RequestParamConstant.INVOICE_ID));
                    order.setAlreadyInvoice(DbConstant.INT_ONE);
                    order.setInvoiceNo(params.getString(RequestParamConstant.INVOICE_NO));
                    order.setInvoiceId(params.getInt(RequestParamConstant.INVOICE_ID));
                }
                pay.setCreateTime(new Date());
                pay.setVersion(DbConstant.INIT_VERSION);
                pay.setStatus(DbConstant.STATUS_ONE);
                payDao.insert(pay);
                
                BigDecimal accountPay = processAccount(params, pay.getId(), lanType);
                pay.setAccountPay(accountPay);
                payDao.updateByPrimaryKeySelective(pay);
                
                if (null != params.get(RequestParamConstant.INVOICE_NO) && !org.springframework.util.StringUtils.isEmpty(params.getString(RequestParamConstant.INVOICE_NO))) {
                    order.setAlreadyInvoice(1);
                    order.setInvoiceNo(params.getString(RequestParamConstant.INVOICE_NO));
                }
                order.setPayId(pay.getId());
                order.setStatus(DbConstant.ORDER_STATUS_FINISH);
                order.setSettleTime(new Date());
                order.setSettleMemberId(user.getAdminId());
                order.setSettleMemberName(user.getRealName());
                if (null != params.get(RequestParamConstant.SETTLE_REMARK)) {
                    order.setSettleRemark(params.getString(RequestParamConstant.SETTLE_REMARK));
                }
                orderDao.updateByPrimaryKeySelective(order);
         	}
         }
    }
    
    private BigDecimal processAccount(JSONObject params, Long payId, String lanType) {
        JSONArray accountList = params.getJSONArray(RequestParamConstant.ACCOUNT_LIST);
        BigDecimal totalAccountPay = new BigDecimal(CommonConstant.INT_ZERO);
        BigDecimal currentUsed;
        if (null != accountList) {
            CustomerAccount account = null;
            JSONObject accountJson = null;
            for (int i = 0; i < accountList.size(); i++) {
            	accountJson = accountList.getJSONObject(i);
            	Long accountId = accountJson.getLong(RequestParamConstant.ACCOUNT_ID);
            	currentUsed = new BigDecimal(accountJson.getString(RequestParamConstant.USED));
            	Integer consumeType = null;
                if(null != accountJson.get(RequestParamConstant.CONSUME_TYPE)) {
                	consumeType = accountJson.getInt(RequestParamConstant.CONSUME_TYPE);
                }
                
                account = accountDao.selectByPrimaryKey(accountId);
                if (null == account) {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ACCOUNT_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_ACCOUNT_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }
                
                BigDecimal newUsed = account.getUsed().add(currentUsed);
                if (newUsed.compareTo(account.getAmount()) > 1) {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ACCOUNT_BALANCE_IS_ERROR.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_ACCOUNT_BALANCE_IS_ERROR.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }
                
                if(account.getAccountType().equals(CommonConstant.INT_TWO)
                		&& account.getCardId() != null) {
                	totalAccountPay = totalAccountPay.add(currentUsed);
                }
                
                account.setUsed(newUsed);
                account.setRemain(account.getAmount().subtract(newUsed));
                accountDao.updateByPrimaryKeySelective(account);
                
                JSONArray commodityList = null;
                if(null != accountJson.get(RequestParamConstant.COMMODITY_LIST)) {
                	commodityList = accountJson.getJSONArray(RequestParamConstant.COMMODITY_LIST);
                }
                JSONArray projectList = null;
                if(null != accountJson.get(RequestParamConstant.PROJECT_LIST)) {
                	projectList = accountJson.getJSONArray(RequestParamConstant.PROJECT_LIST);
                }
                if(commodityList != null ) {
                	JSONObject commodityJson = null;
                	for (int ci = 0; ci < commodityList.size(); ci++) {
                		commodityJson = commodityList.getJSONObject(i);
                		Long id = commodityJson.getLong(RequestParamConstant.ID);
                		BigDecimal cardUsed = new BigDecimal(commodityJson.getString(RequestParamConstant.USED));
                		CustomerAccountCommodity updateCACommodity = cACommodityDao.selectByPrimaryKey(id);
                		
                		if (cardUsed.compareTo(updateCACommodity.getRemain()) > 1) {
                        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ACCOUNT_COMMODITY_BALANCE_IS_ERROR.getCode(), 
                    				ResponseCodeI18n.MODULE_CRM_ACCOUNT_COMMODITY_BALANCE_IS_ERROR.getMessage(), lanType);
                            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                        }
                		updateCACommodity.setUsed(updateCACommodity.getUsed().add(cardUsed));
                		updateCACommodity.setRemain(updateCACommodity.getRemain().subtract(cardUsed));
                		
                		cACommodityDao.updateByPrimaryKeySelective(updateCACommodity);
                	}
                }
                if(projectList != null ) {
                	JSONObject projectJson = null;
                	for (int pi = 0; pi < projectList.size(); pi++) {
                		projectJson = projectList.getJSONObject(i);
                		Long id = projectJson.getLong(RequestParamConstant.ID);
                		BigDecimal cardUsed = new BigDecimal(projectJson.getString(RequestParamConstant.USED));
                		CustomerAccountProject updateCAProject = cAProjectDao.selectByPrimaryKey(id);
                		
                		if (cardUsed.compareTo(updateCAProject.getRemain()) > 1) {
                        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ACCOUNT_PROJECT_BALANCE_IS_ERROR.getCode(), 
                    				ResponseCodeI18n.MODULE_CRM_ACCOUNT_PROJECT_BALANCE_IS_ERROR.getMessage(), lanType);
                            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                        }
                		updateCAProject.setUsed(updateCAProject.getUsed().add(cardUsed));
                		updateCAProject.setRemain(updateCAProject.getRemain().subtract(cardUsed));
                		
                		cAProjectDao.updateByPrimaryKeySelective(updateCAProject);
                	}
                }
                
                insertConsumeRecord(account, accountJson.getString(RequestParamConstant.USED), payId, null,consumeType);
            }
        }
        return totalAccountPay;
    }

    private void insertConsumeRecord(CustomerAccount account, String used, Long payId, Long cardContentId, Integer consumeType) {
        CustomerConsumeRecord consumeRecord = new CustomerConsumeRecord();
        consumeRecord.setCustomerId(account.getCustomerId());
        consumeRecord.setConsumeType(consumeType);
        consumeRecord.setAccountId(account.getId());
        consumeRecord.setCardId(account.getCardId());
        consumeRecord.setConsumeType(consumeType);
        consumeRecord.setCardContentId(cardContentId);
        consumeRecord.setPayId(payId);
        consumeRecord.setAmount(new BigDecimal(used));
        consumeRecord.setIntegral(new BigDecimal(used));
        consumeRecord.setCreateTime(new Date());
        consumeRecord.setStatus(DbConstant.STATUS_ONE);
        consumeRecordDao.insertSelective(consumeRecord);
    }

    @Override
    public void suspend(Long adminId, Long orderId) {
        Order order = orderDao.selectByPrimaryKey(orderId);
        order.setStatus(DbConstant.ORDER_STATUS_SUSPEND);
        order.setUpdateTime(new Date());
        orderDao.updateByPrimaryKeySelective(order);
    }

    
    @Override
    @Transactional
    public void deleteRecharge(Long accountId, Long adminId, String lanType) {
    	CustomerAccount check = accountDao.selectByPrimaryKey(accountId);
    	if(check.getUsed().compareTo(BigDecimal.ZERO) > 0) {
    		ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ACCOUNT_BALANCE_HAS_BEEN_USED.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ACCOUNT_BALANCE_HAS_BEEN_USED.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
    	}
    	CustomerAccount account = new CustomerAccount();
    	account.setId(accountId);
    	account.setUpdateTime(new Date());
    	account.setStatus(DbConstant.STATUS_TWO);
    	accountDao.updateByPrimaryKeySelective(account);
    	
    	CustomerRechargeRecord rechargeRecord = new CustomerRechargeRecord();
    	rechargeRecord.setAccountId(accountId);
    	rechargeRecord.setUpdateTime(new Date());
    	rechargeRecord.setStatus(DbConstant.STATUS_TWO);
    	rechargeRecordDao.updateByStatusAccountId(rechargeRecord);
    }
    
    @Override
    @Transactional
    public void recharge(JSONObject params, Long adminId, String lanType) throws ParseException {    	
    	User user = userDao.findById(adminId);
    	Long cardId = null;
        Long customerId = null;
        Long accountId = null;
        CustomerAccount account = null;
        Date expiryTime = null;
        Card card = null;
        if (null != params.get(RequestParamConstant.CARD_ID)) {
            cardId = params.getLong(RequestParamConstant.CARD_ID);
        }
        if(null != params.get(RequestParamConstant.CUSTOMER_ID)) {
        	customerId = params.getLong(RequestParamConstant.CUSTOMER_ID);
        }
        if (null != params.get(RequestParamConstant.EXPIRY_TIME)) {
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
            expiryTime = sdf.parse(params.getString(RequestParamConstant.EXPIRY_TIME));
        }
        if (null != params.get(RequestParamConstant.ACCOUNT_ID)) {
        	accountId = params.getLong(RequestParamConstant.ACCOUNT_ID);
        }
        BigDecimal amount = BigDecimal.ZERO;
        if(null != params.get(RequestParamConstant.AMOUNT)) {
        	amount = new BigDecimal(params.getString(RequestParamConstant.AMOUNT));
        }
        BigDecimal gift = BigDecimal.ZERO;
        if(null != params.get(RequestParamConstant.GIFT)) {
        	gift = new BigDecimal(params.getString(RequestParamConstant.GIFT));
        }
        String remark = null;
        if(null != params.get(RequestParamConstant.REMARK)) {
        	remark = params.getString(RequestParamConstant.REMARK);
        }
        
        if(customerId == null) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_CUSTOMER_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
        
        //如果传入cardId，则是购买充值卡
        if (null != cardId) {
        	account = new CustomerAccount();
        	account.setGroupId(user.getGroupId());
        	account.setCompanyId(user.getCompanyId());
        	account.setDepartmentId(user.getDepartmentId());
        	account.setAdminId(adminId);
            account.setCustomerId(customerId);
            account.setAccountType(params.getInt(RequestParamConstant.ACCOUNT_TYPE));
            account.setCardId(cardId);
            account.setAmount(amount.add(gift));
            account.setUsed(BigDecimal.ZERO);
            account.setRemain(account.getAmount());
            account.setIntegral(account.getAmount());
            account.setEffectiveTime(new Date());
            account.setExpiryTime(expiryTime);
            account.setRemark(remark);
            account.setCreateTime(new Date());
            account.setStatus(DbConstant.STATUS_ONE);
            account.setVersion(DbConstant.INIT_VERSION);
            accountDao.insertSelective(account);
            
            card = cardDao.selectByPrimaryKey(cardId);
            if(null == card) {
            	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CARD_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_CARD_IS_NOT_EXIST.getMessage(), lanType);
                throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            }
            
            // 处理充值卡里的项目
            updateAccount(account.getId(), card.getId());
        } else {
        	// 如果传了 accountId 在已有账户充值
        	if(null != accountId) {
        		account = accountDao.selectByPrimaryKey(accountId);
                if (null == account) {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ACCOUNT_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_ACCOUNT_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }
                account.setAmount(account.getAmount().add(amount).add(gift));
                account.setRemain(account.getRemain().add(amount).add(gift));
                account.setIntegral(account.getIntegral().add(amount).add(gift));
                account.setRemark(remark);
                account.setExpiryTime(expiryTime);
                accountDao.updateByPrimaryKeySelective(account);
        	} else {
        		List<CustomerAccount> accountList = accountDao.selectByCustomerId(customerId, DbConstant.ACCOUNT_TYPE_NORMAL, DbConstant.STATUS_ONE);
        		if(accountList != null && accountList.size() > 0) {
        			account = accountList.get(0);
        			account.setAmount(account.getAmount().add(amount).add(gift));
                    account.setRemain(account.getRemain().add(amount).add(gift));
                    account.setIntegral(account.getIntegral().add(amount).add(gift));
                    account.setRemark(remark);
                    account.setExpiryTime(expiryTime);
                    accountDao.updateByPrimaryKeySelective(account);
        		}else {
        			account = new CustomerAccount();
        			account.setGroupId(user.getGroupId());
                	account.setCompanyId(user.getCompanyId());
                	account.setDepartmentId(user.getDepartmentId());
                	account.setAdminId(adminId);
                    account.setCustomerId(customerId);
                    account.setAccountType(params.getInt(RequestParamConstant.ACCOUNT_TYPE));
                    account.setCardId(cardId);
                    account.setAmount(amount.add(gift));
                    account.setUsed(BigDecimal.ZERO);
                    account.setRemain(account.getAmount());
                    account.setIntegral(account.getAmount());
                    account.setEffectiveTime(new Date());
                    account.setExpiryTime(expiryTime);
                    account.setRemark(remark);
                    account.setCreateTime(new Date());
                    account.setStatus(DbConstant.STATUS_ONE);
                    account.setVersion(DbConstant.INIT_VERSION);
                    accountDao.insertSelective(account);
        		}
        	}
        }

        CustomerRechargeRecord rechargeRecord = new CustomerRechargeRecord();
        rechargeRecord.setCustomerId(account.getCustomerId());
        rechargeRecord.setRechargeMode(params.getInt(RequestParamConstant.RECHARGE_MODE));
        rechargeRecord.setAccountType(account.getAccountType());
        rechargeRecord.setAccountId(account.getId());
        rechargeRecord.setCardId(account.getCardId());
        if (null != card) {
            rechargeRecord.setAmount(card.getAmount());
            rechargeRecord.setGift(gift);
            rechargeRecord.setTotal(card.getAmount().add(gift));
        } else {
            rechargeRecord.setAmount(amount);
            rechargeRecord.setGift(gift);
            rechargeRecord.setTotal(amount.add(gift));
        }
        rechargeRecord.setAccountBalance(account.getAmount());
        rechargeRecord.setOperatorId(params.getLong(RequestParamConstant.OPERATOR_ID));
        rechargeRecord.setOperatorName(params.getString(RequestParamConstant.OPERATOR_NAME));
        rechargeRecord.setExpiryTime(expiryTime);
        rechargeRecord.setRemark(remark);
        rechargeRecord.setCreateTime(new Date());
        rechargeRecord.setStatus(DbConstant.STATUS_ONE);
        rechargeRecordDao.insertSelective(rechargeRecord);
    }

    private void updateAccount(Long accountId, Long cardId) {
    	List<CardCommodity> cardCommList = cardCommodityDao.selectByCardId(cardId);
    	List<CardProject> cardProjList = cardProjectDao.selectByCardId(cardId);
    	
    	CustomerAccountCommodity cACommodity= null;
    	CustomerAccountProject cAProject= null;
    	for(CardCommodity forTemp : cardCommList) {
    		cACommodity = new CustomerAccountCommodity();
    		BeanUtils.copyProperties(forTemp, cACommodity);
    		cACommodity.setAccountId(accountId);
    		cACommodity.setCardId(cardId);
    		cACommodity.setUsed(BigDecimal.ZERO);
    		cACommodity.setRemain(forTemp.getAmount());
    		cACommodity.setCreateTime(new Date());
    		cACommodityDao.insertSelective(cACommodity);
    	}
    	
    	for(CardProject forTemp : cardProjList) {
    		cAProject = new CustomerAccountProject();
    		BeanUtils.copyProperties(forTemp, cAProject);
    		cAProject.setAccountId(accountId);
    		cAProject.setCardId(cardId);
    		cAProject.setUsed(BigDecimal.ZERO);
    		cAProject.setRemain(forTemp.getAmount());
    		cAProject.setCreateTime(new Date());
    		cAProjectDao.insertSelective(cAProject);
    	}
    }

    @Override
    public void delay(JSONObject params, Long adminId, String lanType) throws ParseException {
        CustomerAccount account = accountDao.selectByPrimaryKey(params.getLong(RequestParamConstant.ACCOUNT_ID));
        if (null == account) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ACCOUNT_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ACCOUNT_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
        Date expireAt = null;
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
        expireAt = sdf.parse(params.getString(RequestParamConstant.EXPIRY_TIME));
        if (account.getExpiryTime().compareTo(expireAt) < CommonConstant.INT_ZERO) {
            account.setExpiryTime(expireAt);
        } else {            
            ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ACCOUNT_EFFECTIVE_TIME_LESS_THAN_BEFORE.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ACCOUNT_EFFECTIVE_TIME_LESS_THAN_BEFORE.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
        accountDao.updateByPrimaryKeySelective(account);
    }

    @Override
    public Map<String, Object> rechargeRecordList(Long adminId, Long customerId, Integer accountType, Page page) {
        List<CustomerRechargeRecord> rechargeRecordList = rechargeRecordDao.selectByCustomerId(customerId, 
        		accountType, DbConstant.STATUS_ONE, page.getStartIndex(), page.getPageSize());
        int total = rechargeRecordDao.countByCustomerId(customerId, accountType, DbConstant.STATUS_ONE);
        Map<String, Object> result = new HashMap<>();
        result.put("dataList", rechargeRecordList);
        result.put("total", total);
        return result;
    }

    public String ocr(JSONObject params, String lanType){
        String imgStr = params.getString(RequestParamConstant.BASE_64);
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";
        String apiResult = "";
        String ocrResult = "";
        try {
            String apiParams = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            String accessToken = getOcrAuth(CommonConstant.DU_OCR_API_KEY, CommonConstant.DU_OCR_SECRET_KEY, lanType);
            apiResult = HttpUtil.post(otherHost, accessToken, apiParams);
            log.info("ocr result:" + apiResult);
            JSONObject resultJson = JSONObject.fromObject(apiResult);
            JSONArray wordsResult = resultJson.getJSONArray("words_result");
            if (null != wordsResult && wordsResult.size() > 0){
                ocrResult = wordsResult.getJSONObject(0).getString("words");
            }
            return ocrResult;
        } catch (Exception e) {
            e.printStackTrace();
            ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_BAIDU_OCR_AUTOLICENSE_FAILED.getCode(), 
    				ResponseCodeI18n.MODULE_BAIDU_OCR_AUTOLICENSE_FAILED.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
    }
    
    
    private String getOcrAuth(String ak, String sk, String lanType) {
        Config existToken = configDao.findByKeyName(CommonConstant.DU_OCR_TOKEN);
        if (null != existToken && existToken.getUpdateTime().compareTo(new Date()) > 0) {
            return existToken.getKeyValue();
        }

        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.err.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            log.info("ocr result:" + result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            String access_token = jsonObject.getString("access_token");
            int expires_in = jsonObject.getInt("expires_in");
//            Config config = configDao.findByKeyName(CommonConstant.DU_OCR_TOKEN);
            existToken.setKeyValue(access_token);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, expires_in);
            calendar.getTime();
            existToken.setUpdateTime(calendar.getTime());
            configDao.updateData(existToken);
            return access_token;
        } catch (Exception e) {
        	log.error("获取ocr token失败！");
            e.printStackTrace();
            ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_BAIDU_OCR_GET_OCR_TOKEN_FAILED.getCode(), 
    				ResponseCodeI18n.MODULE_BAIDU_OCR_GET_OCR_TOKEN_FAILED.getMessage(), lanType);
            
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
    }

    public List<SysData> sysData(Integer dataType){
        List<SysData> sysDataList = sysDataDao.selectByDataType(dataType);
        return sysDataList;
    }

    public Map<String, Object> workData(Map<String, Object> params){
        Map<String, Object> result = new HashMap<>();
        Integer total = orderDao.countOrder(params);
        params.put(RequestParamConstant.STATUS, DbConstant.ORDER_STATUS_DOING);
        Integer doing = orderDao.countOrder(params);
        params.put(RequestParamConstant.STATUS, DbConstant.ORDER_STATUS_FINISH);
        Integer finish = orderDao.countOrder(params);
        result.put("total", total);
        result.put("doing", doing);
        result.put("finish", finish);
        return result;
    }

    public void addFeedback(JSONObject params, Long adminId, String lanType){
    	User user = userDao.findById(adminId);
    	
        Feedback feedback = (Feedback) JSONObject.toBean(params, Feedback.class);
        feedback.setMemberId(user.getAdminId().intValue());
        feedback.setMemberName(user.getRealName());
        feedback.setStatus(DbConstant.FEEDBACK_STATUS_INIT);
        feedback.setCreateAt(new Date());
        feedback.setVersion(DbConstant.INIT_VERSION);
        feedback.setLanType(lanType);
        feedbackDao.insert(feedback);
    }
}
