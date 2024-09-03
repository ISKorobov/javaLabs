plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4" apply false
}
apply(plugin = "io.spring.dependency-management")

group = "org.ISKor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.hibernate:hibernate-core:6.1.7.Final")
    testImplementation("com.h2database:h2:1.3.148")

    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
}

tasks.test {
    useJUnitPlatform()
}