CREATE DATABASE app_db;
USE app_db;
CREATE USER 'app_user'@'%' IDENTIFIED BY 'P@ssw0rd';
GRANT ALL ON app_db.* to 'app_user'@'%';