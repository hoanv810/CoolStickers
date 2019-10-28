package github.hoanv810.icon.data.tracking

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * @author hoanv
 * @since 10/2/18
 */
class IconFirebaseAnalytics(private val analytics: FirebaseAnalytics) : Analytics {

    override fun trackReorderCollection(directory: String) {
        analytics.logEvent(EVENT_REORDER_COLLECTION, Bundle().apply {
            putString(PARAM_DIRECTORY, directory)
        })
    }

    override fun trackHideCollection(directory: String) {
        analytics.logEvent(EVENT_REMOVE_COLLECTION, Bundle().apply {
            putString(PARAM_DIRECTORY, directory)
        })
    }

    override fun trackEditCollection() {
        analytics.logEvent(EVENT_EDIT_COLLECTION, null)
    }

    override fun trackUseIcon(image: String) {
        analytics.logEvent(EVENT_USE_ICON, Bundle().apply {
            putString(PARAM_ICON_IMAGE, image)
        })
    }

    override fun trackUseCollection(directory: String, image: String) {
        analytics.logEvent(EVENT_USE_COLLECTION, Bundle().apply {
            putString(PARAM_DIRECTORY, directory)
            putString(PARAM_ICON_IMAGE, image)
        })
    }

    override fun trackViewCollection(directory: String) {
        analytics.logEvent(EVENT_VIEW_COLLECTION, Bundle().apply {
            putString(PARAM_DIRECTORY, directory)
        })
    }

    override fun trackRateAppClick() {
        analytics.logEvent(EVENT_RATE_APP, null)
    }

    override fun trackInvitePeople() {
        analytics.logEvent(EVENT_INVITE_PEOPLE, null)
    }

    override fun trackShareTarget(target: String) {
        analytics.logEvent(EVENT_SHARE_TARGET, Bundle().apply {
            putString(PARAM_SHARE_TARGET, target)
        })
    }

    companion object {

        const val PARAM_ICON_IMAGE = "icon_image"
        const val PARAM_ID = "sticker_id"
        const val PARAM_TITLE = "sticker_title"
        const val PARAM_DIRECTORY = "sticker_directory"
        const val PARAM_ENABLED = "sticker_enabled"
        const val PARAM_SHARE_TARGET = "share_target"

        const val EVENT_USE_ICON = "use_icon"
        const val EVENT_USE_COLLECTION = "use_collection"
        const val EVENT_VIEW_COLLECTION = "view_collection"
        const val EVENT_EDIT_COLLECTION = "edit_collection"
        const val EVENT_REORDER_COLLECTION = "reorder_collection"
        const val EVENT_REMOVE_COLLECTION = "remove_collection"
        const val EVENT_RATE_APP = "rate_app"
        const val EVENT_INVITE_PEOPLE = "invite_people"
        const val EVENT_SHARE_TARGET = "share_target"
    }
}