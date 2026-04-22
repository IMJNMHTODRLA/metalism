package _RedGold__.main.function

import _RedGold__.main.function.api.WriteSave
import _RedGold__.main.function.api.isFileExists
import _RedGold__.main.function.api.readFileContents
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.nio.file.Files
import java.util.*

object Data {
    fun getData(plugin: JavaPlugin, player: Player, rootName: String): String {
        return getDataUuid(plugin, player.uniqueId, rootName)
    }

    fun hasData(plugin: JavaPlugin, player: Player, rootName: String): Boolean {
        return hasDataUuid(plugin, player.uniqueId, rootName)
    }

    fun defData(plugin: JavaPlugin, player: Player, rootName: String, default: Any) {
        defDataUuid(plugin, player.uniqueId, rootName, default)
    }

    fun saveData(plugin: JavaPlugin, player: Player, rootName: String, write: Any) {
        saveDataUuid(plugin, player.uniqueId, rootName, write)
    }






    fun getDataUuid(plugin: JavaPlugin, uuid: UUID, rootName: String): String {
        val strUuid = uuid.toString().replace("-", "")
        return readFileContents(plugin, "${rootName}/${strUuid.substring(0, 2)}", "${strUuid}.data")
    }

    fun hasDataUuid(plugin: JavaPlugin, uuid: UUID, rootName: String): Boolean {
        val strUuid = uuid.toString().replace("-", "")
        return isFileExists(plugin, "${rootName}/${strUuid.substring(0, 2)}", "${strUuid}.data")
    }

    fun defDataUuid(plugin: JavaPlugin, uuid: UUID, rootName: String, default: Any) {
        if (!hasDataUuid(plugin, uuid, rootName)) {
            saveDataUuid(plugin, uuid, rootName, default.toString())
        }
    }

    fun saveDataUuid(plugin: JavaPlugin, uuid: UUID, rootName: String, write: Any) {
        val strUuid = uuid.toString().replace("-", "")
        WriteSave(plugin, "${rootName}/${strUuid.substring(0, 2)}", "${strUuid}.data", write.toString())
    }

    /** 그 경로에 있는 파일 이름 다 보내줌, 근데 -는 안 붙이고 */
    fun allFileName(plugin: JavaPlugin, rootName: String): Set<String> {
        return try {
            Files.walk(plugin.dataFolder.toPath().resolve(rootName), 2).use { stream -> stream
                .filter {Files.isRegularFile(it)}
                .map {it.fileName.toString().substringBeforeLast('.')}
                .collect(java.util.stream.Collectors.toSet())}
        } catch (e: IOException) {
            return emptySet()
        }
    }

    /*
    fun getDataUuidOrNull(plugin: JavaPlugin, uuid: UUID, rootName: String): String? {
        val strUuid = uuid.toString().replace("-", "")
        return readFileContentsOrNull(plugin, "${rootName}/${strUuid.substring(0, 2)}", "${strUuid}.data")
    }
    */
}