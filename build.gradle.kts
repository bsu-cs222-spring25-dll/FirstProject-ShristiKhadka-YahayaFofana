plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}
group = "edu.bsu.cs"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21) // Use 11, 17, or 21
    }
}

repositories {
    mavenCentral()
}


dependencies {

    dependencies {
        implementation("org.openjfx:javafx-controls:22")
        implementation("org.openjfx:javafx-fxml:22")
    }
    // JUnit 5 Dependency with BOM (Bill of Materials)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.slf4j:slf4j-nop:2.0.11")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("net.minidev:json-smart:2.5.0")


    // Mockito Dependency for mocking
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation ("org.powermock:powermock-api-mockito2:2.0.9")  // PowerMockito integration with Mockito

    // JSON library (for your project)
    implementation("org.json:json:20210307")

}

tasks.test {
    useJUnitPlatform() // Makes sure to use JUnit 5 platform
}
javafx {
    version = "22"
    modules("javafx.controls", "javafx.fxml","javafx.graphics")
}
application {
    mainClass.set("edu.bsu.cs.GraphicalUserInterface")
}
tasks.test {
    useJUnitPlatform()
}


