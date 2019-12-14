package cn.com.bocd.opencbsboot.dispatcher;

import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;

import java.io.IOException;

/**
 * @创建人 chengke
 * @创建时间 2019/11/29
 * @描述 调度器接口
 */
public interface Dispatcher {
    void init() throws IOException;
    CompositeData doDispatch(CompositeData req);
}