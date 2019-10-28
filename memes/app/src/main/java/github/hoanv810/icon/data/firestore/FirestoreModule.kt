package github.hoanv810.icon.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import github.hoanv810.icon.data.utils.FirestoreObservableListerner
import javax.inject.Singleton

/**
 * @author hoanv
 * @since 7/26/18
 */
@Module
class FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        FirebaseFirestore.setLoggingEnabled(false)
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestoreObservableListener(): FirestoreObservableListerner {
        return FirestoreObservableListerner()
    }

    @Provides
    @Singleton
    fun provideFirestoreRepo(firestore: FirebaseFirestore,
                             storageReference: StorageReference,
                             firestoreListener: FirestoreObservableListerner): FirestoreRepo {
        return FirestoreRepoImpl(firestore, storageReference, firestoreListener)
    }

    @Provides
    @Singleton
    fun provideStorage(): StorageReference {
        return FirebaseStorage.getInstance(STORAGE_PREFIX).reference
    }

    companion object {
        const val STORAGE_PREFIX = "gs://iconstore-c0d06.appspot.com"
    }
}