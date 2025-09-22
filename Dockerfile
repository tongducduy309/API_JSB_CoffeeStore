# ---- build stage ----
FROM eclipse-temurin:24-jdk AS build
WORKDIR /app
COPY . .
# nếu dùng Maven:
RUN ./mvnw -q -DskipTests package
# nếu dùng Gradle (thay dòng trên):
# RUN ./gradlew -q -x test bootJar

# ---- run stage ----
FROM eclipse-temurin:24-jre
WORKDIR /app
ARG JAR=target/*.jar
# với Gradle, đổi path sang build/libs/*.jar
COPY --from=build /app/${JAR} app.jar

# Render sẽ inject PORT; Spring Boot cần server.port
ENV PORT=8080
ENV JAVA_OPTS="-Xms256m -Xmx512m"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]
