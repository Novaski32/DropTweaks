DropTweaks (Forge 1.8.9)

Minimal project files to build the mod.

Requirements
- Java 8 (set `JAVA_HOME` to a JDK 8 installation)
- Internet access to download Forge/MCP and Gradle dependencies

Build
1. From this project root run either your system `gradle build` or generate a wrapper with `gradle wrapper` then `./gradlew build`.
2. The built mod jar will be in `build/libs/`.

Notes
- This project targets Forge 1.8.9 using ForgeGradle 2.x. If you prefer a full Forge MDK workspace, download the official MDK and copy these sources into it.
