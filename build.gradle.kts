plugins {
    java
    `kotlin-dsl`
    `maven-publish`
    id("com.gradleup.shadow") version("8.3.6")
}

group = "me.xemor"
version = "4.1.3"

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven { url = uri("https://jitpack.io/")}
    maven { url = uri("https://mvn-repo.arim.space/lesser-gpl3")}
    maven { url = uri("https://repo.xemor.zip/releases")}
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots"
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("com.fasterxml.jackson.core:jackson-core:2.18.3")
    compileOnly("com.fasterxml.jackson.core:jackson-databind:2.18.3")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.7.0")
    shadow("net.kyori:adventure-platform-bukkit:4.3.3-SNAPSHOT")
    shadow("net.kyori:adventure-text-minimessage:4.17.0")
    shadow("me.xemor:configurationdata:4.3.9")
    shadow("space.arim.morepaperlib:morepaperlib:0.4.3")
    shadow("me.xemor:foliahacks:1.7.4")
    shadow("io.papermc:paperlib:1.0.7")
}
-m
java {
    configurations.shadow.get().dependencies.remove(dependencies.gradleApi())
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
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
    relocate("space.arim.morepaperlib", "me.xemor.skillslibrary2.morepaperlib")
    relocate("me.xemor.foliahacks", "me.xemor.skillslibrary2.foliahacks")
    relocate("io.papermc.paperlib", "me.xemor.skillslibrary2.paperlib")
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