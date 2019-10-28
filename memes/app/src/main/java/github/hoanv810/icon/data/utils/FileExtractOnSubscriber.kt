package github.hoanv810.icon.data.utils

import github.hoanv810.icon.App
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.model.FileHeader
import java.io.File
import java.lang.Exception

/**
 * @author hoanv
 * @since 8/8/18
 */
class FileExtractOnSubscriber(private val directoryName: String) : ObservableOnSubscribe<File> {

    override fun subscribe(emitter: ObservableEmitter<File>) {
        val zipName = "$directoryName.zip"
        val desFile = App.FILES_DIR
        try {
            val zipFile = ZipFile(App.FILES_DIR + zipName)
            val fileHeaders = zipFile.fileHeaders
            for (h in fileHeaders) {
                zipFile.extractFile(h as FileHeader, desFile)
            }

            FileManager.deleteFile(App.FILES_DIR, zipName)
            if (!emitter.isDisposed) {
                emitter.onNext(File(desFile))
                emitter.onComplete()
            }
        } catch (e: Exception) {
            if (!emitter.isDisposed) emitter.onError(IllegalStateException("Extract $zipName error"))
        }
    }
}