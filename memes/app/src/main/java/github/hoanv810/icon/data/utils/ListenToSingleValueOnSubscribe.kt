package github.hoanv810.icon.data.utils

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Function

/**
 * @author hoanv
 * @since 7/27/18
 */
class ListenToSingleValueOnSubscriber<T>(private val docRef: DocumentReference,
                                         private val marshaller: Function<DocumentSnapshot, T>)
    : ObservableOnSubscribe<T> {

    override fun subscribe(emitter: ObservableEmitter<T>) {

        docRef.get().addOnCompleteListener { task ->
            if (!emitter.isDisposed) {
                if (task.isSuccessful) {
                    emitter.onNext(marshaller.apply(task.result!!))
                    emitter.onComplete()
                } else {
                    emitter.onError(Exception(task.exception))
                }
            }
        }
    }
}