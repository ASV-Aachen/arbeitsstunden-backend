FROM adoptopenjdk/openjdk8-openj9 AS builder

COPY . ./work

WORKDIR /work
RUN ./gradlew build

FROM adoptopenjdk/openjdk8-openj9
COPY --from=builder /work/build app/

EXPOSE 8080

ENTRYPOINT ["java","-jar","app/libs/work-0.0.1-SNAPSHOT.jar"]