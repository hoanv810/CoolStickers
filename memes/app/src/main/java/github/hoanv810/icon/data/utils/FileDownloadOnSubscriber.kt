package github.hoanv810.icon.data.utils

import com.google.firebase.storage.StorageReference
import github.hoanv810.icon.App
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import timber.log.Timber
import java.io.File

/**
 * @author hoanv
 * @since 8/3/18
 */
class FileDownloadOnSubscriber(private val ref: StorageReference,
                               private val fileName: String) : ObservableOnSubscribe<Int> {

    override fun subscribe(emitter: ObservableEmitter<Int>) {

        val dirPath = App.FILES_DIR
        val dir = File(dirPath)

        // create fold and sub folders
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val file = File(dirPath + File.separator + "$fileName.zip")
        if (!file.exists()) {
            file.createNewFile()
        }

        ref.getFile(file).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful) {
                    emitter.onComplete()
                } else {
                    emitter.onError(IllegalStateException("Download file ${file.absolutePath} error"))
                }
            }
        }.addOnProgressListener { p0 ->
            p0.let {
                val percent = (100 * it.bytesTransferred) / it.totalByteCount
                emitter.onNext(percent.toInt())
            }
        }.addOnFailureListener{
            Timber.e(it, "Failed to download file ${file.absolutePath}")
            emitter.onError(IllegalStateException(it.message))
        }
    }
}