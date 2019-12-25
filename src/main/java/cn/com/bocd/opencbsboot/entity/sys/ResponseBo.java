package cn.com.bocd.opencbsboot.entity.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseBo extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private List<?> rows;    //数据

    public ResponseBo() {
        put("code", 0);
        put("msg", "success");
    }

    public static ResponseBo error() {
        return error(1, "fail");
    }

    public static ResponseBo error(String msg) {
        return error(500, msg);
    }

    public static ResponseBo error(int code, String msg) {
        ResponseBo ResponseBo = new ResponseBo();
        ResponseBo.put("code", code);
        ResponseBo.put("msg", msg);
        return ResponseBo;
    }

    public static ResponseBo ok(String msg) {
        ResponseBo ResponseBo = new ResponseBo();
        ResponseBo.put("msg", msg);
        return ResponseBo;
    }

    public static ResponseBo ok(Map<String, Object> map) {
        ResponseBo ResponseBo = new ResponseBo();
        ResponseBo.putAll(map);
        return ResponseBo;
    }

    public static ResponseBo ok() {
        return new ResponseBo();
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public ResponseBo put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
