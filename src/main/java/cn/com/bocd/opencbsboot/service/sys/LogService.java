package cn.com.bocd.opencbsboot.service.sys;

import cn.com.bocd.opencbsboot.dao.openacct.ZgErrorLogDao;
import cn.com.bocd.opencbsboot.entity.ZgErrorLog;
import cn.com.bocd.opencbsboot.exception.ZgBizException;
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

    public void writeZgBizExceptionLog(ZgBizException ex){
        CompositeData cd = ex.getCompositeData().deepCopy();
//        CompositeData body = new CompositeData();

        String retMsg = ex.retMsg;
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

        cd.remove("SYS_HEAD");
        cd.remove("LOCAL_HEAD");
        cd.remove("APP_HEAD");

        String reqXml = CDUtils.toXml(cd);
        String reqXmlFormatted = CDUtils.toXml(cd,true);
        zgErrorLog.setReqXml(reqXmlFormatted);

        System.out.println("---------------------------------------------------------------");
        System.out.println(reqXml.length());
        System.out.println(reqXmlFormatted.length());
        System.out.println("---------------------------------------------------------------");
        zgErrorLogDao.insert(zgErrorLog);
    }
}
