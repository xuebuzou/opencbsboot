package cn.com.bocd.opencbsboot.entity;

import java.sql.Date;

public class FmClientPoint {

    private String clientNo;
    private Number totalPoint;
    private String lastUserId;
    private Date lastChangeDate;
    private String status;
    private Number depositPoint;
    private Date signDate;


    public String getClientNo() {
        return clientNo;
    }

    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }


    public Number getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Number totalPoint) {
        this.totalPoint = totalPoint;
    }


    public String getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(String lastUserId) {
        this.lastUserId = lastUserId;
    }


    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Number getDepositPoint() {
        return depositPoint;
    }

    public void setDepositPoint(Number depositPoint) {
        this.depositPoint = depositPoint;
    }


    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

}
