-- liquibase formated sql

-- changeset marco:000-initial-empty-schema.sql logicalFilePath:file_0.0.0

-- H2 2.1.214;
SET DB_CLOSE_DELAY -1;

DROP TABLE IF EXISTS "PUBLIC"."MOVIE" CASCADE;
DROP TABLE IF EXISTS "PUBLIC"."ROUND" CASCADE;
DROP TABLE IF EXISTS "PUBLIC"."GAME" CASCADE;
DROP TABLE IF EXISTS "PUBLIC"."PLAYER" CASCADE;

CREATE USER IF NOT EXISTS "SA" SALT '8c93aba1e27e8a25' HASH 'ddf70794194ec101c82e02244332102f48a88cb553a587d865784ab40f9a141a' ADMIN;
CREATE MEMORY TABLE "PUBLIC"."GAME"(
    "FINISHED_AT" TIMESTAMP(6),
    "STARTED_AT" TIMESTAMP(6) NOT NULL,
    "ID" CHARACTER VARYING(255) NOT NULL,
    "PLAYER_ID" CHARACTER VARYING(255)
);
ALTER TABLE "PUBLIC"."GAME" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_21" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.GAME;
CREATE MEMORY TABLE "PUBLIC"."MOVIE"(
    "RATING" FLOAT(53),
    "RELEASE_YEAR" BIGINT,
    "VOTES" BIGINT,
    "ID" CHARACTER VARYING(255) NOT NULL,
    "POSTER_URL" CHARACTER VARYING(255),
    "TITLE" CHARACTER VARYING(255)
);
ALTER TABLE "PUBLIC"."MOVIE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.MOVIE;
CREATE MEMORY TABLE "PUBLIC"."PLAYER"(
    "ID" CHARACTER VARYING(255) NOT NULL,
    "NAME" CHARACTER VARYING(255)
);
ALTER TABLE "PUBLIC"."PLAYER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.PLAYER;
CREATE MEMORY TABLE "PUBLIC"."ROUND"(
    "CORRECT" BOOLEAN,
    "ANSWERED_AT" TIMESTAMP(6),
    "GAME_ID" CHARACTER VARYING(255) NOT NULL,
    "ID" CHARACTER VARYING(255) NOT NULL,
    "LEFT_ID" CHARACTER VARYING(255) NOT NULL,
    "RIGHT_ID" CHARACTER VARYING(255) NOT NULL
);
ALTER TABLE "PUBLIC"."ROUND" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4A8B" PRIMARY KEY("ID");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ROUND;
ALTER TABLE "PUBLIC"."ROUND" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4A" UNIQUE("LEFT_ID");
ALTER TABLE "PUBLIC"."ROUND" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4A8" UNIQUE("RIGHT_ID");
ALTER TABLE "PUBLIC"."GAME" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" UNIQUE("PLAYER_ID");
ALTER TABLE "PUBLIC"."ROUND" ADD CONSTRAINT "PUBLIC"."FK8F08HN9PORAYUV0J5PDLAOJ0Y" FOREIGN KEY("RIGHT_ID") REFERENCES "PUBLIC"."MOVIE"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."GAME" ADD CONSTRAINT "PUBLIC"."FK69KXN13HW2QILI6X6EM4UR6KD" FOREIGN KEY("PLAYER_ID") REFERENCES "PUBLIC"."PLAYER"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."ROUND" ADD CONSTRAINT "PUBLIC"."FKPPXONWN9E98LCCY46M2EVE67M" FOREIGN KEY("GAME_ID") REFERENCES "PUBLIC"."GAME"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."ROUND" ADD CONSTRAINT "PUBLIC"."FK6AO5QA62M0KSX6RPREW3U0FMD" FOREIGN KEY("LEFT_ID") REFERENCES "PUBLIC"."MOVIE"("ID") NOCHECK;