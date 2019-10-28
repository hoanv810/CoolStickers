package github.hoanv810.icon.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * @author hoanv
 * @since 10/1/18
 */
class NoSwipeableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}