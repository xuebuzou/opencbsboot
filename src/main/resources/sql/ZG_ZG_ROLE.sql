create table ZG_ROLE
(
    ID          NUMBER not null
        primary key,
    NAME        VARCHAR2(50),
    ROLE_LEVEL  NUMBER,
    DESCRIPTION VARCHAR2(50)
)
/

INSERT INTO ZG.ZG_ROLE (ID, NAME, ROLE_LEVEL, DESCRIPTION) VALUES (2, 'verification', null, '2-上门核实团队');
INSERT INTO ZG.ZG_ROLE (ID, NAME, ROLE_LEVEL, DESCRIPTION) VALUES (1, 'admin', null, '1-系统管理员');