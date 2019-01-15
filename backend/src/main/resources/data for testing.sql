--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6 (Ubuntu 10.6-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.6 (Ubuntu 10.6-0ubuntu0.18.04.1)

-- Started on 2019-01-13 13:36:33 CET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

---
INSERT INTO logins (login, password) VALUES ('admin', '123');
INSERT INTO logins (login, password) VALUES ('mentor', '123');
INSERT INTO logins (login, password) VALUES ('student', '123');

INSERT INTO mentors (name, surename, email, city, begin_work) VALUES ('Dominik', 'Unitester', 'dominik@cc.cc', 'Kraków', '31-01-2018', '201');
INSERT INTO mentors (name, surename, email, city, begin_work) VALUES ('Mateusch', 'Sqldriver', 'mateusch@cc.cc', 'Kraków', '01-04-2018', '202');
INSERT INTO mentors (name, surename, email, city, begin_work) VALUES ('Kornad', 'Front', 'tornad@cc.cc', 'Kraków', '1-04-2018', '203');

INSERT INTO students (logins_id, name, surname, email) VALUES ('1011', 'Karol', 'Askatrza', 'trzask1@mailg.moc');
INSERT INTO students (logins_id, name, surname, email) VALUES ('1021', 'Arol', 'Askatrz', 'trzask2@mailg.moc');
INSERT INTO students (logins_id, name, surname, email) VALUES ('1031', 'Rol', 'Askat', 'trzask3@mailg.moc');
INSERT INTO students (logins_id, name, surname, email) VALUES ('1041', 'Ol', 'Ask', 'trzask4@mailg.moc');

INSERT INTO admins (logins_id, name, surname, email) VALUES ('007', 'Master', 'Ofpuppets', 'masakrator666@cc.cc');
INSERT INTO admins (logins_id, name, surname, email) VALUES ('1313', 'Andjustice', 'Forall', 'rosekoorviator666@gcc.cc');

INSERT INTO quests (name, description, reward, creator_id, modified_by) VALUES ('Exploring Dungeon', 'random description with some random letters like aabbaeoierthdmfbparb', '15', 'tester', NULL);
INSERT INTO quests (name, description, reward, creator_id, modified_by) VALUES ('Solving MAgic Riddle', 'Headshot into keybouj67hrfzsenofdzniooiadenhbcimjozaerfhnybt', '50', 'tester', NULL);
INSERT INTO quest (name, description, reward, creator_id, modified_by) VALUES ('Slaying A Dragon', 'RNG2135498613617975431649879/986421319879', '25', 'tester', NULL);
INSERT INTO quests (name, description, reward, creator_id, modified_by) VALUES ('Killing rats in a basement', 'for begginers', '1', 'tester', NULL);

INSERT INTO artifacts (name, description, price, creator_id, modified_by) VALUES ('Motyka Of Destruction', 'Nasłońce ruszaj i zażółć gęślą jaźń', '20', 'admin', NULL);
INSERT INTO artifacts (name, description, price, creator_id, modified_by) VALUES ('Sierp of Kositrawnik', 'Czerwony i ostry', '12', 'admin', NULL);
INSERT INTO artifacts (name, description, price, creator_id, modified_by) VALUES ('Młot of Intelligence', 'Nienajostrzejszy młotek w szufladzie', '25', 'admin', NULL);
INSERT INTO artifacts (name, description, price, creator_id, modified_by) VALUES ('Kopia of Ksero', 'Bzzzzzzzzzzzyttt', '1', 'admin', NULL);
INSERT INTO artifacts (name, description, price, creator_id, modified_by) VALUES ('Blunderbuss of Lots of Mess', 'KABOOM!', '50', 'admin', NULL);
INSERT INTO artifacts (name, description, price, creator_id, modified_by) VALUES ('Bottle of Whitesky', 'Zażółć gęślą jaźń i pij i pij i pij', '15', 'admin', NULL);
INSERT INTO artifacts (name, description, price, creator_id, modified_by) VALUES ('Mana-mana Potion', 'chlup!', '5', 'admin', NULL);

INSERT INTO achivements (name, description, experience, tier) VALUES ('First Battle Winner', 'Survived 1st checkpoint', '50', 'I');
INSERT INTO achivements (name, description, experience, tier) VALUES ('Professor', 'Made presentation for lower group', '20');

