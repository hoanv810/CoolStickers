package github.hoanv810.icon.ui.view

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import github.hoanv810.icon.R

/**
 * @author hoanv
 * @since 8/6/18
 */
class MarginDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var margin: Int = context.resources.getDimensionPixelSize(R.dimen.list_item_margin)

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.set(margin, margin, margin, margin)
    }
}