CREATE USER IF NOT EXISTS "ROOT" SALT '072d8303a3a6af2a' HASH '79d9a8d9b735771570ebe31237ea066af96d4e5b3da6d1b6daf8c0055c695da2' ADMIN;
CREATE MEMORY TABLE "PUBLIC"."PHONES"(

    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,

    "CITYCODE" INTEGER,

    "CONTRYCODE" INTEGER,

    "NUMBER_PHONE" INTEGER,

    "USER_ENTITY_ID" BIGINT

);
ALTER TABLE "PUBLIC"."PHONES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.PHONES;
CREATE MEMORY TABLE "PUBLIC"."ROLES"(

    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,

    "NAME" CHARACTER VARYING(255)

);
ALTER TABLE "PUBLIC"."ROLES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ROLES;
CREATE MEMORY TABLE "PUBLIC"."USER_ROLES"(

    "USER_ID" BIGINT NOT NULL,

    "ROLE_ID" BIGINT NOT NULL

);
ALTER TABLE "PUBLIC"."USER_ROLES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_C" PRIMARY KEY("USER_ID", "ROLE_ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.USER_ROLES;
CREATE MEMORY TABLE "PUBLIC"."USERS"(

    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,

    "ACTIVE" BOOLEAN,

    "CREATED" TIMESTAMP(6) NOT NULL,

    "EMAIL" CHARACTER VARYING(255) NOT NULL,

    "LAST_LOGIN" TIMESTAMP(6) NOT NULL,

    "MODIFIED" TIMESTAMP(6) NOT NULL,

    "NAME" CHARACTER VARYING(255),

    "PASSWORD" CHARACTER VARYING(255) NOT NULL,

    "TOKEN" CHARACTER VARYING(255)

);
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4D" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.USERS;
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."UK6DOTKOTT2KJSP8VW4D0M25FB7" UNIQUE("EMAIL");
ALTER TABLE "PUBLIC"."USER_ROLES" ADD CONSTRAINT "PUBLIC"."FKH8CIRAMU9CC9Q3QCQIV4UE8A6" FOREIGN KEY("ROLE_ID") REFERENCES "PUBLIC"."ROLES"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."USER_ROLES" ADD CONSTRAINT "PUBLIC"."FKHFH9DX7W3UBF1CO1VDEV94G3F" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."PHONES" ADD CONSTRAINT "PUBLIC"."FK7BOM10QLSOL7411MLJQX65M0R" FOREIGN KEY("USER_ENTITY_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;