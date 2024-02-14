CREATE TABLE IF NOT EXISTS "users" (
    id int8 NOT NULL,
    "name" varchar(500) NOT NULL,
    date_of_birth date NOT NULL,
    "password" varchar(500) NOT NULL,
    CONSTRAINT password_check CHECK (((length((password)::text) >= 8) AND (length((password)::text) <= 500))),
    CONSTRAINT user_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS account (
    id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NOT NULL,
    balance numeric(15, 2) NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY (id),
    CONSTRAINT account_unique UNIQUE (user_id),
    CONSTRAINT balance_check CHECK ((balance >= (0)::numeric))
    );

ALTER TABLE account ADD CONSTRAINT account_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE email_data (
    id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NOT NULL,
    email varchar(200) NOT NULL,
    CONSTRAINT email_data_pk PRIMARY KEY (id),
    CONSTRAINT email_data_unique UNIQUE (email)
);

ALTER TABLE email_data ADD CONSTRAINT email_data_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE phone_data (
    id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NOT NULL,
    phone varchar(13) NULL,
    CONSTRAINT phone_data_pk PRIMARY KEY (id),
    CONSTRAINT phone_data_unique UNIQUE (phone)
);

ALTER TABLE phone_data ADD CONSTRAINT phone_data_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE SEQUENCE  IF NOT EXISTS USR_COUNTER_SEQ MINVALUE 1 START WITH 1 INCREMENT BY 1;