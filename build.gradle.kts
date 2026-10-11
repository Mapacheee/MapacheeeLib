import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.compile.JavaCompile

plugins {
    java
    `java-library`
    alias(libs.plugins.shadow)
    `maven-publish`
}

base {
    archivesName = "MapacheeeLib"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
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
    // Paper API
    compileOnly(libs.paper.api)

    // Winter Framework
    api(libs.winter.paper)
    annotationProcessor(libs.winter.paper)
    api(libs.winter.configuration)
    annotationProcessor(libs.winter.configuration)
    api(libs.winter.command)
    annotationProcessor(libs.winter.command)

    // Commands & Configuration
    api(libs.cloud.paper)
    api(libs.cloud.annotations)
    api(libs.configurate.core)
    api(libs.configurate.yaml)

    // Utilities & Metrics
    implementation(libs.caffeine)
    implementation(libs.faststats.bukkit)
}

configurations {
    named("apiElements") {
        outgoing.artifacts.clear()
        outgoing.artifact(tasks.shadowJar)
    }
    named("runtimeElements") {
        outgoing.artifacts.clear()
        outgoing.artifact(tasks.shadowJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "MapacheeeLib"
            from(components["java"])
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(25)
    options.compilerArgs.add("-parameters")
}

tasks {
    jar {
        archiveClassifier.set("plain")
    }
    processResources {
        val pluginVersion = project.version.toString()
        inputs.property("version", pluginVersion)
        filesMatching(listOf("plugin.yml", "paper-plugin.yml")) {
            expand("version" to pluginVersion)
        }
    }
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
        relocate("com.github.benmanes.caffeine", "me.mapacheee.lib.caffeine")
        relocate("dev.faststats", "me.mapacheee.lib.faststats")
    }
    build {
        dependsOn(shadowJar)
    }
}
