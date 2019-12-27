package com.saas.api.common.entity.crm;

import java.util.Date;

import com.saas.api.common.entity.BaseEntity;

public class Car extends BaseEntity{
    private Long id;
    
    private Long carId;

    private String carNo;

    private Integer brandId;
    private String brandName;
    private Integer seriesId;
	private String seriesName;
	private Integer modelId;
	private String modelName;
	private Integer year;

    private String engineNo;

    private String vinCode;

    private String chassisNo;

    private Integer mileage;

    private Date buyAt;

    private Date nextMaintenanceAt;

    private String compulsoryInsurance;

    private Date compulsoryExpireAt;

    private String businessInsurance;

    private Date businessExpireAt;

    private Date verifyAt;

    private Integer toStoreAmount;

    private Integer status;

    private String remark;

    private Date createAt;

    private Date modifyAt;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo == null ? null : engineNo.trim();
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode == null ? null : vinCode.trim();
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo == null ? null : chassisNo.trim();
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Date getBuyAt() {
        return buyAt;
    }

    public void setBuyAt(Date buyAt) {
        this.buyAt = buyAt;
    }

    public Date getNextMaintenanceAt() {
        return nextMaintenanceAt;
    }

    public void setNextMaintenanceAt(Date nextMaintenanceAt) {
        this.nextMaintenanceAt = nextMaintenanceAt;
    }

    public String getCompulsoryInsurance() {
        return compulsoryInsurance;
    }

    public void setCompulsoryInsurance(String compulsoryInsurance) {
        this.compulsoryInsurance = compulsoryInsurance == null ? null : compulsoryInsurance.trim();
    }

    public Date getCompulsoryExpireAt() {
        return compulsoryExpireAt;
    }

    public void setCompulsoryExpireAt(Date compulsoryExpireAt) {
        this.compulsoryExpireAt = compulsoryExpireAt;
    }

    public String getBusinessInsurance() {
        return businessInsurance;
    }

    public void setBusinessInsurance(String businessInsurance) {
        this.businessInsurance = businessInsurance == null ? null : businessInsurance.trim();
    }

    public Date getBusinessExpireAt() {
        return businessExpireAt;
    }

    public void setBusinessExpireAt(Date businessExpireAt) {
        this.businessExpireAt = businessExpireAt;
    }

    public Date getVerifyAt() {
        return verifyAt;
    }

    public void setVerifyAt(Date verifyAt) {
        this.verifyAt = verifyAt;
    }

    public Integer getToStoreAmount() {
        return toStoreAmount;
    }

    public void setToStoreAmount(Integer toStoreAmount) {
        this.toStoreAmount = toStoreAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}