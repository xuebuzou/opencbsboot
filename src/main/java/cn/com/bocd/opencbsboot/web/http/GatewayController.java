package cn.com.bocd.opencbsboot.web.http;

import cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher;
import cn.com.bocd.opencbsboot.entity.sys.RetDTO;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class GatewayController {
    @Autowired
    OpenCbsDispatcher dispatcher;

    @RequestMapping("zg/gateway")
    @ResponseBody
    public RetDTO gateway(@RequestParam Map<String,String> map){
        RetDTO ret = new RetDTO();
        CompositeData cd = new CompositeData();
        CompositeData syshead = new CompositeData();
        syshead.put("SOURCE_TYPE",new StringField("ZG"));
        for(String key: map.keySet()){
            if(key.equals("MESSAGE_TYPE")){
                syshead.put("MESSAGE_TYPE",new StringField(map.get(key)));
            }
            if(key.equals("MESSAGE_CODE")){
                syshead.put("MESSAGE_CODE",new StringField(map.get(key)));
            }
            cd.put(key,new StringField(map.get(key)));
        }
        cd.put("SYS_HEAD",syshead);
        CompositeData2Json(dispatcher.doDispatch(cd),ret);
        return ret;
    }

    //目前只能处理一层嵌套数据
    public void CompositeData2Json(CompositeData cd,RetDTO r){
        Array arr = (Array)cd.mGet("SYS_HEAD.RET");
        Object[] objs = arr.toArray();
        CompositeData ret = (CompositeData)objs[0];
        r.setRetCode(CDUtils.getFValue(ret,"RET_CODE"));
        r.setRetMsg(CDUtils.getFValue(ret,"RET_MSG"));

        ArrayList rows = new ArrayList();
        Map<String, AtomData> data = cd.getData();
        for(String key:data.keySet()) {
            if (key.equals("SYS_HEAD") || key.equals("APP_HEAD") || key.equals("LOCAL_HEAD")) {
                continue;
            }
            AtomData atomData = data.get(key);
            if (atomData instanceof StringField) {
                //nothing to do
            } else if (atomData instanceof Array) {
                Iterator<AtomData> iterator = ((Array) atomData).iterator();
                while (iterator.hasNext()) {
                    HashMap itemmap = new HashMap();
                    CompositeData item = (CompositeData) iterator.next();
                    Map<String, AtomData> itemdata = item.getData();
                    for(String itemkey:itemdata.keySet()) {
                        StringField itemvalue = (StringField) itemdata.get(itemkey);
                        if(itemvalue==null){
                            itemmap.put(itemkey,"");
                        }else{
                            itemmap.put(itemkey,itemvalue.getValue());
                        }
                    }
                    rows.add(itemmap);
                }

            }
        }
        r.setRows(rows);
    }

}
