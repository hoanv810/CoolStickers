package github.hoanv810.icon.ui.icons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import github.hoanv810.icon.GlideApp
import github.hoanv810.icon.R
import github.hoanv810.icon.domain.gson.Icon
import github.hoanv810.icon.util.OnItemClickListener

/**
 * @author hoanv
 * @since 1/23/17
 */
class IconAdapter : RecyclerView.Adapter<IconAdapter.IconVH>() {

    private val icons = mutableListOf<Icon>()
    var itemClickListener: OnItemClickListener<Icon>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_icon, parent, false)
        return IconVH(view)
    }

    fun setItems(items: List<Icon>) {
        icons.clear()
        icons.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: IconVH, position: Int) {
        holder.bind(icons[position])
    }

    override fun getItemCount(): Int = icons.size

    inner class IconVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val root: ConstraintLayout = itemView.findViewById(R.id.root)
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)

        fun bind(item: Icon) {
            GlideApp.with(itemView.context)
                    .load(item.previewPath())
                    .dontAnimate()
                    .centerCrop()
                    .into(ivIcon)

            root.setOnClickListener { itemClickListener?.onClicked(item) }
        }
    }
}
