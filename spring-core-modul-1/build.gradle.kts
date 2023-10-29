plugins {
    id("java")

}
tasks.jar {

    manifest {

        attributes["Main-Class"] = "org.example.Main"

    }

}
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    }

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.springframework:spring-context:6.0.8")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.yaml:snakeyaml:2.0")

}

tasks.test {
    useJUnitPlatform()
}
