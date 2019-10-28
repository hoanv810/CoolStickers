package github.hoanv810.shared.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager

/**
 * @author hoanv
 * @since 8/1/17.
 */
object DisplayUtils {

    private val TAG = DisplayUtils::class.java.name
    private const val MAX_VIDEO_IMAGE_WIDTH = 1280

    var longLengthDisplay = false
    var displaySize = Point()
    var imageVideoSize = Point()
    var imageWorkoutSize = Point()
    var displayMetrics = DisplayMetrics()
    var density = 1f

    /**
     * Check screen resolution
     *
     * @param context          the application context
     * @param newConfiguration
     */
    fun checkDisplaySize(context: Context) {
        try {

            density = context.resources.displayMetrics.density
            val configuration: Configuration = context.resources.configuration

            val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            if (display != null) {
                display.getMetrics(displayMetrics)
                display.getSize(displaySize)
            }
            if (configuration.screenWidthDp != Configuration.SCREEN_WIDTH_DP_UNDEFINED) {
                val newSize = Math.ceil((configuration.screenWidthDp * density).toDouble()).toInt()
                if (Math.abs(displaySize.x - newSize) > 3) {
                    displaySize.x = newSize
                }
            }
            if (configuration.screenHeightDp != Configuration.SCREEN_HEIGHT_DP_UNDEFINED) {
                val newSize = Math.ceil((configuration.screenHeightDp * density).toDouble()).toInt()
                if (Math.abs(displaySize.y - newSize) > 3) {
                    displaySize.y = newSize
                }
            }

            val width = displaySize.x / 3 * 2 / 2

            imageVideoSize.x = if (width > MAX_VIDEO_IMAGE_WIDTH) MAX_VIDEO_IMAGE_WIDTH else width
            imageVideoSize.y = imageVideoSize.x / 16 * 9
            imageWorkoutSize.x = displaySize.x
            imageWorkoutSize.y = imageWorkoutSize.x / 16 * 9

            val ratio = displaySize.y.toFloat() / displaySize.x.toFloat()
            Log.d(TAG, "Ratio height/width=" + ratio)
            longLengthDisplay = ratio >= 1.9

            Log.d(TAG, String.format("Image message size = %d, %d", imageVideoSize.x, imageVideoSize.y))
            Log.d(TAG, String.format("Display size: %d, %d", displaySize.x, displaySize.y))
        } catch (e: Exception) {
            Log.w(TAG, e)
        }

    }
}
