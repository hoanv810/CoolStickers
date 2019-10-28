package github.hoanv810.icon.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import github.hoanv810.icon.R
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.ui.view.HorizontalDecoration
import github.hoanv810.icon.ui.view.SwipeAndDragHelper
import github.hoanv810.shared.util.viewModelProvider
import kotlinx.android.synthetic.main.edit_set_fragment.*
import javax.inject.Inject

/**
 * @author hoanv
 * @since 10/24/18
 */
class ManageFragment : DaggerFragment(), ManageAdapter.SwipeAndDragListener {

    private lateinit var adapter: ManageAdapter
    private lateinit var viewModel: ManageViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.edit_set_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewContents()
        initViewModel()
    }

    private fun initViewContents() {

        adapter = ManageAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(HorizontalDecoration(context!!))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(SwipeAndDragHelper(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        adapter.touchHelper = itemTouchHelper
        adapter.swipeAndDragListener = this
    }

    private fun initViewModel() {
        viewModel = viewModelProvider(viewModelFactory)
        viewModel.apply {
            setsData.observe(viewLifecycleOwner, Observer {
                adapter.setItems(it)
            })

            loadingStatus.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            })
        }
    }

    override fun onMovedCompleted(directory: String) {
        viewModel.trackReorderCollection(directory)
        viewModel.saveCollectionOrder(adapter.sets.map { it.id })
    }

    override fun onSwipedCompleted(set: Collection) {
        viewModel.trackHideCollection(set.directory)
        viewModel.hideCollection(set.id)
        viewModel.saveCollectionOrder(adapter.sets.map { it.id })
    }
}