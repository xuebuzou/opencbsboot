create table ZG_DEP
(
    DEP_CODE VARCHAR2(20) default NULL not null
        primary key,
    DEP_DESC VARCHAR2(50)              not null,
    DEP_ID   NUMBER       default NULL not null
)
/

INSERT INTO ZG.ZG_DEP (DEP_CODE, DEP_DESC, DEP_ID) VALUES ('0101', '0101-营业部', 101);
INSERT INTO ZG.ZG_DEP (DEP_CODE, DEP_DESC, DEP_ID) VALUES ('9999', '9999-清算中心', 9999);