plugins {
    glass(JAVA)
    glass(PUBLISHING)
    glass(SIGNING)
    spotless(GRADLE)
    spotless(JAVA)
}

group = "top.nustar.nustarcorebridge"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.AZUL)
    }
}

glass {
    release.set(8)

    application {
        sugar {
            enabled.set(true)
        }
    }

    withCopyright()
    withMavenPom()

    withSourcesJar()
    withJavadocJar()

    withInternal()
    withShadow()

    withJUnitTest()
}

repositories {
    mavenLocal()
    aliyun()
    sonatype()
    sonatype(SNAPSHOT)
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "placeholder-api"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
    mavenCentral()
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.spigot.api)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.minecraft.next.spigot)
    compileOnly(fileTree(File(projectDir, "libraries")))

    runtimeOnly(libs.mysql.connector.j)
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    testImplementation(libs.spigot.api)
    testImplementation(libs.minecraft.next.spigot)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

tasks.processResources {
    val props =
        mapOf(
            "version" to project.version,
        )
    filesMatching(listOf("plugin.yml")) {
        expand(props)
    }
}

publishing {
    repositories {
        project(project)
    }
}
