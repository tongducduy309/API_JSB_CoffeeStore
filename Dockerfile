# ==== BUILD STAGE ====
FROM eclipse-temurin:24-jdk AS build
WORKDIR /app
COPY . .
COPY .env .env
# Fix CRLF nếu clone từ Windows + cấp quyền thực thi cho mvnw
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Build bằng Maven Wrapper
RUN ./mvnw -q -DskipTests clean package

# ==== RUNTIME STAGE ====
FROM eclipse-temurin:24-jre
ENV TZ=Asia/Ho_Chi_Minh \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70.0"

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render sẽ inject PORT; Spring Boot cần server.port
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]
