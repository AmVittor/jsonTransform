plugins {
    kotlin("jvm") version "1.9.23"
}

group = "br.com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation("org.apache.commons:commons-csv:1.9.0")
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}