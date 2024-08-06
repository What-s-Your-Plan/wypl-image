plugins {
    id("java")
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.epages.restdocs-api-spec") version "0.18.4"
    id("jacoco")
}

val projectVersion = "0.0.1"
group = "com.wypl"
version = projectVersion

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

jacoco {
    toolVersion = "0.8.11"
}

val snippetsDir by extra { file("build/generated-snippets") }

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // AWS
    implementation("io.awspring.cloud:spring-cloud-starter-aws-secrets-manager-config:2.4.4")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // Test
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.4")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.mockito:mockito-core")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}


/* Jacoco Start */
tasks.withType<JacocoReport> {
    reports {
        html.required.set(true)
        xml.required.set(true)
        html.outputLocation.set(file("reports/jacoco/index.xml"))
    }

    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                    "**/*Application*",
                    "**/*Configuration*",
                    "**/*Request*",
                    "**/*Response*",
                    "**/common/**",
                    "**/config/**",
                    "**/data/**",
                    "**/exception/**",
                    "**/properties/**"
                )
            }
        })
    )
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            enabled = true
            element = "CLASS"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = 0.00.toBigDecimal()
            }

            excludes = listOf(
                "**/*Application*",
                "**/*Configuration*",
                "**/*Request*",
                "**/*Response*",
                "**/common/**",
                "**/config/**",
                "**/data/**",
                "**/exception/**",
                "**/properties/**"
            )
        }
    }
}
/* Jacoco End */

/* API Docs Start */
openapi3 {
    this.setServer("http://127.0.0.1:8080")
    title = "What's Your Plan! Image Server API Docs"
    description = "What's Your Plan! Image Server API Description"
    version = projectVersion
    format = "yaml"
}

tasks.register<Copy>("copyOasToSwagger") {
    dependsOn(tasks.named("openapi3"))

    delete(file("src/main/resources/static/swagger-ui/openapi3.yaml"))
    from("build/api-spec/openapi3.yaml")
    into("src/main/resources/static/swagger-ui/")
}
/* API Docs End */