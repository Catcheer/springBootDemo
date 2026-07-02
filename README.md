# 🎓 Student Management System

> 一个基于 **Spring Boot 3 + React 18** 的前后端分离学生管理系统。

本项目主要用于学习现代 Web 全栈开发，采用 **RBAC 权限模型** 实现用户、角色、权限管理，并结合 **Spring Security + JWT** 完成认证授权，涵盖学生管理、Excel 导入导出、用户中心等企业项目常见功能。

适合作为：

* 📚 Spring Boot + React 学习项目
* 💼 Java / 前端 / 全栈岗位面试项目
* 🚀 企业后台管理系统基础模板
* 🤝 开源交流学习项目

---

# ✨ 项目特色

* ✅ Spring Boot 3 + React 18 前后端分离
* ✅ Spring Security + JWT 无状态认证
* ✅ RBAC（用户-角色-权限）权限模型
* ✅ 学生信息 CRUD
* ✅ Excel 批量导入 / 导出学生数据
* ✅ 用户中心（头像上传、手机号修改等）
* ✅ 前端菜单、按钮级权限控制
* ✅ RESTful API 设计
* ✅ Docker 容器化部署

---

# 🛠 技术栈

## 后端

| 技术              | 说明          |
| --------------- | ----------- |
| Spring Boot 3   | Web 开发框架    |
| Spring Security | 登录认证、权限控制   |
| JWT             | Token 无状态认证 |
| MyBatis         | ORM 持久层     |
| MySQL           | 数据库         |
| Gradle          | 项目构建工具      |
| Docker          | 容器化部署       |

---

## 前端

| 技术           | 说明      |
| ------------ | ------- |
| React 18     | 前端框架    |
| TypeScript   | 类型支持    |
| React Router | 路由管理    |
| Ant Design   | UI 组件库  |
| Axios        | HTTP 请求 |
| Tailwind CSS | 页面样式    |
| Vite         | 构建工具    |

---

# 📦 已实现功能

## 🔐 登录认证

* 用户登录
* 用户登出
* JWT Token 认证
* Token 自动携带
* Token 失效处理
* Spring Security 权限校验

---

## 👥 用户、角色、权限管理（RBAC）

系统采用经典 RBAC（Role-Based Access Control）权限模型：

```text
用户（User）
      │
      ▼
角色（Role）
      │
      ▼
权限（Permission）
```

目前已实现：

* 用户管理
* 角色管理
* 权限管理
* 用户绑定角色
* 角色绑定权限
* 前端菜单权限控制
* 按钮级权限控制
* 页面访问权限控制

---

## 🎓 学生管理

已完成：

* 学生列表
* 新增学生
* 编辑学生
* 删除学生
* 条件查询
* Excel 批量导入
* Excel 导出

---

## 👤 用户中心

每个用户均可维护自己的个人信息：

* 修改昵称
* 修改手机号
* 上传头像
* 查看个人信息

---

# 👤 演示账号权限

| 用户    | 角色    | 学生查询 | 学生新增 | 学生修改 | 学生删除 | Excel导入 | Excel导出 |  用户管理 |  角色管理 |  权限管理 |
| ----- | ----- | :--: | :--: | :--: | :--: | :-----: | :-----: | :---: | :---: | :---: |
| admin | Admin |   ✅  |   ✅  |   ✅  |   ✅  |    ✅    |    ✅    |   ✅   |   ✅   |   ✅   |
| 张世伟   | 班主任   |   ✅  |   ✅  |   ✅  |   ✅  |    ✅    |    ✅    | 👀仅查看 | 👀仅查看 | 👀仅查看 |
| 刘永进   | 普通教师  |   ✅  |   ❌  |   ❌  |   ❌  |    ❌    |    ❌    |   ❌   |   ❌   |   ❌   |
| 后勤    | 后勤    |   ❌  |   ❌  |   ❌  |   ❌  |    ❌    |    ❌    |   ❌   |   ❌   |   ❌   |

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



# 💡 项目亮点

* 基于 Spring Security + JWT 实现无状态认证
* 采用经典 RBAC 权限模型
* 前后端统一权限控制
* 菜单级 + 按钮级权限控制
* React 18 + TypeScript 开发
* RESTful API 设计规范
* Excel 批量导入导出
* Docker 容器化部署
* 用户中心支持头像上传及个人资料维护

---

# 🚀 后续计划

持续完善更多企业级功能：

* [ ] Dashboard 数据统计
* [ ] 操作日志（Operation Log）
* [ ] 登录日志
* [ ] Redis 缓存
* [ ] 修改密码
* [ ] 数据权限（按班级、教师过滤）
* [ ] WebSocket 消息通知
* [ ] Docker Compose 一键部署
* [ ] GitHub Actions 自动部署
* [ ] Swagger / OpenAPI 接口文档
* [ ] 单元测试
* [ ] 数据字典管理
* [ ] 系统参数配置

---

# ⭐ Star 支持

如果这个项目对你有所帮助，欢迎 **Star ⭐** 支持一下。

也欢迎提交 **Issue** 或 **Pull Request**，一起交流学习，共同完善这个项目！
