package github.hoanv810.icon.ui.manage

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import github.hoanv810.icon.GlideApp
import github.hoanv810.icon.R
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.ui.view.SwipeAndDragHelper

/**
 * @author hoanv
 * @since 10/24/18
 */
class ManageAdapter : RecyclerView.Adapter<ManageAdapter.SetVH>(), SwipeAndDragHelper.ActionCompletionContract {

    val sets = mutableListOf<Collection>()
    var touchHelper: ItemTouchHelper? = null
    var swipeAndDragListener: SwipeAndDragListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_set, parent, false)
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

    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        val target = sets[oldPosition]
        val set = Collection(target)
        sets.removeAt(oldPosition)
        sets.add(newPosition, set)
        notifyItemMoved(oldPosition, newPosition)

        swipeAndDragListener?.onMovedCompleted(set.directory)
    }

    override fun onViewSwiped(position: Int) {
        val set = sets[position]

        sets.removeAt(position)
        notifyItemRemoved(position)

        swipeAndDragListener?.onSwipedCompleted(set)
    }

    inner class SetVH(view: View) : RecyclerView.ViewHolder(view) {

        private val image = itemView.findViewById<ImageView>(R.id.iv_icon)
        private val name = itemView.findViewById<TextView>(R.id.tv_name)
        private val quantity = itemView.findViewById<TextView>(R.id.tv_quantity)
        private val drag = itemView.findViewById<ImageView>(R.id.iv_drag)

        fun bind(set: Collection) {
            GlideApp.with(itemView)
                    .load(set.coverPageUrl)
                    .dontAnimate()
                    .into(image)

            name.text = set.title
            quantity.text = String.format(itemView.context.getString(R.string.number_stickers, itemCount))
            drag.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    touchHelper?.startDrag(this)
                }
                false
            }
        }
    }

    interface SwipeAndDragListener {

        fun onMovedCompleted(directory: String)

        fun onSwipedCompleted(set: Collection)
    }
}