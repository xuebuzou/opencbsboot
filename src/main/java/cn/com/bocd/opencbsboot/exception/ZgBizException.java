package cn.com.bocd.opencbsboot.exception;

import cn.com.bocd.opencbsboot.dao.openacct.ZgErrorLogDao;
import cn.com.bocd.opencbsboot.entity.ZgErrorLog;
import cn.com.bocd.opencbsboot.service.sys.LogService;
import cn.com.bocd.opencbsboot.tool.BeanUtils;
import cn.com.bocd.opencbsboot.tool.DateUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CDUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.StringField;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import sun.rmi.runtime.Log;

public class ZgBizException extends Exception implements ZgException{
    public String retCode;
    public String retMsg;
    private CompositeData compositeData;
    public ZgBizException(String retMsg, CompositeData compositeData) {
        this.retCode = "账管业务逻辑错误";
        this.retMsg = retMsg;
        this.compositeData = compositeData;
        LogService logService = BeanUtils.getBean(LogService.class);
        logService.writeErrorLog(this);
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

    public CompositeData getCompositeData() {
        return compositeData;
    }

    public void setCompositeData(CompositeData compositeData) {
        this.compositeData = compositeData;
    }

    public void printStack(){
        System.out.println(this.retMsg);
        this.printStackTrace();
//        this.printStackTrace();
    }


}
