create table ZG_ROLE
(
    ID          NUMBER not null
        constraint ROLE_PK
            primary key,
    NAME        VARCHAR2(50),
    ROLE_LEVEL  NUMBER,
    DESCRIPTION VARCHAR2(50)
)
/

INSERT INTO SYMBOLS.ZG_ROLE (ID, NAME, ROLE_LEVEL, DESCRIPTION) VALUES (1, 'admin', null, '系统管理员');
create table ZG_USER
(
    ID            NUMBER not null
        constraint USER_PK
            primary key,
    CNNAME        VARCHAR2(50),
    USERNAME      VARCHAR2(50),
    PASSWORD      VARCHAR2(50),
    EMAIL         VARCHAR2(50),
    TELEPHONE     VARCHAR2(50),
    MOBILE_PHONE  VARCHAR2(50),
    WECHAT_ID     VARCHAR2(50),
    SKILL         VARCHAR2(50),
    DEPARTMENT_ID NUMBER,
    LOGIN_COUNT   NUMBER
)
/

INSERT INTO SYMBOLS.ZG_USER (ID, CNNAME, USERNAME, PASSWORD, EMAIL, TELEPHONE, MOBILE_PHONE, WECHAT_ID, SKILL, DEPARTMENT_ID, LOGIN_COUNT) VALUES (1, '程柯', 'admin', 'admin', null, null, null, null, null, null, null);
create table ZG_USER_ROLE
(
    USER_ID NUMBER not null,
    ROLE_ID NUMBER not null,
    constraint USER_ROLE_PK
        primary key (USER_ID, ROLE_ID)
)
/

INSERT INTO SYMBOLS.ZG_USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 1);