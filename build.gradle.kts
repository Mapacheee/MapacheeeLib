plugins {
    id("java")
    id("java-library")
    id("com.gradleup.shadow") version "9.2.2"
    `maven-publish`
}

group = "me.mapacheee"
version = "1.0.2"

base {
    archivesName = "MapacheeeLib"
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven("https://central.sonatype.com/repository/maven-snapshots/") {
        name = "sonatype-snapshots"
        mavenContent {
            snapshotsOnly()
        }
    }
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.faststats.dev/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    api("com.thewinterframework:paper:2.1.5")
    annotationProcessor("com.thewinterframework:paper:2.1.5")
    api("com.thewinterframework:configuration:2.0.2")
    annotationProcessor("com.thewinterframework:configuration:2.0.2")
    api("com.thewinterframework:command:2.0.0")
    annotationProcessor("com.thewinterframework:command:2.0.0")

    implementation("org.spongepowered:configurate-core:4.2.0")
    implementation("org.spongepowered:configurate-yaml:4.2.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.3")
    implementation("dev.faststats.metrics:bukkit:0.29.0")
    compileOnly("org.apiguardian:apiguardian-api:1.1.2")
    api("org.incendo:cloud-paper:2.0.0")
    api("org.incendo:cloud-annotations:2.0.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "MapacheeeLib"
            from(components["java"])
        }
    }
}


tasks.processResources {
    val pluginVersion = project.version.toString()
    inputs.property("version", pluginVersion)
    filesMatching(listOf("plugin.yml", "paper-plugin.yml")) {
        expand("version" to pluginVersion)
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    mergeServiceFiles()
    filesMatching("org/incendo/cloud/bukkit/parser/ItemStackParser*.class") {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    relocate("com.github.benmanes.caffeine", "me.mapacheee.lib.caffeine")
    relocate("dev.faststats", "me.mapacheee.lib.faststats")
}

tasks.jar {
    archiveClassifier.set("plain")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

