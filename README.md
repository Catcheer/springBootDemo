# 🚀 Enterprise Admin Matrix (EAM)

> 基于 **Spring Boot 3 + React 18 + TypeScript** 的企业级通用后台管理系统，集成 **RBAC 权限管理、JWT 身份认证、动态表单、操作日志、Dashboard 数据统计、Excel 导入导出** 等中后台常见能力。

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)
![React](https://img.shields.io/badge/React-18-blue.svg)
![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue.svg)
![License](https://img.shields.io/badge/License-MIT-purple.svg)

---

# 📖 项目简介

Enterprise Admin Matrix（EAM）是一个基于 **Spring Boot + React** 的前后端分离后台管理系统。

项目采用现代 Web 全栈开发技术，围绕企业中后台系统常见需求进行设计，实现了：

- 用户认证与授权
- RBAC 权限管理
- Dashboard 数据统计
- 操作日志审计
- 动态表单引擎
- Excel 数据导入导出
- Docker 容器化部署

整个项目遵循前后端分离架构，适合作为企业后台管理系统、权限管理系统、SaaS 平台基础脚手架，也可作为 Spring Boot + React 全栈学习项目。

---

# ✨ 核心特性

## 🔐 Spring Security + JWT 身份认证

- Spring Security 实现统一认证授权
- JWT 无状态 Token 登录
- 登录拦截
- Token 自动校验
- 接口权限控制
- 前后端分离认证方案

---

## 🛡 RBAC 权限管理

采用经典 RBAC（Role-Based Access Control）权限模型，实现：

- 用户管理
- 角色管理
- 权限管理
- 用户-角色关联
- 角色-权限关联
- 动态菜单生成
- 按钮级权限控制
- 路由级权限控制

真正做到前后端统一权限控制。

---

## 📊 Dashboard 数据看板

Dashboard 首页提供系统运行概览，包括：

- 学生数量统计
- 教师数量统计
- 班级数量统计
- 科目数量统计
- 学生性别比例
- 班级学生分布

基于 **ECharts** 实现可视化图表展示。

---

## 📝 操作日志（Operation Log）

基于 **Spring AOP** 实现统一操作日志记录。

支持记录：

- 操作用户
- 请求地址
- 请求参数
- 操作时间
- IP 地址
- 执行结果
- 执行耗时

日志异步写入数据库，减少对业务请求的影响。

---

## ⚙️ Schema 动态表单引擎

实现了基于 Schema 的动态表单渲染。

一个 Schema 即可描述：

- 表单布局
- 字段类型
- 校验规则
- 默认值
- 是否禁用
- Select 数据源

前端根据 Schema 自动生成表单，无需重复开发 CRUD 页面。

---

## 📂 Excel 导入导出

基于 Alibaba EasyExcel 实现：

- Excel 导入
- Excel 导出
- 模板下载
- 数据校验
- 批量导入

采用流式读取方式，降低大量数据导入时的内存占用。

---

## 🐳 Docker 部署

提供 Docker Compose 部署方案。

包含：

- Spring Boot
- MySQL
- Redis（可选）
- Nginx（可选）

支持快速部署整个项目。

---

# 🛠 技术栈

## 后端

- Spring Boot 3
- Spring Security
- JWT
- MyBatis
- MySQL 8
- EasyExcel
- Lombok
- Gradle

---

## 前端

- React 18
- TypeScript
- Redux Toolkit
- React Router
- Axios
- Ant Design
- Tailwind CSS
- ECharts
- Vite

---

# 📦 功能模块

```text
EAM
│
├── Dashboard 数据看板
├── 登录认证
├── 用户中心
│
├── 用户管理
├── 角色管理
├── 权限管理
│
├── 学生管理
├── 教师管理
├── 班级管理
├── 科目管理
│
├── 操作日志
├── 动态表单引擎
│
├── Excel 导入
└── Excel 导出
```

---

# 📷 系统预览

> 建议放项目截图

- 登录页
- Dashboard
- 用户管理
- 权限管理
- 学生管理
- 操作日志
- 动态表单

---

# 👤 演示账号

| 用户 | 角色 | 学生查询 | 学生新增 | 学生修改 | 学生删除 | Excel导入 | Excel导出 | 用户管理 | 角色管理 | 权限管理 |
|------|------|:------:|:------:|:------:|:------:|:------:|:------:|:------:|:------:|:------:|
| admin | Admin | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 张世伟 | 班主任 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | 👀 查看 | 👀 查看 | 👀 查看 |
| 刘永进 | 普通教师 | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| 后勤 | 后勤 | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |

> 👀 表示拥有查看权限，无新增、修改、删除权限。

---

# 🌐 在线体验

**Demo**

http://47.96.121.113:9006/app-react/login

---

# 📂 项目地址

## 前端

https://github.com/Catcheer/micro-frontend-project/tree/feat_login/react-project

## 后端

https://github.com/Catcheer/springBootDemo

---

# 🚀 快速启动

## 克隆项目

```bash
git clone https://github.com/Catcheer/springBootDemo.git
```

### 后端

```bash
cd springBootDemo
./gradlew bootRun
```

### 前端

```bash
npm install
npm run dev
```

---


# ⭐ Star

如果这个项目对你有所帮助，欢迎点一个 **Star ⭐**。

如果有建议或发现问题，也欢迎提交 **Issue** 或 **Pull Request**。