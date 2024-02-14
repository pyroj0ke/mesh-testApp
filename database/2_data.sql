INSERT INTO "users" (id, name, date_of_birth, password) VALUES(nextval('USR_COUNTER_SEQ'), 'John', TO_DATE('01.01.2000', 'DD.MM.YYYY'), 'qwerty123');
INSERT INTO account (user_id, balance) VALUES(currval('USR_COUNTER_SEQ'), 8341.84);
INSERT INTO email_data (user_id, email) VALUES(currval('USR_COUNTER_SEQ'), 'example@mail.com');
INSERT INTO phone_data (user_id, phone) VALUES(currval('USR_COUNTER_SEQ'), '78005553535');

INSERT INTO "users" (id, name, date_of_birth, password) VALUES(nextval('USR_COUNTER_SEQ'), 'Sara', TO_DATE('23.08.1994', 'DD.MM.YYYY'), 'bt325Dg!');
INSERT INTO account (user_id, balance) VALUES(currval('USR_COUNTER_SEQ'), 62356612.46);
INSERT INTO email_data (user_id, email) VALUES(currval('USR_COUNTER_SEQ'), 'mail@yandex.com');
INSERT INTO phone_data (user_id, phone) VALUES(currval('USR_COUNTER_SEQ'), '79274572819');

INSERT INTO "users" (id, name, date_of_birth, password) VALUES(nextval('USR_COUNTER_SEQ'), 'Sam', TO_DATE('07.11.1998', 'DD.MM.YYYY'), 'leetcode');
INSERT INTO account (user_id, balance) VALUES(currval('USR_COUNTER_SEQ'), 1337.00);
INSERT INTO email_data (user_id, email) VALUES(currval('USR_COUNTER_SEQ'), 'kekus@gmail.com');
INSERT INTO phone_data (user_id, phone) VALUES(currval('USR_COUNTER_SEQ'), '79994819843');