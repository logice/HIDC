package hbc315.HIDC.model;

import java.math.BigDecimal;
import java.util.Date;

public class CallDetail {
	/** 主键自增id */
    private Integer id;
    /** 数据写入时间 */
    private Date createdTime;
    /** 主机号码 */
    private String mobile;
    /** 通话地点 */
    private String callAddress;
    /** 呼叫类型，1：主叫，2：被叫 */
    private Byte callMethod;
    /** 通话类型，1：本地通话，2：国内长途，3：其他 */
    private Byte callType;
    /** 对方号码 */
    private String relatedMobile;
    /** 通话日期 */
    private Date callTime;
    /** 通话时长(秒) */
    private Integer callDuration;
    /** 通话费用 */
    private BigDecimal callFee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCallAddress() {
        return callAddress;
    }

    public void setCallAddress(String callAddress) {
        this.callAddress = callAddress == null ? null : callAddress.trim();
    }

    public Byte getCallMethod() {
        return callMethod;
    }

    public void setCallMethod(Byte callMethod) {
        this.callMethod = callMethod;
    }

    public Byte getCallType() {
        return callType;
    }

    public void setCallType(Byte callType) {
        this.callType = callType;
    }

    public String getRelatedMobile() {
        return relatedMobile;
    }

    public void setRelatedMobile(String relatedMobile) {
        this.relatedMobile = relatedMobile == null ? null : relatedMobile.trim();
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public BigDecimal getCallFee() {
        return callFee;
    }

    public void setCallFee(BigDecimal callFee) {
        this.callFee = callFee;
    }
}