plugins {
    id("java")
    id("java-library")
    id("com.gradleup.shadow") version "9.2.2"
    `maven-publish`
}

group = "me.mapacheee"
version = "1.0.0"

base {
    archivesName = "MapacheeeLib"
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    api("com.thewinterframework:paper:1.0.6")
    annotationProcessor("com.thewinterframework:paper:1.0.6")
    api("com.thewinterframework:configuration:1.0.4")
    annotationProcessor("com.thewinterframework:configuration:1.0.4")
    api("com.thewinterframework:command:1.0.1")
    annotationProcessor("com.thewinterframework:command:1.0.1")

    implementation("org.spongepowered:configurate-core:4.2.0")
    implementation("org.spongepowered:configurate-yaml:4.2.0")
    //api("org.incendo:cloud-paper:2.0.0-beta.10")
    //api("org.incendo:cloud-annotations:2.0.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "MapacheeeLib"
            from(components["java"])
        }
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    mergeServiceFiles()
}
