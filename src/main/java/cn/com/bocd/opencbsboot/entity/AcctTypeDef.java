package cn.com.bocd.opencbsboot.entity;

import java.io.Serializable;

/**
 * ACCT_TYPE_DEF
 * @author 
 */
public class AcctTypeDef implements Serializable {
    private String acctType;

    private String acctTypeDesc;

    private static final long serialVersionUID = 1L;

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAcctTypeDesc() {
        return acctTypeDesc;
    }

    public void setAcctTypeDesc(String acctTypeDesc) {
        this.acctTypeDesc = acctTypeDesc;
    }
}