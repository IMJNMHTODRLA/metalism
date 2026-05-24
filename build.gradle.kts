plugins {
    kotlin("jvm") version "2.0.0"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "_RedGold__"
version = "1.0.0"

val projName = "main"
val projVersion = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        url = uri("https://repo.aikar.co/content/groups/aikar/")
    }
    maven {
        url = uri("https://repo.maven.apache.org/maven2")
    }
    maven { url = uri("https://repo.lucko.me/") }
    maven { url = uri("https://repo.dmulloy2.net/repository/public/") }
    maven { url = uri("https://repo.codemc.io/repository/maven-public/") }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.classgraph:classgraph:4.8.179")

    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    //compileOnly("net.kyori:adventure-api:4.17.0")
    //compileOnly("net.kyori:adventure-key:4.17.0")

    compileOnly("net.luckperms:api:5.5-SNAPSHOT")

    implementation("com.google.code.gson:gson:2.10.1")//delete

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")//delete

    compileOnly("com.comphenix.protocol:ProtocolLib:5.3.0")//delete

    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.15.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.15.0")

    implementation("org.jetbrains.exposed:exposed-core:0.58.0") //sqlite API
    implementation("org.jetbrains.exposed:exposed-dao:0.58.0") //sqlite API
    implementation("org.jetbrains.exposed:exposed-jdbc:0.58.0") //sqlite API
    implementation("org.xerial:sqlite-jdbc:3.45.1.0") //sqlite API

    implementation("com.zaxxer:HikariCP:5.1.0") //sqlite API

    implementation("com.github.retrooper:packetevents-spigot:2.11.2") //packetevents API
    //implementation("me.chancesd.pvpmanager:pvpmanager-plugin:4.0.9") //PVPManager API
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.21")
    }
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn(tasks.shadowJar)
    finalizedBy("copyPlugin")
    //finalizedBy("proguard")
    //finalizedBy("copyPlugin")
}

tasks.shadowJar {
    archiveFileName.set("$projName-$projVersion.jar") // 기본 jar 이름 그대로
    mergeServiceFiles()       // ACF 관련 서비스 파일 병합

    //re("co.aikar", "libs.acf-paper")
    //exclude("_RedGold__/**")
}

tasks.register<Copy>("copyPlugin") {
    doFirst { println("copying built plugin ...") }

    from("build/libs/$name-$projVersion.jar")
    into("C:/Users/User/OneDrive/바탕 화면/plgins/마크서버테스트용플러그인/testset/VelocitysServer/metalism.mcv.kr@/Main/plugins")

    doLast { println("copied built plugin!") }
    doNotTrackState("Plugin copy task does not produce incremental outputs")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}