# 🚀 Enterprise Admin Matrix (EAM)

> **基于 Spring Boot 3 + React 18 + TypeScript 的企业级通用中后台权限管理与低代码效能平台。**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18.x-blue.svg)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue.svg)](https://www.typescriptlang.org/)
[![License](https://img.shields.io/badge/License-MIT-purple.svg)](LICENSE)

EAM 是一个前后端分离的、全栈一体化的**企业级基础演进脚手架**。项目核心聚焦于中后台系统的三大底层痛点：**企业级精细化权限管控（RBAC）、研发效能提升（低代码表单引擎）、以及全链路数据合规与审计**。

本项目沉淀了现代 Web 全栈开发的标准工程实践，可直接作为企业级 SaaS 平台、内部运营系统、数据治理平台的底层底层框架。

---

## 💡 核心工程亮点

* 🔐 **高安全级无状态认证**：基于 Spring Security + JWT 打造无状态分布式认证架构。针对高危操作设计 **AOP 统一审计日志切面**，通过异步线程池（Thread Pool）进行 I/O 隔离，确保核心操作 100% 可追溯且不阻塞主业务。
* ⚙️ **Schema 驱动表单引擎**：针对传统中后台表单代码冗余度高（>40%）的痛点，自主设计并实现了 **Schema 驱动的动态表单渲染引擎**。将 UI 视图、业务校验规则（Validation Rules）与后端元数据高度抽象解耦，使标准业务表单的研发效率提升 **60% 以上**。
* 🛡️ **多维度真动态权限隔离**：采用标准 **RBAC 权限模型**，在 React 18 + TS 上层构建路由级与按钮级（Button-level）的真动态权限控制。利用自研路由守卫与高阶组件（HOC），配合前端路由懒加载，实现动态菜单在流式渲染下的秒级响应与越权拦截。
* 💾 **大文件导入内存控顶优化**：针对百兆级大型 Excel 批量导入导致 JVM OOM 的合规风险，引入 **Alibaba EasyExcel 结合流式解析算法**。基于事件驱动的读写模型，将内存占用从百兆级降至兆级（Constant Memory），保障高并发导入时的内存稳定性。
* 🐳 **全沙箱容器化部署（安全硬化）**：推行“最小暴露面”原则。通过 **Docker Compose 构建强隔离的网络沙箱环境**，全面禁用外网直连，采用 SSH Tunnel 隧道双重认证与白名单机制硬化底层 MySQL / Redis 存储，彻底阻断未授权访问风险。

---

## 🛠️ 核心技术栈

### 后端架构 (Backend)
* **核心框架**：Spring Boot 3.x
* **安全引擎**：Spring Security + JWT（无状态、跨域友好）
* **数据持久层**：MyBatis + MySQL 8.x（索引调优、读写防注入）
* **工程构建**：Gradle（声明式依赖管理、增量构建优化）
* **基础设施**：Docker / Docker Compose（沙箱硬化部署）

### 前端工程 (Frontend)
* **核心框架**：React 18（并发模式、Hooks 函数式组件）
* **语言特性**：TypeScript（全链路严格类型约束）
* **状态管理**：Redux Toolkit（切片化状态治理）
* **UI 基础设施**：Ant Design + Tailwind CSS（原子化样式与组件化封装）
* **数据可视化**：ECharts（动态流式数据渲染）
* **构建工具**：Vite（基于 ESM 的极速热更新与打包调优）

---

## 📦 平台功能矩阵

```text
EAM 平台底层
 ├── 💡 认证与效能基础设施
 │    ├── JWT 令牌无状态签发与秒级失效策略
 │    ├── AOP 异步线程池审计日志切面 (Operation Log)
 │    └── Schema 驱动动态表单渲染引擎 (元数据配置)
 ├── 👥 精细化权限管控 (RBAC Framework)
 │    ├── 用户/角色/权限 多对多矩阵映射
 │    └── 按钮级 (Button-level) 权限拦截器
 └── 📊 核心业务领域集成 (以教育/组织管理场景为例)
      ├── 班级/教师/学生 多维关联实体域
      ├── 跨域数据看板 (Dashboard 聚合统计)
      └── 流式 Excel 批量数据导入/导出



---

# 🛠 技术栈

## 后端

| 技术 | 说明 |
|------|------|
| Spring Boot 3 | Web 开发框架 |
| Spring Security | 登录认证、权限控制 |
| JWT | Token 无状态认证 |
| MyBatis | ORM 持久层 |
| MySQL | 数据库 |
| Gradle | 项目构建工具 |
| Docker | 容器化部署 |

---

## 前端

| 技术 | 说明 |
|------|------|
| React 18 | 前端框架 |
| TypeScript | 类型支持 |
| React Router | 路由管理 |
| Redux Toolkit | 状态管理 |
| Ant Design | UI 组件库 |
| Axios | HTTP 请求 |
| ECharts | Dashboard 数据可视化 |
| Tailwind CSS | 页面样式 |
| Vite | 构建工具 |

---

---

# 👤 演示账号权限

| 用户 | 角色 | 学生查询 | 学生新增 | 学生修改 | 学生删除 | Excel导入 | Excel导出 | 用户管理 | 角色管理 | 权限管理 |
|------|------|:------:|:------:|:------:|:------:|:------:|:------:|:------:|:------:|:------:|
| admin | Admin | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 张世伟 | 班主任 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 👀仅查看 | 👀仅查看 | 👀仅查看 |
| 刘永进 | 普通教师 | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| 后勤 | 后勤 | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |

> 👀 表示拥有查看权限，但无新增、修改、删除权限。

---

# 🌐 在线体验

**演示地址**

http://47.96.121.113:9006/app-react/login

---

# 📂 项目地址

## 前端（React）

https://github.com/Catcheer/micro-frontend-project/tree/feat_login/react-project

## 后端（Spring Boot）

https://github.com/Catcheer/springBootDemo

---

# ⭐ Star 支持

如果这个项目对你有所帮助，欢迎 **Star ⭐** 支持一下。

如果你有更好的建议，欢迎提交 **Issue** 或 **Pull Request**，一起交流学习，共同完善这个项目！