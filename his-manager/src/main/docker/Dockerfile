 #基础镜像
FROM java:8
 #将本地文件挂载到容器
VOLUME /home/container
#拷贝文件到容器
ADD his-authorization-0.0.1-SNAPSHOT.jar app.jar
#运行命令
RUN bash -c 'touch  /app.jar'
# 指定容器需要映射到宿主机器的端口
EXPOSE 30004
#容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]