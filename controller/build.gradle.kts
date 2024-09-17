plugins {
    id("java-library")
    id("org.springframework.boot") version "3.2.4" apply false
}

apply(plugin = "io.spring.dependency-management")
group = "org.ISKor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":Service")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    implementation("org.hibernate:hibernate-core:6.1.7.Final")
    implementation("org.postgresql:postgresql:42.5.4")
    testImplementation("com.h2database:h2:1.3.148")

    api("org.springframework.boot:spring-boot-starter:3.2.4")
    api("org.springframework.boot:spring-boot-starter-data-jpa:3.2.4")
    api("org.springframework.boot:spring-boot-starter-validation:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}