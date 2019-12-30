create table ZG_ROLE
(
    ID          NUMBER not null
        primary key,
    NAME        VARCHAR2(50),
    ROLE_LEVEL  NUMBER,
    DESCRIPTION VARCHAR2(50)
)
/

INSERT INTO ZG.ZG_ROLE (ID, NAME, ROLE_LEVEL, DESCRIPTION) VALUES (1, 'admin', null, '系统管理员');
INSERT INTO ZG.ZG_ROLE (ID, NAME, ROLE_LEVEL, DESCRIPTION) VALUES (2, 'accounting', null, '会计岗');