package cn.com.bocd.opencbsboot.service.sys;

import cn.com.bocd.opencbsboot.dao.openacct.ZgErrorLogDao;
import cn.com.bocd.opencbsboot.entity.ZgErrorLog;
import cn.com.bocd.opencbsboot.exception.ZgBizException;
import cn.com.bocd.opencbsboot.exception.ZgException;
import cn.com.bocd.opencbsboot.exception.ZgPtException;
import cn.com.bocd.opencbsboot.tool.DateUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CDUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.StringField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

@Service
public class LogService {
    @Autowired
    ZgErrorLogDao zgErrorLogDao;

    public ZgErrorLogDao getZgErrorLogDao() {
        return zgErrorLogDao;
    }

    public void setZgErrorLogDao(ZgErrorLogDao zgErrorLogDao) {
        this.zgErrorLogDao = zgErrorLogDao;
    }

    public void writeErrorLog(ZgException ex){
        CompositeData cd = ex.getCompositeData().deepCopy();
        CompositeData sys = new CompositeData();
        CompositeData app = new CompositeData();

        String retMsg = ex.getRetMsg();
        String msgType = ((StringField) cd.mGet("SYS_HEAD.MESSAGE_TYPE")).getValue();
        String msgCode = ((StringField) cd.mGet("SYS_HEAD.MESSAGE_CODE")).getValue();
        String srcType = ((StringField) cd.mGet("SYS_HEAD.SOURCE_TYPE")).getValue();
        String userId = ((StringField) cd.mGet("SYS_HEAD.USER_ID")).getValue();
        String logTime = DateUtils.getCurrDate(DateUtils.FORMAT_ONE);
        String seqNo = ((StringField) cd.mGet("SYS_HEAD.SEQ_NO")).getValue();
        ZgErrorLog zgErrorLog = new ZgErrorLog();

        zgErrorLog.setMsgType(msgType);
        zgErrorLog.setMsgCode(msgCode);
        zgErrorLog.setRetMsg(retMsg);
        zgErrorLog.setSrcType(srcType);
        zgErrorLog.setUserId(userId);
        zgErrorLog.setLogTime(logTime);
        zgErrorLog.setSeqNo(seqNo);

        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackMsg = sw.toString();
        zgErrorLog.setStackMsg(stackMsg);

        sys = (CompositeData)cd.mGet("SYS_HEAD");
        app = (CompositeData)cd.mGet("APP_HEAD");

        cd.remove("SYS_HEAD");
        cd.remove("LOCAL_HEAD");
        cd.remove("APP_HEAD");

        zgErrorLog.setReqBody(CDUtils.toXml(cd,true));
        zgErrorLog.setSysHead(CDUtils.toXml(sys,true));
        zgErrorLog.setAppHead(CDUtils.toXml(app,true));

        if(ex instanceof ZgPtException){
            zgErrorLog.setErrorType("pt");
        }else if(ex instanceof ZgBizException){
            zgErrorLog.setErrorType("biz");
        }

        zgErrorLogDao.insert(zgErrorLog);
    }
}
