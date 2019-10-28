package github.hoanv810.icon.ui.archive

import android.app.Application
import androidx.lifecycle.MutableLiveData
import github.hoanv810.icon.data.api.ApiService
import github.hoanv810.icon.data.firestore.CollectionManager
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.ui.base.BaseViewModel
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

/**
 * @author hoanv
 * @since 11/7/18
 */
class ArchiveViewModel @Inject constructor(private val app: Application,
                                           private val api: ApiService,
                                           @Named("io") private val io: Scheduler,
                                           @Named("ui") private val ui: Scheduler) : BaseViewModel() {

    val setsData = MutableLiveData<List<Collection>>()

    init {
        loadSets()
    }

    private fun loadSets() {
        addDisposable(api.getCollections()
                .doOnSubscribe { loadingStatus.postValue(true) }
                .doOnComplete { loadingStatus.postValue(false) }
                .doOnError { loadingStatus.postValue(false) }
                .subscribeOn(io)
                .observeOn(ui)
                .subscribe({
                    loadArchivedSets(it)
                }, {

                }))
    }

    private fun loadArchivedSets(sets: List<Collection>) {
        val result = mutableListOf<Collection>()

        val map = HashMap<Int, Collection>()
        sets.forEach { item -> map[item.id] = item }

        val removedIds = CollectionManager.getHiddenCollection(app)
        removedIds.forEach { item ->
            val id = item.toInt()
            map[id]?.let { result.add(it) }
        }

        setsData.postValue(result)
    }

    fun restoreSet(id: Int) {
        CollectionManager.restoreCollection(app, id)
    }
}