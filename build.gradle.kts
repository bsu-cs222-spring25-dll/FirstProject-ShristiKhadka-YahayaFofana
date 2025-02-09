plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5 Dependency with BOM (Bill of Materials)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Mockito Dependency for mocking
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation ("org.powermock:powermock-api-mockito2:2.0.9")  // PowerMockito integration with Mockito
    testImplementation ("org.powermock:powermock-module-junit4:2.0.9")  // PowerMockito for JUnit 4

    // JSON library (for your project)
    implementation("org.json:json:20210307")
    testImplementation("junit:junit:4.13")
}

tasks.test {
    useJUnitPlatform() // Makes sure to use JUnit 5 platform
}
