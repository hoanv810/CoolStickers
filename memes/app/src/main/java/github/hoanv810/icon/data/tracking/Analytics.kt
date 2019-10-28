package github.hoanv810.icon.data.tracking

/**
 * @author hoanv
 * @since 10/2/18
 */
interface Analytics {

    fun trackUseIcon(image: String)

    fun trackUseCollection(directory: String, image: String)

    fun trackViewCollection(directory: String)

    fun trackEditCollection()

    fun trackReorderCollection(directory: String)

    fun trackHideCollection(directory: String)

    fun trackRateAppClick()

    fun trackInvitePeople()

    fun trackShareTarget(target: String)

}