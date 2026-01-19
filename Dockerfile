# 基础镜像
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/eclipse-temurin:21-jdk-alpine
# 设置容器内工作目录
WORKDIR /app
# Gradle 默认打包后的 jar 路径在 build/libs/
# 注意：* .jar 会匹配到 plain.jar，建议指定具体的 jar 文件名或使用通配符但排除 plain
COPY build/libs/*.jar app.jar
# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]