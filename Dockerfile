FROM node:24 AS frontend-builder
WORKDIR /app
COPY ./frontend ./
RUN npm -g upgrade
RUN npm install
RUN npm run generate

FROM eclipse-temurin:25-jdk AS backend-builder
WORKDIR /app
COPY ./ ./
COPY --from=frontend-builder /app/.output/public ./src/main/resources/static
RUN ./gradlew assemble --no-daemon --stacktrace

FROM gcr.io/distroless/java25-debian13
WORKDIR /app
COPY --from=backend-builder /app/build/libs/app.jar ./app.jar
EXPOSE 18080
ENTRYPOINT ["java", "-jar", "./app.jar"]
