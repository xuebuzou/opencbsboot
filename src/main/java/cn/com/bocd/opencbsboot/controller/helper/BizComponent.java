package cn.com.bocd.opencbsboot.controller.helper;

/**
 * @创建人 chengke
 * @创建时间 2019/12/3
 * @描述
 */
public class BizComponent {
    private String service;
    private String func;

    public BizComponent(String serviceClassName, String func) {
        this.service = serviceClassName;
        this.func = func;
    }

    public String getService() {
        return service;
    }

    public void setService(String serviceClassName) {
        this.service = serviceClassName;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }
}
