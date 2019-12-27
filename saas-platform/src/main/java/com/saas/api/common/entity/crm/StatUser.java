package com.saas.api.common.entity.crm;

import java.util.Date;

import com.saas.api.common.entity.BaseEntity;

public class StatUser extends BaseEntity {
    private Long id;

    private String statDay;

    private Integer amount;

    private Date createAt;

    private Date modifyAt;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatDay() {
        return statDay;
    }

    public void setStatDay(String statDay) {
        this.statDay = statDay == null ? null : statDay.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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