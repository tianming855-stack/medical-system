-- 医疗系统数据库初始化脚本
-- 执行方式（PowerShell）：
-- & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -uroot -p < "C:\Users\TM129\Doubao\chats\2026-11-06\new-chat\medical-system\sql\init.sql"

CREATE DATABASE IF NOT EXISTS medical_system
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

CREATE USER IF NOT EXISTS 'medical'@'localhost' IDENTIFIED BY 'Medical@2026';
GRANT ALL PRIVILEGES ON medical_system.* TO 'medical'@'localhost';
FLUSH PRIVILEGES;

USE medical_system;

CREATE TABLE IF NOT EXISTS patient (
    id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    patient_no   VARCHAR(32)  NOT NULL COMMENT '病历号',
    name         VARCHAR(50)  NOT NULL COMMENT '姓名',
    gender       TINYINT      NOT NULL DEFAULT 0 COMMENT '性别 0未知 1男 2女',
    birth_date   DATE         NULL COMMENT '出生日期',
    phone        VARCHAR(20)  NULL COMMENT '联系电话',
    id_card      VARCHAR(18)  NULL COMMENT '身份证号',
    created_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_patient_no (patient_no)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '患者基本信息表';

INSERT INTO patient (patient_no, name, gender, birth_date, phone)
VALUES ('P2026001', '张三', 1, '1990-05-20', '13800000000')
ON DUPLICATE KEY UPDATE name = VALUES(name);

SELECT '数据库 medical_system 初始化完成' AS result;
SELECT COUNT(*) AS patient_rows FROM patient;
