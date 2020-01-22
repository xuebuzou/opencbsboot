package cn.com.bocd.opencbsboot.test;

import cn.com.bocd.opencbsboot.tool.compositedata.helper.CDUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
import cn.com.bocd.opencbsboot.web.tcp.util.rpc.impl.ESBBoundTransformer;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.util.StringUtils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SpringBootTest {

    private static final Logger logger = Logger.getLogger(SpringBootTest.class);

    @Test
    public void test12000001() throws IOException {
        ESBBoundTransformer transformer = new ESBBoundTransformer();
        Socket s = new Socket("localhost", 8899);

        String path = SpringBootTest.class.getResource("12000001.xml").getPath();
        File packet = new File(path);
        transformer.compositeData2Byte(CDUtils.fromFile(packet), s.getOutputStream());
        s.getOutputStream().flush();
        InputStream resp = s.getInputStream();
        CompositeData cdExcepted = transformer.byte2CompositeData(resp);
        logger.info("返回报文:\n" + CDUtils.toXml(cdExcepted, true));
        s.close();
    }


    @Test
    public void test12000002() throws IOException {
        ESBBoundTransformer transformer = new ESBBoundTransformer();
        Socket s = new Socket("localhost", 8899);
        String path = SpringBootTest.class.getResource("12000002.xml").getPath();
        File packet = new File(path);
        transformer.compositeData2Byte(CDUtils.fromFile(packet), s.getOutputStream());
        s.getOutputStream().flush();
        InputStream resp = s.getInputStream();
        CompositeData cdExcepted = transformer.byte2CompositeData(resp);
        logger.info("返回报文:\n" + CDUtils.toXml(cdExcepted, true));
        s.close();
    }
}
