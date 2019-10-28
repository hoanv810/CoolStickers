package github.hoanv810.icon.ui.collections

import android.app.Application
import androidx.lifecycle.MutableLiveData
import github.hoanv810.icon.data.api.ApiService
import github.hoanv810.icon.data.firestore.CollectionManager
import github.hoanv810.icon.data.tracking.Analytics
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.ui.base.BaseViewModel
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * @author hoanv
 * @since 1/22/17
 */
class SetViewModel @Inject constructor(
        private val app: Application,
        private val api: ApiService,
        private val analytics: Analytics,
        @Named("io") val io: Scheduler,
        @Named("ui") val ui: Scheduler) : BaseViewModel() {

    val setsData = MutableLiveData<List<Collection>>()

    fun loadCollections() {
        addDisposable(api.getCollections()
                .doOnSubscribe { loadingStatus.postValue(true) }
                .doOnComplete { loadingStatus.postValue(false) }
                .subscribeOn(io)
                .observeOn(ui)
                .subscribe({
                    val collectionOrder = CollectionManager.getVisibleCollections(app)
                    if (collectionOrder == null) {
                        setsData.postValue(it)
                        return@subscribe
                    }

                    setsData.postValue(CollectionManager.sortCollectionsByOrder(collectionOrder, it))
                }, { e ->
                    Timber.e(e, "Failed to load FireStore icon sets")
                    errorStatus.postValue(true)
                }))
    }

    fun invalidateCollection() {
        setsData.value?.let { collection ->
            CollectionManager.getVisibleCollections(app)?.let { ids ->
                setsData.postValue(CollectionManager.sortCollectionsByOrder(ids, collection))
            }
        }
    }

    fun trackInvitePeople() {
        analytics.trackInvitePeople()
    }

    fun trackRateAppClick() {
        analytics.trackRateAppClick()
    }
}
