package github.hoanv810.icon.ui.archive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import github.hoanv810.icon.GlideApp
import github.hoanv810.icon.R
import github.hoanv810.icon.domain.gson.Collection

/**
 * @author hoanv
 * @since 10/24/18
 */
class ArchiveAdapter : RecyclerView.Adapter<ArchiveAdapter.SetVH>() {

    private val sets = mutableListOf<Collection>()
    var restoreListener: RestoreListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_archive, parent, false)
        return SetVH(view)
    }

    override fun onBindViewHolder(holder: SetVH, position: Int) {
        holder.bind(sets[position])
    }

    override fun getItemCount(): Int = sets.size

    fun setItems(items: List<Collection>) {
        sets.clear()
        sets.addAll(items)
        notifyDataSetChanged()
    }

    fun restoreItem(position: Int) {
        val collection = sets[position]

        sets.removeAt(position)
        notifyItemRemoved(position)

        restoreListener?.onRestored(collection.id)
    }

    inner class SetVH(view: View) : RecyclerView.ViewHolder(view) {

        private val image = itemView.findViewById<ImageView>(R.id.iv_icon)
        private val name = itemView.findViewById<TextView>(R.id.tv_name)
        private val quantity = itemView.findViewById<TextView>(R.id.tv_quantity)
        private val restore = itemView.findViewById<MaterialButton>(R.id.button_restore)

        fun bind(set: Collection) {
            GlideApp.with(itemView)
                    .load(set.coverPageUrl)
                    .dontAnimate()
                    .into(image)

            name.text = set.title
            quantity.text = String.format(itemView.context.getString(R.string.number_stickers, itemCount))
            restore.setOnClickListener { restoreItem(adapterPosition) }
        }
    }

    interface RestoreListener {

        fun onRestored(id: Int)
    }
}