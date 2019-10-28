package github.hoanv810.icon.data.utils

import github.hoanv810.icon.App
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*

/**
 * @author hoanv
 * @since 2/1/17
 */

object FileManager {

    fun saveFile(body: ResponseBody, fileName: String): Boolean {
        try {
            val f = File(App.FILES_DIR + File.separator + fileName)
            Timber.d("File path: %s", f.path)

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(f)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) break

                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Timber.d("File download: $fileSizeDownloaded of $fileSize")
                }

                outputStream.flush()
                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            return false
        }

    }

    fun deleteFile(filesDir: String, fileName: String): Boolean {
        val file = File(filesDir, fileName)
        return file.delete()
    }
}
