plugins {
    java
    `kotlin-dsl`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version("7.1.2")
}

group = "me.xemor"
version = "2.17.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven { url = uri("https://jitpack.io/") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:23.0.0")
    shadow("net.kyori:adventure-text-minimessage:4.11.0")
    shadow("net.kyori:adventure-platform-bukkit:4.1.2")
    shadow("me.xemor:configurationdata:2.0.0-SNAPSHOT")
}

java {
    configurations.shadow.get().dependencies.remove(dependencies.gradleApi())
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.shadowJar {
    minimize()
    relocate("net.kyori", "me.xemor.skillslibrary2.kyori")
    relocate("me.xemor.configurationdata", "me.xemor.skillslibrary2.configurationdata")
    configurations = listOf(project.configurations.shadow.get())
    val folder = System.getenv("pluginFolder")
    destinationDirectory.set(file(folder))
}

tasks.processResources {
    inputs.property("version", rootProject.version)
    filesMatching("plugin.yml") {
        expand("version" to rootProject.version)
    }
}