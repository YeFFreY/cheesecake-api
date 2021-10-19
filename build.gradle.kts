plugins {
    id("groovy") 
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "2.0.6"
    id("nu.studer.jooq") version "6.0.1"
}

version = "0.1"
group = "org.yeffrey"

repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("org.yeffrey.*")
    }
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.beanvalidation:micronaut-hibernate-validator")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.liquibase:micronaut-liquibase")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.security:micronaut-security-session")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.sql:micronaut-jooq")
    implementation("javax.annotation:javax.annotation-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.postgresql:postgresql")
    implementation("io.micronaut:micronaut-validation")
    implementation("org.slf4j:jul-to-slf4j:1.7.32") // Because liquibase use JUL for logging, this allows to get logging format same as micronaut https://micronaut-projects.github.io/micronaut-liquibase/latest/guide/index.html

    jooqGenerator("org.postgresql:postgresql:42.2.23")
    implementation("org.springframework.security:spring-security-crypto:5.5.2")
    implementation("org.bouncycastle:bcprov-jdk15on:1.69")
    implementation("commons-logging:commons-logging:1.2")

    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:spock")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("com.github.javafaker:javafaker:1.0.2")

}


application {
    mainClass.set("org.yeffrey.Application")
}
java {
    sourceCompatibility = JavaVersion.toVersion("16")
    targetCompatibility = JavaVersion.toVersion("16")
}

jooq {
    version.set("3.15.1")
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/cheesecake_dev"
                    user = "cheesecaker"
                    password = "cheesecaker"
                }
                generator.apply {
                    target.apply {
                        packageName = "org.yeffrey.cheesecake"
                        directory = "src/generated/jooq"
                    }
                    generate.apply {
                        isValidationAnnotations = true
                        isFluentSetters = true
                    }
                    database.apply {
                        inputSchema = "public"
                        excludes = "databasechangelog|databasechangeloglock"
                    }
                }
            }
        }
    }
}


