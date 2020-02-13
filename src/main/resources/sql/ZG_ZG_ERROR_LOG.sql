create table ZG_ERROR_LOG
(
    MSG_TYPE   VARCHAR2(20),
    MSG_CODE   VARCHAR2(50),
    SRC_TYPE   VARCHAR2(20),
    USER_ID    VARCHAR2(50),
    STACK_MSG  VARCHAR2(4000),
    RET_MSG    VARCHAR2(1000),
    SEQ_NO     VARCHAR2(1000),
    LOG_TIME   VARCHAR2(50),
    REQ_BODY   VARCHAR2(4000),
    SYS_HEAD   VARCHAR2(4000),
    APP_HEAD   VARCHAR2(4000),
    ERROR_TYPE VARCHAR2(50)
)
/

INSERT INTO ZG.ZG_ERROR_LOG (MSG_TYPE, MSG_CODE, SRC_TYPE, USER_ID, STACK_MSG, RET_MSG, SEQ_NO, LOG_TIME, REQ_BODY, SYS_HEAD, APP_HEAD, ERROR_TYPE) VALUES ('ADD', 'RESERV', 'CB', '903303', 'cn.com.bocd.opencbsboot.exception.ZgBizException
	at cn.com.bocd.opencbsboot.components.ReservBizComPkg.ValidateInsertData(ReservBizComPkg.java:40)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher.doDispatch(OpenCbsDispatcher.java:209)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$FastClassBySpringCGLIB$$ac3b6628.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$EnhancerBySpringCGLIB$$d54f44d3.doDispatch(<generated>)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$FastClassBySpringCGLIB$$ac3b6628.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$EnhancerBySpringCGLIB$$2bb9dfe1.doDispatch(<generated>)
	at cn.com.bocd.opencbsboot.tool.compositedata.handler.impl.CDHandlerImpl.doHandle(CDHandlerImpl.java:36)
	at cn.com.bocd.opencbsboot.web.tcp.util.NioThread.run(NioThread.java:43)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
', '同一手机号每天最多预约三笔开户', 'T1712081517866', '2020-02-04 10:08:40', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="RESERV_PHONE">
            <field length="11" scale="0" type="string">15008426617</field>
        </data>
        <data name="DEP_CODE">
            <field length="4" scale="0" type="string">0101</field>
        </data>
        <data name="CERT_NO">
            <field length="8" scale="0" type="string">CERT1234</field>
        </data>
        <data name="PT_NAME">
            <field length="17" scale="0" type="string">XX猪饲料公司</field>
        </data>
        <data name="ACCT_TYPE">
            <field length="2" scale="0" type="string">JB</field>
        </data>
    </body>
</service>
', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="SERVER_ID">
            <field length="9" scale="0" type="string">127.0.0.1</field>
        </data>
        <data name="DEST_BRANCH_NO">
            <field length="6" scale="0" type="string">000001</field>
        </data>
        <data name="SERVICE_CODE">
            <field length="6" scale="0" type="string">SVR_ZG</field>
        </data>
        <data name="MESSAGE_TYPE">
            <field length="3" scale="0" type="string">ADD</field>
        </data>
        <data name="USER_ID">
            <field length="6" scale="0" type="string">903303</field>
        </data>
        <data name="AUTH_FLAG">
            <field length="1" scale="0" type="string">N</field>
        </data>
        <data name="PROGRAM_ID">
            <field length="5" scale="0" type="string">FM701</field>
        </data>
        <data name="WS_ID">
            <field length="3" scale="0" type="string">001</field>
        </data>
        <data name="SEQ_NO">
            <field length="14" scale="0" type="string">T1712081517866</field>
        </data>
        <data name="SOURCE_TYPE">
            <field length="2" scale="0" type="string">CB</field>
        </data>
        <data name="TRAN_MODE">
            <field length="6" scale="0" type="string">ONLINE</field>
        </data>
        <data name="USER_LANG">
            <field length="7" scale="0" type="string">CHINESE</field>
        </data>
        <data name="MESSAGE_CODE">
            <field length="6" scale="0" type="string">RESERV</field>
        </data>
        <data name="MODULE_ID">
            <field length="2" scale="0" type="string">FM</field>
        </data>
        <data name="SOURCE_BRANCH_NO">
            <field length="6" scale="0" type="string">000001</field>
        </data>
        <data name="TRAN_DATE">
            <field length="8" scale="0" type="string">20190809</field>
        </data>
        <data name="BRANCH_ID">
            <field length="4" scale="0" type="string">0101</field>
        </data>
    </body>
</service>
', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="PAGE_END">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="PAGE_START">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="TOTAL_NUM">
            <field length="2" scale="0" type="string">-1</field>
        </data>
        <data name="CURRENT_NUM">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="PGUP_OR_PGDN">
            <field length="1" scale="0" type="string">0</field>
        </data>
    </body>
</service>
', null);
INSERT INTO ZG.ZG_ERROR_LOG (MSG_TYPE, MSG_CODE, SRC_TYPE, USER_ID, STACK_MSG, RET_MSG, SEQ_NO, LOG_TIME, REQ_BODY, SYS_HEAD, APP_HEAD, ERROR_TYPE) VALUES ('ADD', 'RESERV', 'CB', '903303', 'cn.com.bocd.opencbsboot.exception.ZgBizException
	at cn.com.bocd.opencbsboot.components.ReservBizComPkg.ValidateInsertData(ReservBizComPkg.java:40)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher.doDispatch(OpenCbsDispatcher.java:210)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$FastClassBySpringCGLIB$$ac3b6628.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$EnhancerBySpringCGLIB$$20f766cf.doDispatch(<generated>)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$FastClassBySpringCGLIB$$ac3b6628.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$EnhancerBySpringCGLIB$$776201dd.doDispatch(<generated>)
	at cn.com.bocd.opencbsboot.tool.compositedata.handler.impl.CDHandlerImpl.doHandle(CDHandlerImpl.java:36)
	at cn.com.bocd.opencbsboot.web.tcp.util.NioThread.run(NioThread.java:43)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
', '同一手机号每天最多预约三笔开户', 'T1712081517866', '2020-02-04 11:34:53', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="RESERV_PHONE">
            <field length="11" scale="0" type="string">15008426617</field>
        </data>
        <data name="DEP_CODE">
            <field length="4" scale="0" type="string">0101</field>
        </data>
        <data name="CERT_NO">
            <field length="8" scale="0" type="string">CERT1234</field>
        </data>
        <data name="PT_NAME">
            <field length="17" scale="0" type="string">XX猪饲料公司</field>
        </data>
        <data name="ACCT_TYPE">
            <field length="2" scale="0" type="string">JB</field>
        </data>
    </body>
</service>
', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="SERVER_ID">
            <field length="9" scale="0" type="string">127.0.0.1</field>
        </data>
        <data name="DEST_BRANCH_NO">
            <field length="6" scale="0" type="string">000001</field>
        </data>
        <data name="SERVICE_CODE">
            <field length="6" scale="0" type="string">SVR_ZG</field>
        </data>
        <data name="MESSAGE_TYPE">
            <field length="3" scale="0" type="string">ADD</field>
        </data>
        <data name="USER_ID">
            <field length="6" scale="0" type="string">903303</field>
        </data>
        <data name="AUTH_FLAG">
            <field length="1" scale="0" type="string">N</field>
        </data>
        <data name="PROGRAM_ID">
            <field length="5" scale="0" type="string">FM701</field>
        </data>
        <data name="WS_ID">
            <field length="3" scale="0" type="string">001</field>
        </data>
        <data name="SEQ_NO">
            <field length="14" scale="0" type="string">T1712081517866</field>
        </data>
        <data name="SOURCE_TYPE">
            <field length="2" scale="0" type="string">CB</field>
        </data>
        <data name="TRAN_MODE">
            <field length="6" scale="0" type="string">ONLINE</field>
        </data>
        <data name="USER_LANG">
            <field length="7" scale="0" type="string">CHINESE</field>
        </data>
        <data name="MESSAGE_CODE">
            <field length="6" scale="0" type="string">RESERV</field>
        </data>
        <data name="MODULE_ID">
            <field length="2" scale="0" type="string">FM</field>
        </data>
        <data name="SOURCE_BRANCH_NO">
            <field length="6" scale="0" type="string">000001</field>
        </data>
        <data name="TRAN_DATE">
            <field length="8" scale="0" type="string">20190809</field>
        </data>
        <data name="BRANCH_ID">
            <field length="4" scale="0" type="string">0101</field>
        </data>
    </body>
</service>
', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="PAGE_END">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="PAGE_START">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="TOTAL_NUM">
            <field length="2" scale="0" type="string">-1</field>
        </data>
        <data name="CURRENT_NUM">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="PGUP_OR_PGDN">
            <field length="1" scale="0" type="string">0</field>
        </data>
    </body>
</service>
', null);
INSERT INTO ZG.ZG_ERROR_LOG (MSG_TYPE, MSG_CODE, SRC_TYPE, USER_ID, STACK_MSG, RET_MSG, SEQ_NO, LOG_TIME, REQ_BODY, SYS_HEAD, APP_HEAD, ERROR_TYPE) VALUES ('ADD', 'RESERV', 'CB', '903303', 'cn.com.bocd.opencbsboot.exception.ZgBizException
	at cn.com.bocd.opencbsboot.components.ReservBizComPkg.ValidateInsertData(ReservBizComPkg.java:40)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher.doDispatch(OpenCbsDispatcher.java:210)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$FastClassBySpringCGLIB$$ac3b6628.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$EnhancerBySpringCGLIB$$7f23761d.doDispatch(<generated>)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$FastClassBySpringCGLIB$$ac3b6628.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)
	at cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher$$EnhancerBySpringCGLIB$$d58e112b.doDispatch(<generated>)
	at cn.com.bocd.opencbsboot.tool.compositedata.handler.impl.CDHandlerImpl.doHandle(CDHandlerImpl.java:36)
	at cn.com.bocd.opencbsboot.web.tcp.util.NioThread.run(NioThread.java:43)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
', '同一手机号每天最多预约三笔开户', 'T1712081517866', '2020-02-04 11:57:12', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="RESERV_PHONE">
            <field length="11" scale="0" type="string">15008426617</field>
        </data>
        <data name="DEP_CODE">
            <field length="4" scale="0" type="string">0101</field>
        </data>
        <data name="CERT_NO">
            <field length="8" scale="0" type="string">CERT1234</field>
        </data>
        <data name="PT_NAME">
            <field length="17" scale="0" type="string">XX猪饲料公司</field>
        </data>
        <data name="ACCT_TYPE">
            <field length="2" scale="0" type="string">JB</field>
        </data>
    </body>
</service>
', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="SERVER_ID">
            <field length="9" scale="0" type="string">127.0.0.1</field>
        </data>
        <data name="DEST_BRANCH_NO">
            <field length="6" scale="0" type="string">000001</field>
        </data>
        <data name="SERVICE_CODE">
            <field length="6" scale="0" type="string">SVR_ZG</field>
        </data>
        <data name="MESSAGE_TYPE">
            <field length="3" scale="0" type="string">ADD</field>
        </data>
        <data name="USER_ID">
            <field length="6" scale="0" type="string">903303</field>
        </data>
        <data name="AUTH_FLAG">
            <field length="1" scale="0" type="string">N</field>
        </data>
        <data name="PROGRAM_ID">
            <field length="5" scale="0" type="string">FM701</field>
        </data>
        <data name="WS_ID">
            <field length="3" scale="0" type="string">001</field>
        </data>
        <data name="SEQ_NO">
            <field length="14" scale="0" type="string">T1712081517866</field>
        </data>
        <data name="SOURCE_TYPE">
            <field length="2" scale="0" type="string">CB</field>
        </data>
        <data name="TRAN_MODE">
            <field length="6" scale="0" type="string">ONLINE</field>
        </data>
        <data name="USER_LANG">
            <field length="7" scale="0" type="string">CHINESE</field>
        </data>
        <data name="MESSAGE_CODE">
            <field length="6" scale="0" type="string">RESERV</field>
        </data>
        <data name="MODULE_ID">
            <field length="2" scale="0" type="string">FM</field>
        </data>
        <data name="SOURCE_BRANCH_NO">
            <field length="6" scale="0" type="string">000001</field>
        </data>
        <data name="TRAN_DATE">
            <field length="8" scale="0" type="string">20190809</field>
        </data>
        <data name="BRANCH_ID">
            <field length="4" scale="0" type="string">0101</field>
        </data>
    </body>
</service>
', '<?xml version="1.0" encoding="UTF-8"?>

<service>
    <sys-header>
        <data name="SYS_HEAD">
            <struct/>
        </data>
    </sys-header>
    <app-header>
        <data name="APP_HEAD">
            <struct/>
        </data>
    </app-header>
    <local-header>
        <data name="LOCAL_HEAD">
            <struct/>
        </data>
    </local-header>
    <body>
        <data name="PAGE_END">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="PAGE_START">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="TOTAL_NUM">
            <field length="2" scale="0" type="string">-1</field>
        </data>
        <data name="CURRENT_NUM">
            <field length="1" scale="0" type="string">0</field>
        </data>
        <data name="PGUP_OR_PGDN">
            <field length="1" scale="0" type="string">0</field>
        </data>
    </body>
</service>
', 'biz');