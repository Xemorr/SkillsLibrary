plugins {
    java
    `kotlin-dsl`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version("8.1.1")
}

group = "me.xemor"
version = "2.24.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven { url = uri("https://jitpack.io/")}
    maven { url = uri("https://repo.xemor.zip/releases")}
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:23.0.0")
    shadow("net.kyori:adventure-platform-bukkit:4.3.3-SNAPSHOT")
    shadow("net.kyori:adventure-text-minimessage:4.17.0")
    shadow("me.xemor:configurationdata:3.5.1-SNAPSHOT")
    shadow("me.creeves:ParticlesLibrary:1.1-SNAPSHOT")
}

java {
    configurations.shadow.get().dependencies.remove(dependencies.gradleApi())
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

publishing {
    repositories {
        maven {
            name = "xemorReleases"
            url = uri("https://repo.xemor.zip/releases")
            credentials(PasswordCredentials::class)
            authentication {
                isAllowInsecureProtocol = true
                create<BasicAuthentication>("basic")
            }
        }

        maven {
            name = "xemorSnapshots"
            url = uri("https://repo.xemor.zip/snapshots")
            credentials(PasswordCredentials::class)
            authentication {
                isAllowInsecureProtocol = true
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = rootProject.group.toString()
            artifactId = rootProject.name
            version = rootProject.version.toString()
            from(project.components["java"])
        }
    }
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