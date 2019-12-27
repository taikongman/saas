package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.PurchaseRecord;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(PurchaseRecord record);

    int insertSelective(PurchaseRecord record);

    PurchaseRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PurchaseRecord record);

    int updateByPrimaryKey(PurchaseRecord record);
    
    void updateIsToInventory(PurchaseRecord record);

    List<PurchaseRecord> listData(Map<?, ?> params);

    int countData(Map<?, ?> params);
    
    List<PurchaseRecord> findByObject(PurchaseRecord record);
    
    List<PurchaseRecord> selectByPurchaseCode(String purchaseCode);

    int countByCommodityId(Long commodityId);
}