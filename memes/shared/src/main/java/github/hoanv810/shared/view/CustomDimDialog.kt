package github.hoanv810.shared.view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.res.ResourcesCompat
import github.hoanv810.shared.R

/**
 * Dialog with custom background dim.
 */
class CustomDimDialog(context: Context?) : AppCompatDialog(context, R.style.Widget_Shared_Dialog) {

    init {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.run {
            // Spread the dialog as large as the screen.
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun setContentView(view: View?) {
        if (view != null) {
            super.setContentView(wrap(view))
        }
    }

    private fun wrap(content: View): View {
        val res = context.resources
        val verticalMargin = res.getDimensionPixelSize(R.dimen.dialog_vertical_margin)
        val horizontalMargin = res.getDimensionPixelSize(R.dimen.dialog_horizontal_margin)
        return FrameLayout(context).apply {
            addView(content, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
                gravity = Gravity.CENTER
            })
            val rect = Rect()
            setOnTouchListener { _, event ->
                when (event.action) {
                // The FrameLayout is technically inside the dialog, but we treat it as outside.
                    MotionEvent.ACTION_DOWN -> {
                        content.getGlobalVisibleRect(rect)
                        if (!rect.contains(event.x.toInt(), event.y.toInt())) {
                            cancel()
                            true
                        } else {
                            false
                        }
                    }
                    else -> {
                        false
                    }
                }
            }
            background = ColorDrawable(ResourcesCompat.getColor(res, R.color.scrim, context.theme))
        }
    }
}
