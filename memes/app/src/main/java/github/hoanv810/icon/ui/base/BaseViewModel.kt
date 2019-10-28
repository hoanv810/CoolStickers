package github.hoanv810.icon.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author hoanv
 * @since 4/6/18.
 */
abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    val loadingStatus = MutableLiveData<Boolean>()
    val errorStatus = MutableLiveData<Boolean>()

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}