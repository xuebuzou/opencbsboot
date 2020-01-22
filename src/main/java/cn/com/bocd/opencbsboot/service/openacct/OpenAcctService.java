package cn.com.bocd.opencbsboot.service.openacct;

import cn.com.bocd.opencbsboot.dao.openacct.ReservInfoDao;
import cn.com.bocd.opencbsboot.entity.ReservInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 chengke
 * @创建时间 2020/1/19
 * @描述
 */
@Component
public class OpenAcctService {
    @Autowired
    ReservInfoDao dao;

    public List<ReservInfo> selectByParam(ReservInfo params){
        return dao.selectByParam(params);
    }

//    public List<ReservInfo> selectByParam(ReservInfo params){
//        return dao.selectByParam(params);
//    }

    @Test
    public void test(){
        ReservInfo reservInfo = new ReservInfo();
        reservInfo.setReservId("20200119010199");
        List<ReservInfo> infos = new ArrayList<>();
        infos = dao.selectByParam(reservInfo);
        System.out.println(111);
    }
}
