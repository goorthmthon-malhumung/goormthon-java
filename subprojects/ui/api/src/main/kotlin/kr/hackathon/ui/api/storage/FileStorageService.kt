package kr.hackathon.ui.api.storage

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

@Service
class FileStorageService {

    private val photoDir = Paths.get("photo").toAbsolutePath()
    private val mediaDir = Paths.get("media").toAbsolutePath()

    init {
        Files.createDirectories(photoDir)
        Files.createDirectories(mediaDir)
    }

    fun storePhoto(file: MultipartFile): String {
        val filename = "${UUID.randomUUID()}${extension(file)}"
        Files.copy(file.inputStream, photoDir.resolve(filename))
        return "photo/$filename"
    }

    fun storeMedia(file: MultipartFile): String {
        val filename = "${UUID.randomUUID()}${extension(file)}"
        Files.copy(file.inputStream, mediaDir.resolve(filename))
        return "media/$filename"
    }

    private fun extension(file: MultipartFile): String {
        val original = file.originalFilename ?: return ""
        return if (original.contains(".")) ".${original.substringAfterLast(".")}" else ""
    }
}
