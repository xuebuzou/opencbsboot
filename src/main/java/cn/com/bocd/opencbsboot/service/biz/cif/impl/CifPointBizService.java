package cn.com.bocd.opencbsboot.service.biz.cif.impl;

import cn.com.bocd.opencbsboot.cddata.helper.CDUtils;
import cn.com.bocd.opencbsboot.cddata.helper.CompositeData;
import cn.com.bocd.opencbsboot.cddata.helper.IntField;
import cn.com.bocd.opencbsboot.dao.cif.*;
import cn.com.bocd.opencbsboot.service.biz.cif.CifBizService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人 chengke
 * @创建时间 2019/12/3
 * @描述 客户积分服务类
 */
@Service("CifPointBizService")
public class CifPointBizService implements CifBizService {
    private static final Logger logger = Logger.getLogger(CifPointBizService.class);
    @Autowired
    private FmClientPointDAO fmClientPointDAO;

    public void queryCifPointById(CompositeData req, CompositeData data, CompositeData resp) throws Exception{

        String clientNo = CDUtils.getFValue(req, "CLIENT_NO");
        String pointType = CDUtils.getFValue(req, "POINT_TYPE");
        logger.debug("clientNo"+clientNo);
        int points;
        if (pointType.equalsIgnoreCase("001") || pointType.equalsIgnoreCase("")){
            points = fmClientPointDAO.selectClientDepositPointByClientNo(clientNo);
            resp.put("POINTS",new IntField(points));
        } else if (pointType.equalsIgnoreCase("002")){
            points = fmClientPointDAO.selectClientTotalPointByClientNo(clientNo);
            resp.put("POINTS",new IntField(points));
        }
    }

    public void addClfPoint(CompositeData req, CompositeData data, CompositeData resp) throws Exception{
    }

}
