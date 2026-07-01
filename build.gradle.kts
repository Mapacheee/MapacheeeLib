plugins {
    id("java")
    id("java-library")
    id("com.gradleup.shadow") version "9.2.2"
    `maven-publish`
}

group = "me.mapacheee"
version = "1.0.1"

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
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    api("com.thewinterframework:paper:2.1.1")
    annotationProcessor("com.thewinterframework:paper:2.1.1")
    api("com.thewinterframework:configuration:2.0.0")
    annotationProcessor("com.thewinterframework:configuration:2.0.0")
    api("com.thewinterframework:command:2.0.0")
    annotationProcessor("com.thewinterframework:command:2.0.0")

    implementation("org.spongepowered:configurate-core:4.2.0")
    implementation("org.spongepowered:configurate-yaml:4.2.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.3")
    api("org.incendo:cloud-paper:2.0.0-beta.17")
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

// Make resources available on the compile classpath so Winter's annotation processor
// can find META-INF/winter/exposed-classes.txt during annotation processing
val generatedResourcesDir = layout.buildDirectory.dir("generated/classpath/winter").get().asFile

tasks.register("generateWinterExposedClasses") {
    doLast {
        val metaInfDir = File(generatedResourcesDir, "META-INF/winter")
        metaInfDir.mkdirs()
        val file = File(metaInfDir, "exposed-classes.txt")
        file.createNewFile()
    }
}

tasks.compileJava {
    dependsOn("generateWinterExposedClasses")
    classpath = classpath + files(generatedResourcesDir)
}

tasks.shadowJar {
    archiveClassifier.set("")
    mergeServiceFiles()
    relocate("com.github.benmanes.caffeine", "me.mapacheee.lib.caffeine")
}
