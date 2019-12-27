package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import com.saas.api.common.entity.BaseEntity;

public class StatRevenue extends BaseEntity {
    private Long id;

    private String statDay;

    private BigDecimal amount;

    private Date createAt;

    private Date modityAt;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModityAt() {
        return modityAt;
    }

    public void setModityAt(Date modityAt) {
        this.modityAt = modityAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}