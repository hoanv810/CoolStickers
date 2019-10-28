package github.hoanv810.icon.ui.collections

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import github.hoanv810.icon.GlideApp
import github.hoanv810.icon.R
import github.hoanv810.icon.data.firestore.CollectionManager
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.ui.icons.IconFragment

/**
 * @author hoanv
 * @since 2/6/17
 */
class SetPagerAdapter(val context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val collections = mutableListOf<Collection>()

    fun setItems(items: List<Collection>) {
        collections.clear()
        collections.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment = IconFragment.newInstance(collections[position], position)

    override fun getCount(): Int = collections.size

    fun getCustomTabView(position: Int): View {
        val tab = LayoutInflater.from(context).inflate(R.layout.item_tab, null)
        val icon = tab.findViewById<ImageView>(R.id.iv_tab)
        GlideApp.with(context)
                .load(collections[position].coverPageUrl)
                .centerCrop()
                .into(icon)
        return tab
    }


    override fun getItemPosition(`object`: Any): Int {
        if (`object` is IconFragment) {
            val pos = `object`.position
            val idAtPosition = collections[pos].id

            if (idAtPosition != `object`.collection.id) return POSITION_NONE
        }

        return POSITION_UNCHANGED
    }
}
