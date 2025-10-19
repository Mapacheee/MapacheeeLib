plugins {
    id("java")
    id("java-library")
    id("com.gradleup.shadow") version "9.2.2"
    `maven-publish`
}

group = "com.thewinterframework"
version = "1.0.0"

base {
    archivesName = "wintercore"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

    api("com.thewinterframework:paper:1.0.4")
    annotationProcessor("com.thewinterframework:paper:1.0.4")
    api("com.thewinterframework:configuration:1.0.2")
    annotationProcessor("com.thewinterframework:configuration:1.0.2")
    api("com.thewinterframework:command:1.0.1")
    annotationProcessor("com.thewinterframework:command:1.0.1")

    implementation("org.spongepowered:configurate-core:4.2.0")
    implementation("org.spongepowered:configurate-yaml:4.2.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
}

//tasks.jar { enabled = false }

//tasks.named("generateMetadataFileForMavenPublication") {
//    dependsOn(tasks.shadowJar)
//}
//tasks.named("publishMavenPublicationToMavenLocal") {
//    dependsOn(tasks.shadowJar)
//}