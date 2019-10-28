package github.hoanv810.icon.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import github.hoanv810.icon.R

/**
 * @author hoanv
 * @since 11/2/18
 */
class HorizontalDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val paint = Paint()
    private val margin = context.resources.getDimensionPixelSize(R.dimen.list_item_set_margin)

    init {
        paint.color = Color.parseColor("#eeeeee")
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            c.drawLine(margin.toFloat(), child.y, (margin + child.width).toFloat(), child.y + 1, paint)
        }
    }
}