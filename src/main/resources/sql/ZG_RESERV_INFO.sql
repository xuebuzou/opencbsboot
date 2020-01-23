CREATE TABLE ZG.RESERV_INFO
(
    RESERV_ID VARCHAR2(20) PRIMARY KEY NOT NULL,
    PT_NAME VARCHAR2(150),
    CERT_NO VARCHAR2(50),
    STATUS VARCHAR2(10),
    DEP_CODE VARCHAR2(10) DEFAULT NULL,
    RESERV_PHONE VARCHAR2(20),
    ACCT_TYPE VARCHAR2(10),
    VERIFIER VARCHAR2(10),
    NOTE VARCHAR2(2000),
    RESERV_TIME VARCHAR2(50) DEFAULT NULL,
    RESERV_DATE VARCHAR2(50)
);
INSERT INTO ZG.RESERV_INFO (RESERV_ID, PT_NAME, CERT_NO, STATUS, DEP_CODE, RESERV_PHONE, ACCT_TYPE, VERIFIER, NOTE, RESERV_TIME, RESERV_DATE) VALUES ('20200119010199', '某某科技公司', '500103198905171816', '2', '0101', '15008426617', 'JB', null, '开户成功', null, null);
INSERT INTO ZG.RESERV_INFO (RESERV_ID, PT_NAME, CERT_NO, STATUS, DEP_CODE, RESERV_PHONE, ACCT_TYPE, VERIFIER, NOTE, RESERV_TIME, RESERV_DATE) VALUES ('20012117026617', 'XX猪饲料公司', 'CERT1234', '5', '0101', '15008426617', 'JB', null, 'yayaaya', '2020-01-21 17:02:08', '2020-01-21');
INSERT INTO ZG.RESERV_INFO (RESERV_ID, PT_NAME, CERT_NO, STATUS, DEP_CODE, RESERV_PHONE, ACCT_TYPE, VERIFIER, NOTE, RESERV_TIME, RESERV_DATE) VALUES ('20012117036617', 'XX猪饲料公司', 'CERT1234', '8', '0101', '15008426617', 'JB', null, null, '2020-01-21 17:03:35', '2020-01-21');
INSERT INTO ZG.RESERV_INFO (RESERV_ID, PT_NAME, CERT_NO, STATUS, DEP_CODE, RESERV_PHONE, ACCT_TYPE, VERIFIER, NOTE, RESERV_TIME, RESERV_DATE) VALUES ('20012117006617', 'XX猪饲料公司', 'CERT1234', '9', '0101', '15008426617', 'JB', null, null, '2020-01-21 17:00:58', '2020-01-21');
INSERT INTO ZG.RESERV_INFO (RESERV_ID, PT_NAME, CERT_NO, STATUS, DEP_CODE, RESERV_PHONE, ACCT_TYPE, VERIFIER, NOTE, RESERV_TIME, RESERV_DATE) VALUES ('20012117016617', 'XX猪饲料公司', 'CERT1234', '9', '0101', '15008426617', 'JB', null, null, '2020-01-21 17:01:47', '2020-01-21');