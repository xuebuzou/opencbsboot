package cn.com.bocd.opencbsboot.web.util.session;

public enum SessionDef {
    REQUEST_REMOTE_ADDR, // 远端地址
    REQUEST_LOCAL_ADDR, // 本地地址
    RAW_REQUEST, // 原始的请求报文，byte[]

    // fminit
    RUN_DATE, LAST_RUN_DATE, SYSTEM_PHASE, BASE_CCY, LOCAL_CCY, DEFAULT_BRANCH,
    UTIL_PATH,
    MULTI_CORPORATION_FLAG, MULTI_CORP_QUERY_ALLOW // for multi company check
}
