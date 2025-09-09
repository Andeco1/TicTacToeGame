plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "entertainment.FI"
version = "1"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("entertainment.fi.tictactoegame")
    mainClass.set("entertainment.fi.tictactoegame.Main")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(file("/Users/yan/MIREA/Testing/TicTacToeGame/build/jlink-image.zip")) // опционально, архив runtime-image
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))

    launcher {
        name = "TicTacToeGame"
    }

    jpackage {
        installerType = when {
            org.gradle.internal.os.OperatingSystem.current().isWindows -> "exe"
            org.gradle.internal.os.OperatingSystem.current().isMacOsX -> "dmg"
            else -> "pkg"
        }
        installerOptions.addAll(
            listOf(
                "--app-version", project.version.toString(),
                "--vendor", "Yan Vasylchenko",
                "--name", "TicTacToeGame",
                "--icon", "src/main/resources/img.png" // для macOS, для Windows можно .ico
            )
        )
    }
}
