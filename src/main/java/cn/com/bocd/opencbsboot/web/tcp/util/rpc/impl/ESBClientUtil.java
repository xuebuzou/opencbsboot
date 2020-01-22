package cn.com.bocd.opencbsboot.web.tcp.util.rpc.impl;

import cn.com.bocd.opencbsboot.entity.sys.UserVO;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.Array;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CDUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.StringField;
import cn.com.bocd.opencbsboot.web.http.PageController;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.InputStream;
import java.net.Socket;
import java.util.Date;

public class ESBClientUtil {
    private static final Logger logger = Logger.getLogger(ESBClientUtil.class);

    public static CompositeData getReqTemplate(String seq,String msgtype,String msgcode,String svrcode){
        CompositeData reqTemplate = new CompositeData();
        //syshead
        CompositeData syshead = new CompositeData();
        syshead.put("MODULE_ID",new StringField("ED"));
        syshead.put("TRAN_TIMESTAMP",new StringField("161103"));
        syshead.put("SOURCE_BRANCH_NO",new StringField("000005"));
        syshead.put("BRANCH_ID",new StringField("9999"));
        syshead.put("USER_LANG",new StringField("CHINESE"));
        syshead.put("SEQ_NO",new StringField(seq));
        syshead.put("SOURCE_TYPE",new StringField("ZG"));
        syshead.put("TRAN_CODE",new StringField(""));
        syshead.put("SERVER_ID",new StringField("127.0.0.1"));
        syshead.put("MESSAGE_CODE",new StringField(msgcode));
        syshead.put("SERVICE_CODE",new StringField(svrcode));
        syshead.put("AUTH_PASSWORD",new StringField("MM"));
        syshead.put("APPR_FLAG",new StringField("A"));
        syshead.put("USER_ID",new StringField("admin"));
        syshead.put("PROGRAM_ID",new StringField(""));
        syshead.put("AUTH_FLAG",new StringField("M"));
        syshead.put("TRAN_TYPE",new StringField(""));
        syshead.put("APPR_USER_ID",new StringField("WEIGZ"));
        syshead.put("DEST_BRANCH_NO",new StringField("751080000"));
        syshead.put("TRAN_DATE",new StringField("20200101"));
        syshead.put("AUTH_USER_ID",new StringField("RONGGP"));
        syshead.put("MESSAGE_TYPE",new StringField(msgtype));
        syshead.put("WS_ID",new StringField("null"));
        syshead.put("FILE_PATH",new StringField(""));
        syshead.put("REVERSAL_TRAN_TYPE",new StringField(""));
        reqTemplate.put("SYS_HEAD", syshead);
        //apphead
        CompositeData apphead = new CompositeData();
        apphead.put("PAGE_END",new StringField(""));
        apphead.put("CURRENT_NUM",new StringField(""));
        apphead.put("PGUP_OR_PGDN",new StringField("1"));
        apphead.put("PAGE_START",new StringField(""));
        apphead.put("TOTAL_NUM",new StringField(""));
        reqTemplate.put("APP_HEAD", apphead);
        //localhead
        CompositeData localhead = new CompositeData();
        localhead.put("RET",new Array());
        reqTemplate.put("LOCAL_HEAD",localhead);
        return reqTemplate;
    }


    @Test
    public void test(){
        CompositeData reqTemplate = ESBClientUtil.
                getReqTemplate("chengke","EDS","EDS101","SVR_EDS");
        reqTemplate.put("ClientNo",new StringField(""));//CUS061735
        reqTemplate.put("CUST_FULL_NAME",new StringField("随便输入姓名"));//2152147
        reqTemplate.put("ValidTime",new StringField("1"));//单位：天。查看外部数据缓存X天内的数据，没有则查询外部系统
        Array arr1 = new Array();
        CompositeData struct1 = new CompositeData();
        struct1.put("CERT_TYPE",new StringField("202"));//203
        struct1.put("CERT_NO",new StringField("91510100633142770A"));
        arr1.add(struct1);
        reqTemplate.put("CERT_ARRAY_INFO",arr1);

        Array arr2 = new Array();
        CompositeData struct2 = new CompositeData();

        String reqxml = CDUtils.toXml(reqTemplate,true);
        logger.info("请求报文:\n" +reqxml);
        Socket s;
        try{
            ESBBoundTransformer transformer = new ESBBoundTransformer();
            s = new Socket("15.0.20.17", 8092);
            transformer.compositeData2Byte(reqTemplate, s.getOutputStream());
            s.getOutputStream().flush();
            InputStream resp = s.getInputStream();
            CompositeData cdExcepted = transformer.byte2CompositeData(resp);
            logger.info("返回报文:\n" + CDUtils.toXml(cdExcepted, true));
            logger.info("返回报文:\n" + CDUtils.toXml(cdExcepted, false));
            s.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }

    }
    @Test
    public void testPOJOtoCD(){
        CompositeData cd = new CompositeData();
        UserVO userVO = new UserVO();
        userVO.setCnname("chengke");
        userVO.setUsername("admin");
        userVO.setDepartmentId("1111");
        CDUtils.extractPOJOToFields(userVO,cd);
        logger.info(111);
    }

    @Test
    public void testStrsub(){
        String str = "123456";
        String sub = str.substring(str.length()-4,str.length());
        logger.info(sub);
    }

}
