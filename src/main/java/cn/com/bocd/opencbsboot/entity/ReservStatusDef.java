package cn.com.bocd.opencbsboot.entity;

import java.io.Serializable;

/**
 * RESERV_STATUS_DEF
 * @author 
 */
public class ReservStatusDef implements Serializable {
    private String status;

    private String statusDesc;

    private static final long serialVersionUID = 1L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}