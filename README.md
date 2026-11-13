# medical-system 医疗系统

实验一产物：开发环境搭建 + Spring Boot 骨架 + 基础 Controller 接口。

仓库地址：<https://github.com/tianming855-stack/medical-system>

分支：`main`（可交付主干）/ `develop`（日常集成）。

## 本机环境

| 组件 | 版本 | 安装路径 |
| --- | --- | --- |
| JDK | Temurin 17.0.20.1 | `D:\dev\jdk17` |
| Maven | 3.9.16 | `D:\dev\maven` |
| Git | 2.56.0 | `D:\dev\Git` |
| MySQL | 8.0（服务 MySQL80） | 系统安装 |
| IDEA | IntelliJ IDEA Ultimate 2026.3（build IU-263.7307.200） | `D:\IntelliJ IDEA 2026.3` |

环境变量（用户级，已写入注册表）：

- `JAVA_HOME` = `D:\dev\jdk17`
- `MAVEN_HOME` = `D:\dev\maven`
- `MAVEN_OPTS` = `-Dfile.encoding=UTF-8`
- `Path` 追加 `D:\dev\maven\bin;D:\dev\jdk17\bin;D:\dev\Git\cmd`

## 目录结构

```
medical-system
├─ pom.xml
├─ src/main/java/com/hospital/medical
│  ├─ MedicalApplication.java            启动类
│  ├─ controller/HelloController.java    环境验证接口
│  ├─ controller/PatientController.java  患者查询接口
│  ├─ service/PatientService.java        业务层
│  ├─ dao/PatientDao.java                数据访问层（JdbcTemplate）
│  └─ entity/Patient.java                患者实体
├─ src/main/resources/application.yml    含 MySQL 数据源配置
├─ sql/init.sql                          数据库初始化脚本
├─ docs/实验1-环境搭建与验证记录.md
└─ README.md
```

## 启动

```powershell
cd C:\Users\TM129\Doubao\chats\2026-11-06\new-chat\medical-system
mvn spring-boot:run
```

## 接口

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/v1/hello?name=xxx` | 返回系统状态 JSON |
| GET | `/api/v1/ping` | 返回 `pong` |
| GET | `/api/v1/patient/db-check` | 返回数据库版本、库名与患者总数 |
| GET | `/api/v1/patient/list?keyword=xxx` | 按姓名或病历号模糊查询患者，关键词可省略 |
| GET | `/actuator/health` | 健康检查 |

## 数据库

- 库名 `medical_system`，字符集 utf8mb4，排序规则 utf8mb4_general_ci
- 业务账号 `medical@localhost` / `Medical@2026`（root 密码不入库）
- 表 `patient`（患者基本信息表），含样例数据 `P2026001 张三`
- 初始化脚本 `sql/init.sql`，可重复执行，已存在的对象会跳过

重跑脚本（需输入 root 密码）：

```powershell
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -uroot -p --default-character-set=utf8mb4 < "C:\Users\TM129\Doubao\chats\2026-11-06\new-chat\medical-system\sql\init.sql"
```

注意：必须用文件重定向 `<` 导入。用 `Get-Content | mysql` 管道会把中文转成 `?`。

应用连接配置在 `src/main/resources/application.yml`，使用业务账号 `medical`，连接串带 `useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true`。连接池为 HikariCP，最大 5 个连接。

## 分支规范

- `main`：可运行、可交付的主干，只接受合并，不直接提交
- `develop`：日常集成，功能合并到此
- `feature/<模块名>`：功能开发，如 `feature/patient`
- `hotfix/<问题名>`：主干紧急修复
- 合并前先 `git pull --rebase`，禁止把 `target/`、`.idea/` 提交进仓库

## 提交信息规范

`feat: 新增患者查询接口` / `fix: 修复参数校验空指针` / `docs: 补充接口说明`
类型前缀：feat、fix、docs、style、refactor、test、chore
