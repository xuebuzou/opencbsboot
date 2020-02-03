package cn.com.bocd.opencbsboot.components;

import cn.com.bocd.opencbsboot.dao.openacct.ReservInfoDao;
import cn.com.bocd.opencbsboot.entity.ReservInfo;
import cn.com.bocd.opencbsboot.exception.OpenCbsException;
import cn.com.bocd.opencbsboot.exception.ZgBizException;
import cn.com.bocd.opencbsboot.tool.DateUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @创建人 chengke
 * @创建时间 2020/1/20
 * @描述 预约开户服务组件包
 */
@Component("ReservBizComPkg")
public class ReservBizComPkg {
    @Autowired
    ReservInfoDao reservInfoDao;

    public void ValidateUpdData(CompositeData req, CompositeData data, CompositeData resp) throws Exception{
        if(!CDUtils.getFValue(req,"SYS_HEAD.SOURCE_TYPE").equals("ZG")){
            if(!CDUtils.getFValue(req,"STATUS").equals("")){
                throw new OpenCbsException("zg_biz_error","电子渠道不能修改开户状态");
            }
        }
    }

    public void ValidateInsertData(CompositeData req, CompositeData data, CompositeData resp) throws Exception{
        ReservInfo reservInfo = new ReservInfo();
        reservInfo.setReservPhone(CDUtils.getFValue(req,"RESERV_PHONE"));
        reservInfo.setReservDate(DateUtils.getCurrDate(DateUtils.LONG_DATE_FORMAT));
        if(reservInfoDao.selectByParam(reservInfo).size() >= 0){
            throw new ZgBizException("同一手机号每天最多预约三笔开户",req);
        }
    }

    public void updReservInfo(CompositeData req, CompositeData data, CompositeData resp) {
        ReservInfo reservInfo = new ReservInfo();
        setCommReqReservInfoParams(reservInfo,req);
        reservInfoDao.updateByPrimaryKey(reservInfo);
    }

    public void addReservInfo(CompositeData req, CompositeData data, CompositeData resp) throws Exception{
        ReservInfo reservInfo = new ReservInfo();
        setCommReqReservInfoParams(reservInfo,req);
        String reservPhone = CDUtils.getFValue(req,"RESERV_PHONE");
        reservInfo.setReservId(DateUtils.getCurrDate(DateUtils.FORMAT_FIVE)
                +reservPhone.substring(reservPhone.length()-4,reservPhone.length()));
        reservInfo.setStatus("1");
        reservInfo.setReservTime(DateUtils.getNow());
        reservInfo.setReservDate(DateUtils.getCurrDate(DateUtils.LONG_DATE_FORMAT));
        reservInfoDao.insert(reservInfo);
        resp.mPut("RESERV_ID",new StringField(reservInfo.getReservId()));
    }

    public void qryReservInfo(CompositeData req, CompositeData data, CompositeData resp) {
        int start = Integer.parseInt(CDUtils.getFValue(req,"APP_HEAD.PAGE_START"));
        int num = Integer.parseInt(CDUtils.getFValue(req,"APP_HEAD.TOTAL_NUM"));
        PageHelper.startPage(start,num);
        ReservInfo reservInfo = new ReservInfo();
        setCommReqReservInfoParams(reservInfo,req);
        List<ReservInfo> infos = reservInfoDao.selectByParam(reservInfo);
        PageInfo<ReservInfo> pageInfo = new PageInfo<>(infos);
        Array reserInfos = new Array();
        CompositeData infoStruct;
        for (ReservInfo info : infos) {
            infoStruct = new CompositeData();
            infoStruct.put("RESERV_ID",new StringField(info.getReservId()));
            infoStruct.put("PT_NAME",new StringField(info.getPtName()));
            infoStruct.put("CERT_NO",new StringField(info.getCertNo()));
            infoStruct.put("STATUS",new StringField(info.getStatus()));
            infoStruct.put("DEP_CODE",new StringField(info.getDepCode()));
            infoStruct.put("RESERV_PHONE",new StringField(info.getReservPhone()));
            infoStruct.put("ACCT_TYPE",new StringField(info.getAcctType()));
            infoStruct.put("VERIFIER",new StringField(info.getVerifier()));
            infoStruct.put("NOTE",new StringField(info.getNote()));
            reserInfos.add(infoStruct);
        }
        resp.put("RESERV_INFO_ARRAY",reserInfos);
        Long total = pageInfo.getTotal();
        resp.put("TOTAL",new IntField(total.intValue()));

    }

    public void setCommReqReservInfoParams(ReservInfo reservInfo,CompositeData req){
        reservInfo.setReservId(CDUtils.getFValue(req, "RESERV_ID"));
        reservInfo.setPtName(CDUtils.getFValue(req,"PT_NAME"));
        reservInfo.setCertNo(CDUtils.getFValue(req,"CERT_NO"));
        reservInfo.setStatus(CDUtils.getFValue(req,"STATUS"));
        reservInfo.setDepCode(CDUtils.getFValue(req,"DEP_CODE"));
        reservInfo.setReservPhone(CDUtils.getFValue(req,"RESERV_PHONE"));
        reservInfo.setAcctType(CDUtils.getFValue(req,"ACCT_TYPE"));
        reservInfo.setNote(CDUtils.getFValue(req,"NOTE"));
    }
}
