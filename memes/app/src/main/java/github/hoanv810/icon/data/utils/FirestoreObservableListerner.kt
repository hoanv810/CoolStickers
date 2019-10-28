package github.hoanv810.icon.data.utils

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.io.File

/**
 * @author hoanv
 * @since 7/27/18
 */
class FirestoreObservableListerner {

    fun <T> listenToSingleValue(docRef: DocumentReference, marshaller: Function<DocumentSnapshot, T>): Observable<T> {
        return Observable.create(ListenToSingleValueOnSubscriber(docRef, marshaller))
    }

    fun <T> listenToValues(query: Task<QuerySnapshot>, marshaller: Function<List<DocumentSnapshot>, T>): Observable<T> {
        return Observable.create(ListenToValuesOnSubscriber(query, marshaller))
    }

    fun listenToDownload(storageReference: StorageReference, path: String): Observable<Int> {
        return Observable.create(FileDownloadOnSubscriber(storageReference, path))
    }

    fun listenToExtraction(name: String): Observable<File>{
        return Observable.create(FileExtractOnSubscriber(name))
    }
}