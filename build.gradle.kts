import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.song"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    // 开发时使用 mirai-core-api，运行时提供 mirai-core

    implementation ("io.github.microutils:kotlin-logging:2.1.21")

    api("net.mamoe:mirai-core-api:2.10.1")
    runtimeOnly("net.mamoe:mirai-core:2.10.1")

    // 可以简单地只添加 api("net.mamoe:mirai-core:2.6.1")

    implementation("com.baomidou:mybatis-plus-generator:3.5.1")
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.1")

    implementation("org.freemarker:freemarker:2.3.31")

    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.6.7")

    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation("mysql:mysql-connector-java:8.0.28")
    testImplementation("org.springframework:spring-test:5.3.19")
    implementation ("org.reactivestreams:reactive-streams:1.0.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }

}

tasks.withType<Test> {
    useJUnitPlatform()
    
}
