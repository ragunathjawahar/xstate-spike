import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    application
}

group = "io.redgreen"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.21")
    implementation("com.spotify.mobius:mobius-core:1.5.3")
    implementation("com.spotify.mobius:mobius-rx2:1.5.3")
    implementation("com.spotify.mobius:mobius-extras:1.5.3")
    implementation("com.google.code.gson:gson:2.8.6")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")

    testImplementation("com.approvaltests:approvaltests:9.5.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClassName = "MainKt"
}
