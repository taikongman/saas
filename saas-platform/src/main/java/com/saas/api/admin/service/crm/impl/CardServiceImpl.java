package com.saas.api.admin.service.crm.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.api.admin.res.crm.CardResponse;
import com.saas.api.admin.res.select.crm.CardSelect;
import com.saas.api.admin.service.crm.CardService;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.common.SettleModeDao;
import com.saas.api.common.dao.crm.CardCommodityDao;
import com.saas.api.common.dao.crm.CardDao;
import com.saas.api.common.dao.crm.CardProjectDao;
import com.saas.api.common.dao.crm.InventoryRecordDao;
import com.saas.api.common.dao.crm.ProjectDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.SettleMode;
import com.saas.api.common.entity.crm.Card;
import com.saas.api.common.entity.crm.CardCommodity;
import com.saas.api.common.entity.crm.CardProject;
import com.saas.api.common.entity.crm.InventoryRecord;
import com.saas.api.common.entity.crm.Project;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class CardServiceImpl implements CardService {
	
	@Resource
    private CardDao cardDao;
	
	@Resource
    private UserDao userDao;
	
	@Resource
    private CardCommodityDao cardCommodityDao;
	
	@Resource
    private CardProjectDao cardProjectDao;
	
	@Resource
    private InventoryRecordDao inventoryRecordDao;
	
	@Resource
    private ProjectDao projectDao;
	
	@Resource
    private SettleModeDao settleModeDao;
    
	
	@Override
    public Map<String, Object> getListData(String cardName, Integer categoryId, Long adminId, String lanType, Page page) {
    	User user = userDao.findById(adminId);
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("groupId", user.getGroupId());
    	params.put("companyId", user.getCompanyId());
    	params.put("cardName", cardName);
    	params.put("categoryId", categoryId);
    	params.put("status", DbConstant.COMM_STATUS_NORMAL);
    	params.put("lanType", lanType);
    	params.put("startIndex", page.getStartIndex());
    	params.put("pageSize", page.getPageSize());
    	
    	
    	List<Card> resultList = cardDao.listData(params);
        int total = cardDao.countData(params);
        
        List<CardResponse> dataList = new ArrayList<CardResponse>();
        for(Card forTemp : resultList) {
        	CardResponse cardResponse = new CardResponse();
        	BeanUtils.copyProperties(forTemp, cardResponse);
        	
        	List<CardCommodity> commodityList = cardCommodityDao.selectByCardId(cardResponse.getId());
        	cardResponse.setCommodityList(commodityList);
        	
        	List<CardProject> projectList = cardProjectDao.selectByCardId(cardResponse.getId());
        	cardResponse.setProjectList(projectList);
        	dataList.add(cardResponse);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("dataList", dataList);
        result.put("total", total);
        return result;
    }
	

	@Override
    @Transactional
    public void addData(JSONObject jsonObj , Long adminId, String lanType){
		User user = userDao.findById(adminId);
    	JSONArray commodityList = null;
        if(null != jsonObj.get(RequestParamConstant.COMMODITY_LIST)){
        	commodityList = jsonObj.getJSONArray(RequestParamConstant.COMMODITY_LIST);
        }
        JSONArray projectList = null;
        if(null != jsonObj.get(RequestParamConstant.PROJECT_LIST)){
        	projectList = jsonObj.getJSONArray(RequestParamConstant.PROJECT_LIST);
        }
        jsonObj.remove(RequestParamConstant.COMMODITY_LIST);
        jsonObj.remove(RequestParamConstant.PROJECT_LIST);
        
        Card record = (Card) JSONObject.toBean(jsonObj, Card.class);
        
        Card check = cardDao.selectByName(user.getGroupId(), user.getCompanyId(), null, null, record.getCardName(), lanType);
        if (check != null) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CARD_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_CARD_IS_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
        record.setGroupId(user.getGroupId());
        record.setCompanyId(user.getCompanyId());
        record.setDepartmentId(user.getDepartmentId());
        record.setAdminId(adminId);
        record.setCreateTime(new Date());
        record.setStatus(DbConstant.STATUS_ONE);
        cardDao.insertSelective(record);
        
        if (null != commodityList && commodityList.size() > 0) {
        	CardCommodity cardCommodity = null;
            for (int i = 0; i < commodityList.size(); i++) {
            	cardCommodity = (CardCommodity) JSONObject.toBean(commodityList.getJSONObject(i), CardCommodity.class);
            	InventoryRecord inventory = inventoryRecordDao.selectByPrimaryKey(cardCommodity.getInventoryId());
            	cardCommodity.setCardId(record.getId());
            	cardCommodity.setCommodityId(inventory.getCommodityId());
            	cardCommodity.setCommodityCode(inventory.getCommodityCode());
            	cardCommodity.setCommodityName(inventory.getCommodityName());
            	cardCommodity.setSupplierId(inventory.getSupplierId());
            	cardCommodity.setSupplierCode(inventory.getSupplierCode());
            	cardCommodity.setSupplierName(inventory.getSupplierName());
            	cardCommodity.setUnitId(inventory.getUnitId());
            	cardCommodity.setUnitName(inventory.getUnitName());
            	if(cardCommodity.getSettleMode() != null) {
            		SettleMode settleMode = settleModeDao.findById(cardCommodity.getSettleMode());
            		if(settleMode != null) {
            			cardCommodity.setSettleName(settleMode.getName());
            		}
            	}
            	
            	cardCommodity.setCreateTime(new Date());
            	cardCommodityDao.insertSelective(cardCommodity);
            }
        }
        
        if (null != projectList && projectList.size() > 0) {
        	CardProject cardProject = null;
            for (int i = 0; i < projectList.size(); i++) {
            	cardProject = (CardProject) JSONObject.toBean(projectList.getJSONObject(i), CardProject.class);
            	Project project = projectDao.selectByPrimaryKey(cardProject.getProjectId());
            	cardProject.setCardId(record.getId());
            	cardProject.setProjectCode(project.getProjectCode());
            	cardProject.setProjectName(project.getProjectName());
            	cardProject.setUnitId(project.getUnitId());
            	cardProject.setUnitName(project.getUnitName());
            	if(cardProject.getSettleMode() != null) {
            		SettleMode settleMode = settleModeDao.findById(cardProject.getSettleMode());
            		if(settleMode != null) {
            			cardProject.setSettleName(settleMode.getName());
            		}
            	}
            	cardProject.setCreateTime(new Date());
            	cardProjectDao.insertSelective(cardProject);
            }
        }
    }

    @Override
    public void deleteData(Long id) {
        Card card = cardDao.selectByPrimaryKey(id);
        card.setStatus(DbConstant.COMM_STATUS_DEL);
        card.setUpdateTime(new Date());
        cardDao.updateByPrimaryKeySelective(card);
    }

    @Override
    @Transactional
    public void modifyData(JSONObject jsonObj , Long adminId, String lanType) {
    	User user = userDao.findById(adminId);
    	JSONArray commodityList = null;
        if(null != jsonObj.get(RequestParamConstant.COMMODITY_LIST)){
        	commodityList = jsonObj.getJSONArray(RequestParamConstant.COMMODITY_LIST);
        }
        JSONArray projectList = null;
        if(null != jsonObj.get(RequestParamConstant.PROJECT_LIST)){
        	projectList = jsonObj.getJSONArray(RequestParamConstant.PROJECT_LIST);
        }
        jsonObj.remove(RequestParamConstant.COMMODITY_LIST);
        jsonObj.remove(RequestParamConstant.PROJECT_LIST);
        
        Card record = (Card) JSONObject.toBean(jsonObj, Card.class);
        Card check = cardDao.selectByPrimaryKey(record.getId());
        if (check == null) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CARD_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_CARD_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        } else {
        	Card existCard = cardDao.selectByName(user.getGroupId(), user.getCompanyId(), null, null, record.getCardName(), lanType);
            if (null != existCard && !existCard.getId().equals(record.getId())) {
            	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CARD_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_CARD_IS_EXIST.getMessage(), lanType);
                throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            }
        }
        
        record.setUpdateTime(new Date());
        cardDao.updateByPrimaryKeySelective(record);
        
        if (null != commodityList && commodityList.size() > 0) {
        	CardCommodity cardCommodity = null;
        	cardCommodityDao.deleteByCardId(record.getId());
            for (int i = 0; i < commodityList.size(); i++) {
            	cardCommodity = (CardCommodity) JSONObject.toBean(commodityList.getJSONObject(i), CardCommodity.class);
            	InventoryRecord inventory = inventoryRecordDao.selectByPrimaryKey(cardCommodity.getInventoryId());
            	cardCommodity.setCardId(record.getId());
            	cardCommodity.setCommodityId(inventory.getCommodityId());
            	cardCommodity.setCommodityCode(inventory.getCommodityCode());
            	cardCommodity.setCommodityName(inventory.getCommodityName());
            	cardCommodity.setSupplierId(inventory.getSupplierId());
            	cardCommodity.setSupplierCode(inventory.getSupplierCode());
            	cardCommodity.setSupplierName(inventory.getSupplierName());
            	cardCommodity.setUnitId(inventory.getUnitId());
            	cardCommodity.setUnitName(inventory.getUnitName());
            	if(cardCommodity.getSettleMode() != null) {
            		SettleMode settleMode = settleModeDao.findById(cardCommodity.getSettleMode());
            		if(settleMode != null) {
            			cardCommodity.setSettleName(settleMode.getName());
            		}
            	}
            	
            	cardCommodity.setCreateTime(new Date());
        		cardCommodityDao.insertSelective(cardCommodity);
            }
        }
        
        if (null != projectList && projectList.size() > 0) {
        	CardProject cardProject = null;
        	cardProjectDao.deleteByByCardId(record.getId());
            for (int i = 0; i < projectList.size(); i++) {
            	cardProject = (CardProject) JSONObject.toBean(projectList.getJSONObject(i), CardProject.class);
            	Project project = projectDao.selectByPrimaryKey(cardProject.getProjectId());
            	cardProject.setCardId(record.getId());
            	cardProject.setProjectCode(project.getProjectCode());
            	cardProject.setProjectName(project.getProjectName());
            	cardProject.setUnitId(project.getUnitId());
            	cardProject.setUnitName(project.getUnitName());
            	if(cardProject.getSettleMode() != null) {
            		SettleMode settleMode = settleModeDao.findById(cardProject.getSettleMode());
            		if(settleMode != null) {
            			cardProject.setSettleName(settleMode.getName());
            		}
            	}
            	cardProject.setCreateTime(new Date());
            	cardProjectDao.insertSelective(cardProject);
            }
        }
    }
	
	@Override
	public Card selectByPrimaryKey(Long id) {
		return cardDao.selectByPrimaryKey(id);
	}
	
	@Override
	public Card selectByName(Long adminId, String cardName, String lanType) {
		User user = userDao.findById(adminId);
		return cardDao.selectByName(user.getGroupId(), user.getDepartmentId(), null, null, cardName, lanType);
	}
	
	@Override
	public List<Card> findByObject(Card record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setAdminId(null);
		return cardDao.findByObject(record);
	}
	
	@Override
	public List<CardSelect> findSelectObject(Long adminId, String lanType) {
		Card record = new Card();
		User user = userDao.findById(adminId);
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setLanType(lanType);
		record.setStatus(DbConstant.STATUS_ONE);
		return cardDao.findSelectObject(record);
	}
	

}
