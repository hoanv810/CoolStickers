package github.hoanv810.icon.ui.icons

import android.app.Application
import androidx.lifecycle.MutableLiveData
import github.hoanv810.icon.data.firestore.FirestoreRepo
import github.hoanv810.icon.data.tracking.Analytics
import github.hoanv810.icon.domain.gson.Icon
import github.hoanv810.icon.ui.base.BaseViewModel
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * @author hoanv
 * @since 8/3/18
 */
class IconViewModel @Inject constructor(
        val app: Application,
        private val storageRepo: FirestoreRepo,
        private val analytics: Analytics,
        @Named("io") val io: Scheduler,
        @Named("ui") val ui: Scheduler) : BaseViewModel() {

    val fireIconsData = MutableLiveData<List<Icon>>()
    val downloadPercentage = MutableLiveData<Int>()

    fun loadCollection(directory: String) {

        if (!storageRepo.fileExist(directory)) {
            addDisposable(storageRepo.downloadFile(directory)
                    .subscribeOn(io)
                    .observeOn(ui)
                    .subscribe({
                        downloadPercentage.postValue(it)
                    }, {
                        Timber.e(it, "Error when downloading $directory.zip file")
                        errorStatus.postValue(true)
                    }, {
                        Timber.d("$directory.zip has been downloaded")
                        extractFiles(directory)
                    }))
        } else {
            getFireIcons(directory)
        }
    }

    fun trackViewCollection(directory: String) {
        analytics.trackViewCollection(directory)
    }

    private fun extractFiles(name: String) {
        addDisposable(storageRepo.extractFile(name)
                .subscribeOn(io)
                .observeOn(ui)
                .subscribe({
                    Timber.d("$name.zip has been extracted")
                    getFireIcons(name)
                }, {
                    Timber.e("Error when extracting $name.zip file")
                    errorStatus.postValue(true)
                }))
    }

    private fun getFireIcons(path: String) {
        addDisposable(storageRepo.getFireIcons(path)
                .subscribeOn(io)
                .observeOn(ui)
                .subscribe({
                    fireIconsData.postValue(it)
                }, {
                    Timber.e(it, "Error occurred when loading icons from $path directory")
                    errorStatus.postValue(true)
                }))
    }

}