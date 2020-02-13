create table ACCT_TYPE_DEF
(
    ACCT_TYPE      VARCHAR2(10) not null
        primary key,
    ACCT_TYPE_DESC VARCHAR2(50)
)
/

INSERT INTO ZG.ACCT_TYPE_DEF (ACCT_TYPE, ACCT_TYPE_DESC) VALUES ('JB', '基本存款账户');
INSERT INTO ZG.ACCT_TYPE_DEF (ACCT_TYPE, ACCT_TYPE_DESC) VALUES ('YB', '一般存款账户');
INSERT INTO ZG.ACCT_TYPE_DEF (ACCT_TYPE, ACCT_TYPE_DESC) VALUES ('ZY', '专用存款账户');
INSERT INTO ZG.ACCT_TYPE_DEF (ACCT_TYPE, ACCT_TYPE_DESC) VALUES ('LS', '临时存款账户');