package github.hoanv810.icon.ui.share

import android.app.Application
import android.content.Context
import github.hoanv810.icon.data.tracking.Analytics
import github.hoanv810.icon.domain.gson.Icon
import github.hoanv810.icon.ui.base.BaseViewModel
import github.hoanv810.icon.util.FileUtils
import javax.inject.Inject

/**
 * @author hoanv
 * @since 8/31/18
 */
class ShareDialogViewModel @Inject constructor(
        val app: Application,
        val analytics: Analytics
) : BaseViewModel() {

    fun shareImage(context: Context, icon: Icon, type: FileUtils.ShareTarget) {
        FileUtils.shareStreamImage(context, icon.imagePath(), type)

        analytics.trackUseIcon(icon.image)
        analytics.trackShareTarget(type.name)
        analytics.trackUseCollection(icon.image.split("/")[0], icon.image)
    }
}