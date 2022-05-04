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
    implementation("junit:junit:4.13.2")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    // 开发时使用 mirai-core-api，运行时提供 mirai-core

    implementation ("io.github.microutils:kotlin-logging:2.1.21")

    // 可以简单地只添加 api("net.mamoe:mirai-core:2.6.1")
    api("net.mamoe:mirai-core-api:2.10.1")
    runtimeOnly("net.mamoe:mirai-core:2.10.1")



    implementation("com.baomidou:mybatis-plus-generator:3.5.1")
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.1")

    //mybatisplus生成器
    implementation("org.freemarker:freemarker:2.3.31")

    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.6.7")

    // mysql
    implementation("mysql:mysql-connector-java:8.0.28")
    testImplementation("org.springframework:spring-test:5.3.19")
    implementation ("org.reactivestreams:reactive-streams:1.0.2")

    //日志
    implementation("log4j:log4j:1.2.17")



    //redis
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis:2.6.7")

    //胡图图
// https://mvnrepository.com/artifact/cn.hutool/hutool-all
    implementation("cn.hutool:hutool-all:5.8.0.M4")
// https://mvnrepository.com/artifact/com.alibaba/fastjson
    //对象序列化
    implementation("com.alibaba:fastjson:2.0.1")

// https://mvnrepository.com/artifact/cn.dev33/sa-token-spring-boot-starter
    implementation("cn.dev33:sa-token-spring-boot-starter:1.29.0")

    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

// https://mvnrepository.com/artifact/com.dtflys.forest/forest-spring-boot-starter
    implementation("com.dtflys.forest:forest-spring-boot-starter:1.5.16")
// https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
    implementation("javax.xml.bind:jaxb-api:2.3.1")


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
