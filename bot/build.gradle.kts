plugins {
    id("java")
}

group = "cycliclang"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":game"))
    
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}