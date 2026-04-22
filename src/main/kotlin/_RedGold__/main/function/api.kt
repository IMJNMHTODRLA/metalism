package _RedGold__.main.function

import org.bukkit.plugin.java.JavaPlugin
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.text.NumberFormat
import java.util.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

object api {
    fun WriteSave(plugin: JavaPlugin, path: String, fileName: String, content: String) {
        try {
            val filePath = plugin.dataFolder.toPath().resolve(path).resolve(fileName)

            if (!Files.exists(filePath.parent)) {
                Files.createDirectories(filePath.parent)
            }

            Files.write(filePath, content.toByteArray(StandardCharsets.UTF_8))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun byteSave(plugin: JavaPlugin, path: String, fileName: String, bytes: ByteArray, isPrint: Boolean = false): Boolean {
        try {
            val filePath = plugin.dataFolder.toPath().resolve(path).resolve(fileName)

            Files.write(filePath, bytes, StandardOpenOption.CREATE_NEW)
            return true
        } catch (e: IOException) {
            if (isPrint) e.printStackTrace()
            return false
        }
    }

    fun isFileExists(plugin: JavaPlugin, path: String, fileName: String): Boolean {
        val filePath = plugin.dataFolder.toPath().resolve(path).resolve(fileName)
        return Files.exists(filePath) && Files.isRegularFile(filePath)
    }

    fun readFileContents(plugin: JavaPlugin, path: String, fileName: String): String {
        val file = plugin.dataFolder.resolve(path).resolve(fileName)
        return if (file.exists()) file.readText(Charsets.UTF_8) else ""
    }

    fun readFileContentsOrNull(plugin: JavaPlugin, path: String, fileName: String): String? {
        return try {
            val filePath = plugin.dataFolder.toPath().resolve(path).resolve(fileName)
            val bytes = Files.readAllBytes(filePath)
            String(bytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            null
        }
    }

    fun readGzipByte(plugin: JavaPlugin, path: String, fileName: String): ByteArray {
        val filePath = plugin.dataFolder.toPath().resolve(path).resolve(fileName)

        GZIPInputStream(ByteArrayInputStream(Files.readAllBytes(filePath))).use { gis ->
            return gis.readBytes()
        }
    }

    fun saveGzipByte(plugin: JavaPlugin, path: String, fileName: String, dataToCompress: ByteArray) {
        val file = plugin.dataFolder.toPath().resolve(path).resolve(fileName).toFile()

        file.parentFile.mkdirs()

        FileOutputStream(file).use { fos ->
            GZIPOutputStream(fos).use { gzip -> gzip.write(dataToCompress) }
        }
    }

    fun deleteFile(plugin: JavaPlugin, path: String, fileName: String) {
        try {
            val filePath = plugin.dataFolder.toPath().resolve(path).resolve(fileName)

            Files.delete(filePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun Long.toFormat(): String {
        return NumberFormat.getInstance().format(this)
    }

    fun Int.toFormat(): String {
        return NumberFormat.getInstance().format(this)
    }

    fun String.toUuid(): UUID {
        return try {
            if (this.contains("-")) UUID.fromString(this)
            else UUID.fromString(this.replaceFirst(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(),
                "$1-$2-$3-$4-$5"
            ))
        } catch (e: IllegalArgumentException) {
            UUID.randomUUID()
        }
    }

    fun String.toUUIDOrNull(): UUID? {
        return try {
            if (this.contains("-")) UUID.fromString(this)
            else UUID.fromString(this.replaceFirst(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(),
                "$1-$2-$3-$4-$5"
            ))
        } catch (e: IllegalArgumentException) {
            return null
        }
    }

    fun Float.toFormat(number: Int): String {
        val formatter = NumberFormat.getInstance()

        formatter.minimumFractionDigits = number
        formatter.maximumFractionDigits = number

        return formatter.format(this)
    }

    fun Double.toFormat(number: Int): String {
        val formatter = NumberFormat.getInstance()

        formatter.minimumFractionDigits = number
        formatter.maximumFractionDigits = number

        return formatter.format(this)
    }

    fun Long.toTimeFormat(): String {
        val minutes = this / 60
        val seconds = this % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}