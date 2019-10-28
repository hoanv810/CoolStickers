package github.hoanv810.icon.data.firestore

import github.hoanv810.icon.domain.gson.Icon
import github.hoanv810.icon.domain.gson.Collection
import io.reactivex.Observable
import java.io.File

/**
 * @author hoanv
 * @since 7/26/18
 */
interface FirestoreRepo {

    fun getFireSets(): Observable<List<Collection>>

    /**
     * @param relativePath to the icon directory
     */
    fun getFireIcons(relativePath: String): Observable<List<Icon>>

    fun downloadFile(fileName: String): Observable<Int>

    fun extractFile(fileName: String): Observable<File>

    fun fileExist(fileName: String): Boolean
}