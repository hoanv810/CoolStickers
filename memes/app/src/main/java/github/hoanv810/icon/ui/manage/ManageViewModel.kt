package github.hoanv810.icon.ui.manage

import android.app.Application
import androidx.lifecycle.MutableLiveData
import github.hoanv810.icon.data.api.ApiService
import github.hoanv810.icon.data.firestore.CollectionManager
import github.hoanv810.icon.data.tracking.Analytics
import github.hoanv810.icon.domain.event.InvalidateCollection
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.ui.base.BaseViewModel
import io.reactivex.Scheduler
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * @author hoanv
 * @since 10/25/18
 */
class ManageViewModel @Inject constructor(
        private val app: Application,
        private val api: ApiService,
        private val analytics: Analytics,
        @Named("io") val io: Scheduler,
        @Named("ui") val ui: Scheduler) : BaseViewModel() {

    val setsData = MutableLiveData<List<Collection>>()

    init {
        loadCollections()
        trackManageCollection()
    }

    private fun loadCollections() {
        addDisposable(api.getCollections()
                .observeOn(ui)
                .subscribeOn(io)
                .doOnSubscribe { loadingStatus.postValue(true) }
                .doOnComplete { loadingStatus.postValue(false) }
                .subscribe({
                    val settingOrder = CollectionManager.getVisibleCollections(app)
                    if (settingOrder == null) {
                        setsData.postValue(it)
                        return@subscribe
                    }

                    setsData.postValue(CollectionManager.sortCollectionsByOrder(settingOrder, it))
                }, {
                    Timber.e(it)
                }))
    }

    fun saveCollectionOrder(ids: List<Int>) {
        CollectionManager.saveVisibleCollections(app, ids)

        EventBus.getDefault().post(InvalidateCollection())
    }

    fun hideCollection(id: Int){
        CollectionManager.hideCollection(app, id)
    }

    fun trackReorderCollection(directory: String) {
        analytics.trackReorderCollection(directory)
    }

    fun trackHideCollection(directory: String) {
        analytics.trackHideCollection(directory)
    }

    private fun trackManageCollection() {
        analytics.trackEditCollection()
    }
}