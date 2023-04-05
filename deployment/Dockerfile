#FROM amazoncorretto:17-alpine
#VOLUME /tmp
#COPY *.jar cafeapi.jar
#ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
## Replace with a non-root user to avoid running the container with excessive privileges
#USER appuser
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar cafeapi.jar" ]

# Use the official OpenJDK image as the base image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle Wrapper files (gradlew and gradle) to the container
COPY gradlew .
COPY gradle gradle

# Copy the Gradle configuration files (build.gradle and settings.gradle) to the container
COPY build.gradle .
COPY settings.gradle .

# Copy the source code of the application to the container
COPY applications/app-service/src src

# Build the application using Gradle
RUN ./gradlew build

# Set the command to run the application when the container is started
CMD ["java", "-jar", "build/libs/CafeAppBack-0.0.1-SNAPSHOT.jar"]
