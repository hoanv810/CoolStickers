package github.hoanv810.icon.data.firestore

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.domain.gson.Icon

/**
 * @author hoanv
 * @since 11/2/18
 */
object CollectionManager {

    private const val MAX_RECENTLY_USED_ICON = 24

    private const val KEY_COLLECTION_ORDER = "collection_ids"
    private const val KEY_HIDDEN_COLLECTIONS = "collection_ids_hidden"
    private const val KEY_RECENTLY_USED_ICONS = "recently_used_icons"

    private fun getPrefs(context: Context): SharedPreferences =
            context.getSharedPreferences("FireSetPreference", Context.MODE_PRIVATE)

    fun saveVisibleCollections(context: Context, ids: List<Int>) {
        getPrefs(context).edit {
            putString(KEY_COLLECTION_ORDER, ids.joinToString(",", "", ""))
        }
    }

    fun getVisibleCollections(context: Context): List<Int>? {
        val order = getPrefs(context).getString(KEY_COLLECTION_ORDER, null)
        if (!order.isNullOrEmpty()) {
            return order.split(",").map { it.toInt() }
        }

        return null
    }

    fun restoreCollection(context: Context, id: Int) {
        val collections = getVisibleCollections(context)?.toMutableList()
        collections?.add(id)
        collections?.let { saveVisibleCollections(context, it) }

        val hiddenCollection = getHiddenCollection(context)
        hiddenCollection.remove(id.toString())
        getPrefs(context).edit { putStringSet(KEY_HIDDEN_COLLECTIONS, hiddenCollection) }
    }

    fun sortCollectionsByOrder(order: List<Int>, sets: List<Collection>): List<Collection> {
        val mutSets = sets.toMutableList()
        val orderList = mutableListOf<Collection>()
        val map = hashMapOf<Int, Collection>()

        mutSets.forEach { map[it.id] = it }
        order.forEach {
            map[it]?.let { s -> orderList.add(s) }
            map.remove(it)
        }

        return orderList
    }

    /**
     * @return List id of removed collection
     */
    fun getHiddenCollection(context: Context): MutableSet<String> {
        return getPrefs(context).getStringSet(KEY_HIDDEN_COLLECTIONS, mutableSetOf())!!
    }

    // Put id of hidden collection to the list of hidden collections
    fun hideCollection(context: Context, id: Int) {
        val hiddenCollection = getHiddenCollection(context)
        hiddenCollection.add(id.toString())

        getPrefs(context).edit { putStringSet(KEY_HIDDEN_COLLECTIONS, hiddenCollection) }
    }

    fun isHiddenCollection(context: Context, id: Int): Boolean {
        return getHiddenCollection(context).contains(id.toString())
    }

    /**
     * Add icon to recently used list
     */
    fun addRecentlyIcon(context: Context, icon: Icon) {
        val icons = getRecentlyIcons(context).toMutableList().apply {
            add(0, icon)
            if (size >= MAX_RECENTLY_USED_ICON) {
                for (i in MAX_RECENTLY_USED_ICON..size) {
                    removeAt(i)
                }
            }
        }

        getPrefs(context).edit {
            putString(KEY_RECENTLY_USED_ICONS, Gson().toJson(icons))
        }
    }

    fun getRecentlyIcons(context: Context): List<Icon> {
        val payload = getPrefs(context).getString(KEY_RECENTLY_USED_ICONS, "[]")
        return Gson().fromJson(payload, object : TypeToken<List<Icon>>() {}.type)
    }
}