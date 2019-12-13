package cn.com.bocd.opencbsboot.controller.helper.factory;

import cn.com.bocd.opencbsboot.controller.helper.Flow;

/**
 * @创建人 chengke
 * @创建时间 2019/12/3
 * @描述
 */
public class FlowFactory {
    public static Flow getFlowInstance(){
        return new Flow();
    }
}
