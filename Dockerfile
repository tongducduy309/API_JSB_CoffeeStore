# ==== BUILD STAGE ====
FROM eclipse-temurin:24-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests clean package

# ==== RUNTIME STAGE ====
FROM eclipse-temurin:24-jre
ENV TZ=Asia/Ho_Chi_Minh \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
