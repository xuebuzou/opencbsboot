package cn.com.bocd.opencbsboot.entity.sys;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RetDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String retCode = "success";
    private String retMsg;
    private Object data;
    private Integer total;
    private List<?> rows;
    private Map<String, Object> result;

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setSuccessRet(String retMsg) {
        this.retCode = "success";
        this.retMsg = retMsg;
    }

    public void setFailRet(String retMsg) {
        this.retCode = "fail";
        this.retMsg = retMsg;
    }

    public void setRet(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
}
