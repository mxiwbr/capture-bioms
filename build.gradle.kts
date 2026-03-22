plugins {
    id("java")
    id("com.gradleup.shadow") version "9.3.1"
}

group = "io.github.mxiwbr"
version = "1.0.0"

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    mavenCentral()
}

dependencies {
    // bStats
    implementation("org.bstats:bstats-bukkit:3.2.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // compile only if all features are available in 1.21.5 (plugin works 1.21.5 and newer)
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
    implementation("org.apache.commons:commons-text:1.10.0")
    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    // Gson implementation for plugin update service
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.shadowJar {
    configurations = project.configurations.runtimeClasspath.map { setOf(it) }

    // Relocate bStats into the plugin's package to avoid conflicts with other
    // plugins using bStats
    relocate("org.bstats", project.group.toString())
}