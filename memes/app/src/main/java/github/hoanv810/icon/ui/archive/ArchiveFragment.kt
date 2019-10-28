package github.hoanv810.icon.ui.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import github.hoanv810.icon.R
import github.hoanv810.icon.ui.view.HorizontalDecoration
import github.hoanv810.shared.util.viewModelProvider
import kotlinx.android.synthetic.main.edit_set_fragment.*
import javax.inject.Inject

/**
 * @author hoanv
 * @since 11/7/18
 */

class ArchiveFragment : DaggerFragment(), ArchiveAdapter.RestoreListener {

    private lateinit var adapter: ArchiveAdapter
    private lateinit var viewModel: ArchiveViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.edit_set_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewContents()
        initViewModels()
    }

    private fun initViewContents() {
        adapter = ArchiveAdapter()
        adapter.restoreListener = this

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(HorizontalDecoration(context!!))
        recyclerView.adapter = adapter
    }

    private fun initViewModels() {
        viewModel = viewModelProvider(viewModelFactory)
        viewModel.apply {
            loadingStatus.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })

            setsData.observe(viewLifecycleOwner, Observer {
                adapter.setItems(it)
            })
        }
    }

    override fun onRestored(id: Int) {
        viewModel.restoreSet(id)
    }
}