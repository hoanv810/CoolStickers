package github.hoanv810.icon.data.firestore

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import github.hoanv810.icon.App
import github.hoanv810.icon.data.utils.FirestoreObservableListerner
import github.hoanv810.icon.domain.gson.Icon
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.util.RestServiceUtils
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.io.File

/**
 * @author hoanv
 * @since 7/27/18
 */
class FirestoreRepoImpl(private val db: FirebaseFirestore,
                        val storage: StorageReference,
                        private val firestoreListener: FirestoreObservableListerner) : FirestoreRepo {

    override fun getFireSets() =
            firestoreListener.listenToValues(db.collection("ic_sets").get(), asIconSets())

    override fun getFireIcons(relativePath: String): Observable<List<Icon>> {
        val absolutePath = App.FILES_DIR + relativePath + File.separator + "setdetails.json"
        val icons = Gson().fromJson<List<Icon>>(RestServiceUtils.getStringFromFile(absolutePath),
                object : TypeToken<List<Icon>>() {}.type)
        return Observable.just(icons)
    }

    override fun downloadFile(fileName: String): Observable<Int> {
        val ref = storage.child("$fileName.zip")
        return firestoreListener.listenToDownload(ref, fileName)
    }

    override fun extractFile(fileName: String) = firestoreListener.listenToExtraction(fileName)

    override fun fileExist(fileName: String): Boolean {
        val file = File(App.FILES_DIR + fileName)
        return file.exists()
    }

    private fun asIconSets(): Function<List<DocumentSnapshot>, List<Collection>> {
        return Function { snapshots ->
            val sets = mutableListOf<Collection>()
            for (document in snapshots) {
                document.toObject(Collection::class.java)?.let { sets.add(it) }
            }
            return@Function sets
        }
    }
}