plugins {
    java
    id("org.springframework.boot") version "2.7.13"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "team.hanaro.hanamate"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.5.6")
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    runtimeOnly ("com.h2database:h2")

    //p6spy - sql 쿼리문 모니터링 및 로깅 라이브러리
    implementation ("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6")


    //security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")


    //redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Spring Boot Starter Web
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Swagger UI Starter
    implementation("io.springfox:springfox-boot-starter:3.0.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    jar {
        enabled = false
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes["Main-Class"] = "team.hanaro.hanamate.HanamateServerApplication"
        }
    }
}
