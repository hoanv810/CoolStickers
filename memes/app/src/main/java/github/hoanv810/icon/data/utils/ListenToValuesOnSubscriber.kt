package github.hoanv810.icon.data.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Function

/**
 * @author hoanv
 * @since 7/27/18
 */
class ListenToValuesOnSubscriber<T>(private val query: Task<QuerySnapshot>,
                                    private val marshaller: Function<List<DocumentSnapshot>, T>)
    : ObservableOnSubscribe<T> {

    override fun subscribe(emitter: ObservableEmitter<T>) {

        query.addOnCompleteListener { task ->
            if (!emitter.isDisposed) {
                if (task.isSuccessful) {
                    task.result?.let { emitter.onNext(marshaller.apply(it.documents)) }
                    emitter.onComplete()
                } else {
                    emitter.onError(Exception(task.exception))
                }
            }
        }
    }
}