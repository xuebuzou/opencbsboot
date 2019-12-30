create table ZG_USER_ROLE
(
    USER_ID NUMBER not null,
    ROLE_ID NUMBER not null,
    constraint USER_ROLE_PK
        primary key (USER_ID, ROLE_ID)
)
/

INSERT INTO ZG.ZG_USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO ZG.ZG_USER_ROLE (USER_ID, ROLE_ID) VALUES (2, 1);