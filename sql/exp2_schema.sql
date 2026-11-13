-- =============================================================
-- 实验二：医疗系统数据库设计与基础 CRUD
-- 数据库：medical_system    字符集：utf8mb4    引擎：InnoDB
-- 说明：按第三范式设计，先删后建，脚本可重复执行
-- =============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS prescription_item;
DROP TABLE IF EXISTS prescription;
DROP TABLE IF EXISTS registration;
DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS department;

SET FOREIGN_KEY_CHECKS = 1;

-- -------------------------------------------------------------
-- 1. 科室表 department
-- 主键 id 为代理键，dept_code 为业务唯一标识
-- -------------------------------------------------------------
CREATE TABLE department (
  id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '科室主键',
  dept_code    VARCHAR(20)  NOT NULL                COMMENT '科室编码，业务唯一标识',
  dept_name    VARCHAR(50)  NOT NULL                COMMENT '科室名称',
  location     VARCHAR(100) DEFAULT NULL            COMMENT '科室位置',
  phone        VARCHAR(20)  DEFAULT NULL            COMMENT '科室联系电话',
  status       TINYINT      NOT NULL DEFAULT 1      COMMENT '状态 1启用 0停用',
  created_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_dept_code (dept_code),
  CONSTRAINT ck_department_status CHECK (status IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='科室信息表';

-- -------------------------------------------------------------
-- 2. 医生表 doctor
-- department_id 为外键，科室名称不复制到本表，避免传递依赖
-- -------------------------------------------------------------
CREATE TABLE doctor (
  id            BIGINT      NOT NULL AUTO_INCREMENT COMMENT '医生主键',
  doctor_no     VARCHAR(20) NOT NULL                COMMENT '医生工号，业务唯一标识',
  name          VARCHAR(30) NOT NULL                COMMENT '姓名',
  gender        TINYINT     NOT NULL                COMMENT '性别 1男 2女',
  title         VARCHAR(20) DEFAULT NULL            COMMENT '职称',
  department_id BIGINT      NOT NULL                COMMENT '所属科室，外键',
  phone         VARCHAR(20) NOT NULL                COMMENT '联系电话',
  email         VARCHAR(60) DEFAULT NULL            COMMENT '电子邮箱',
  specialty     VARCHAR(100) DEFAULT NULL           COMMENT '擅长方向',
  status        TINYINT     NOT NULL DEFAULT 1      COMMENT '状态 1在职 0停诊',
  created_time  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_doctor_no (doctor_no),
  KEY idx_doctor_department (department_id),
  CONSTRAINT fk_doctor_department FOREIGN KEY (department_id) REFERENCES department (id),
  CONSTRAINT ck_doctor_gender  CHECK (gender IN (1, 2)),
  CONSTRAINT ck_doctor_status  CHECK (status IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='医生信息表';

-- -------------------------------------------------------------
-- 3. 患者表 patient
-- id_card 唯一，手机号与身份证作为敏感字段由服务端校验格式
-- -------------------------------------------------------------
CREATE TABLE patient (
  id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '患者主键',
  patient_no  VARCHAR(20) NOT NULL                COMMENT '病历号，业务唯一标识',
  name        VARCHAR(30) NOT NULL                COMMENT '姓名',
  gender      TINYINT     NOT NULL                COMMENT '性别 1男 2女',
  birth_date  DATE        DEFAULT NULL            COMMENT '出生日期',
  phone       VARCHAR(20) NOT NULL                COMMENT '手机号',
  id_card     VARCHAR(18) DEFAULT NULL            COMMENT '身份证号',
  address     VARCHAR(200) DEFAULT NULL           COMMENT '联系地址',
  status      TINYINT     NOT NULL DEFAULT 1      COMMENT '状态 1正常 0停用',
  create_time DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建档时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_patient_no (patient_no),
  UNIQUE KEY uk_patient_id_card (id_card),
  KEY idx_patient_phone (phone),
  CONSTRAINT ck_patient_gender CHECK (gender IN (1, 2)),
  CONSTRAINT ck_patient_status CHECK (status IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='患者基本信息表';

-- -------------------------------------------------------------
-- 4. 挂号记录表 registration
-- 只存 patient_id 与 doctor_id：科室可由医生关联得到，若在本表
-- 再存 department_id 则形成对主键的传递依赖，违反第三范式
-- charge_amount 为实收金额，受减免政策影响，不由 reg_type 唯一决定
-- -------------------------------------------------------------
CREATE TABLE registration (
  id            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '挂号主键',
  reg_no        VARCHAR(24)  NOT NULL                COMMENT '挂号单号，业务唯一标识',
  patient_id    BIGINT       NOT NULL                COMMENT '患者，外键',
  doctor_id     BIGINT       NOT NULL                COMMENT '医生，外键',
  reg_time      DATETIME     NOT NULL                COMMENT '挂号时间',
  reg_type      TINYINT      NOT NULL DEFAULT 1      COMMENT '类型 1普通 2专家 3急诊',
  charge_amount DECIMAL(8,2) NOT NULL DEFAULT 0.00    COMMENT '实收挂号费',
  status        TINYINT      NOT NULL DEFAULT 0      COMMENT '状态 0待诊 1就诊中 2已完成 3已取消',
  created_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_reg_no (reg_no),
  KEY idx_reg_patient (patient_id),
  KEY idx_reg_doctor (doctor_id),
  CONSTRAINT fk_reg_patient FOREIGN KEY (patient_id) REFERENCES patient (id),
  CONSTRAINT fk_reg_doctor  FOREIGN KEY (doctor_id)  REFERENCES doctor (id),
  CONSTRAINT ck_reg_type   CHECK (reg_type IN (1, 2, 3)),
  CONSTRAINT ck_reg_status CHECK (status IN (0, 1, 2, 3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='挂号记录表';

-- -------------------------------------------------------------
-- 5. 电子处方表 prescription
-- 患者与医生可由 registration 关联得到，本表不再重复存储
-- 处方金额由明细汇总计算，不设冗余的金额字段
-- -------------------------------------------------------------
CREATE TABLE prescription (
  id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '处方主键',
  presc_no        VARCHAR(24)  NOT NULL                COMMENT '处方编号，业务唯一标识',
  registration_id BIGINT       NOT NULL                COMMENT '对应挂号记录，外键',
  diagnosis       VARCHAR(200) NOT NULL                COMMENT '临床诊断',
  advice          VARCHAR(500) DEFAULT NULL            COMMENT '医嘱',
  status          TINYINT      NOT NULL DEFAULT 0      COMMENT '状态 0草稿 1已开具 2已发药 3已作废',
  created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开方时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_presc_no (presc_no),
  KEY idx_presc_registration (registration_id),
  CONSTRAINT fk_presc_registration FOREIGN KEY (registration_id) REFERENCES registration (id),
  CONSTRAINT ck_presc_status CHECK (status IN (0, 1, 2, 3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='电子处方表';

-- -------------------------------------------------------------
-- 6. 处方明细表 prescription_item
-- 一张处方对应多条明细，药品名称作为属性存放，不作冗余拆分
-- -------------------------------------------------------------
CREATE TABLE prescription_item (
  id              BIGINT      NOT NULL AUTO_INCREMENT COMMENT '明细主键',
  prescription_id BIGINT      NOT NULL                COMMENT '所属处方，外键',
  drug_name       VARCHAR(60) NOT NULL                COMMENT '药品名称',
  spec            VARCHAR(40) DEFAULT NULL            COMMENT '规格',
  dosage          VARCHAR(40) DEFAULT NULL            COMMENT '单次用量',
  quantity        INT         NOT NULL                COMMENT '数量',
  unit_price      DECIMAL(8,2) DEFAULT NULL           COMMENT '单价',
  usage_method    VARCHAR(60) DEFAULT NULL            COMMENT '用法',
  PRIMARY KEY (id),
  KEY idx_item_prescription (prescription_id),
  CONSTRAINT fk_item_prescription FOREIGN KEY (prescription_id) REFERENCES prescription (id) ON DELETE CASCADE,
  CONSTRAINT ck_item_quantity CHECK (quantity > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='处方明细表';

-- =============================================================
-- 基础数据
-- =============================================================
INSERT INTO department (dept_code, dept_name, location, phone) VALUES
('D001', '内科',   '门诊楼2层东区', '0743-8561001'),
('D002', '外科',   '门诊楼3层西区', '0743-8561002'),
('D003', '儿科',   '门诊楼1层南区', '0743-8561003'),
('D004', '急诊科', '急诊楼1层',     '0743-8561004');

INSERT INTO doctor (doctor_no, name, gender, title, department_id, phone, email, specialty) VALUES
('DOC001', '张明远', 1, '主任医师', 1, '13800000001', 'zhangmy@hospital.com', '高血压、冠心病'),
('DOC002', '李慧',   2, '副主任医师', 1, '13800000002', 'lihui@hospital.com', '呼吸系统疾病'),
('DOC003', '王建国', 1, '主任医师', 2, '13800000003', 'wangjg@hospital.com', '肝胆外科'),
('DOC004', '陈晓燕', 2, '主治医师', 3, '13800000004', 'chenxy@hospital.com', '小儿呼吸道感染'),
('DOC005', '周涛',   1, '医师',     4, '13800000005', 'zhoutao@hospital.com', '急诊创伤');

INSERT INTO patient (patient_no, name, gender, birth_date, phone, id_card, address) VALUES
('P2026001', '张三', 1, '1990-05-20', '13800000000', '433101199005201234', '吉首市人民路12号'),
('P2026002', '李四', 2, '1985-11-03', '13800000011', '433101198511033456', '吉首市武陵路8号'),
('P2026003', '王芳', 2, '1998-02-17', '13800000012', '433101199802175678', '吉首市乾州新区'),
('P2026004', '赵强', 1, '1976-07-09', '13800000013', '433101197607097890', '吉首市峒河街道'),
('P2026005', '孙丽', 2, '2001-09-28', '13800000014', '433101200109281234', '吉首市小溪桥'),
('P2026006', '刘洋', 1, '2012-04-15', '13800000015', '433101201204155678', '吉首市吉大路');

INSERT INTO registration (reg_no, patient_id, doctor_id, reg_time, reg_type, charge_amount, status) VALUES
('R20261113001', 1, 1, '2026-11-13 08:30:00', 1, 12.00, 2),
('R20261113002', 2, 2, '2026-11-13 08:45:00', 1, 12.00, 2),
('R20261113003', 3, 4, '2026-11-13 09:10:00', 2, 30.00, 1),
('R20261113004', 4, 3, '2026-11-13 09:25:00', 2, 30.00, 1),
('R20261113005', 5, 1, '2026-11-13 10:05:00', 1, 12.00, 0),
('R20261113006', 6, 5, '2026-11-13 10:30:00', 3, 50.00, 0);

INSERT INTO prescription (presc_no, registration_id, diagnosis, advice, status) VALUES
('PC20261113001', 1, '原发性高血压 2 级', '低盐饮食，规律服药，两周后复查', 2),
('PC20261113002', 2, '急性支气管炎', '多饮水，避免受凉，按时服药', 2),
('PC20261113003', 3, '小儿上呼吸道感染', '监测体温，退热后复诊', 1);

INSERT INTO prescription_item (prescription_id, drug_name, spec, dosage, quantity, unit_price, usage_method) VALUES
(1, '苯磺酸氨氯地平片', '5mg*28片', '1片/次', 2, 32.50, '口服，每日一次'),
(1, '阿托伐他汀钙片', '20mg*7片', '1片/次', 1, 45.00, '口服，每晚一次'),
(2, '头孢克肟胶囊', '0.1g*12粒', '1粒/次', 2, 28.00, '口服，每日两次'),
(2, '氨溴索口服液', '100ml', '10ml/次', 1, 22.80, '口服，每日三次'),
(3, '布洛芬混悬液', '100ml', '5ml/次', 1, 18.60, '口服，发热时服用');

-- 业务账号授权（与应用配置中的账号一致）
GRANT SELECT, INSERT, UPDATE, DELETE ON medical_system.* TO 'medical'@'localhost';
FLUSH PRIVILEGES;

SELECT '科室' AS 表名, COUNT(*) AS 记录数 FROM department
UNION ALL SELECT '医生', COUNT(*) FROM doctor
UNION ALL SELECT '患者', COUNT(*) FROM patient
UNION ALL SELECT '挂号', COUNT(*) FROM registration
UNION ALL SELECT '处方', COUNT(*) FROM prescription
UNION ALL SELECT '处方明细', COUNT(*) FROM prescription_item;
