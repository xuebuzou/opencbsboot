package cn.com.bocd.opencbsboot.entity;

import java.io.Serializable;

/**
 * RESERV_INFO
 *
 * @author
 */
public class ReservInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String reservId;
    private String ptName;
    private String certNo;
    private String status;
    private String depCode;
    private String reservPhone;
    private String acctType;
    private String verifier;
    private String note;
    private String reservTime;
    private String reservDate;

    public String getReservDate() {
        return reservDate;
    }

    public void setReservDate(String reservDate) {
        this.reservDate = reservDate;
    }

    public String getReservTime() {
        return reservTime;
    }

    public void setReservTime(String reservTime) {
        this.reservTime = reservTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReservId() {
        return reservId;
    }

    public void setReservId(String reservId) {
        this.reservId = reservId;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getReservPhone() {
        return reservPhone;
    }

    public void setReservPhone(String reservPhone) {
        this.reservPhone = reservPhone;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }
}