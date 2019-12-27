package com.saas.api.common.entity.crm;

import java.util.Date;

public class Feedback {
    private Integer id;

    private Integer memberId;

    private String memberName;

    private String phone;

    private String content;

    private String picUrl;

    private Integer status;

    private Date createAt;

    private Date modifyAt;

    private Integer version;
    
    private String lanType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

	public String getLanType() {
		return lanType;
	}

	public void setLanType(String lanType) {
		this.lanType = lanType;
	}
    
}