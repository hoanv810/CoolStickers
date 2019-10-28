package github.hoanv810.icon.ui.icons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import github.hoanv810.icon.R
import github.hoanv810.icon.domain.gson.Icon
import github.hoanv810.icon.domain.gson.Collection
import github.hoanv810.icon.ui.share.ShareDialogFragment
import github.hoanv810.icon.ui.share.ShareDialogFragment.Companion.DIALOG_SHARE_PREFERENCE
import github.hoanv810.icon.ui.view.MarginDecoration
import github.hoanv810.icon.util.OnItemClickListener
import github.hoanv810.shared.util.viewModelProvider
import kotlinx.android.synthetic.main.fragment_icons.*
import javax.inject.Inject

/**
 * @author hoanv
 * @since 1/23/17
 */
class IconFragment : DaggerFragment() {

    private lateinit var adapter: IconAdapter
    private lateinit var viewModel: IconViewModel
    lateinit var collection: Collection
    var position: Int = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_icons, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = IconAdapter()
        adapter.itemClickListener = object : OnItemClickListener<Icon> {
            override fun onClicked(item: Icon) {
                openShareDialog(item)
            }
        }

        recyclerView.addItemDecoration(MarginDecoration(context!!))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        initContentViews()
        initViewModel()
    }

    private fun initContentViews() {
        button_refresh.setOnClickListener {
            button_refresh.visibility = View.GONE
            tv_percentage.visibility = View.VISIBLE
            tv_percentage.text = getString(R.string.download_percentage, 0)
            viewModel.loadCollection(collection.directory)
        }
    }

    private fun initViewModel() {
        viewModel = viewModelProvider(viewModelFactory)
        viewModel.apply {
            downloadPercentage.observe(viewLifecycleOwner, Observer {
                tv_percentage.text = getString(R.string.download_percentage, it)
            })

            fireIconsData.observe(viewLifecycleOwner, Observer { icons ->
                tv_percentage.visibility = View.GONE
                adapter.setItems(icons)
            })

            errorStatus.observe(viewLifecycleOwner, Observer {
                if (it) {
                    tv_percentage.visibility = View.GONE
                    button_refresh.visibility = View.VISIBLE
                }
            })
        }

        arguments?.let {
            collection = it.getParcelable(COLLECTION)!!
            position = it.getInt(POSITION)
        }

        viewModel.loadCollection(collection.directory)
        viewModel.trackViewCollection(collection.directory)
    }

    private fun openShareDialog(icon: Icon) {
        val dialog = ShareDialogFragment.newInstance(icon)
        dialog.show(requireActivity().supportFragmentManager, DIALOG_SHARE_PREFERENCE)
    }

    companion object {

        private const val COLLECTION = "collection"
        private const val POSITION = "position"

        fun newInstance(coll: Collection, pos: Int): Fragment {
            val args = Bundle().apply {
                putParcelable(COLLECTION, coll)
                putInt(POSITION, pos)
            }
            return IconFragment().apply {
                arguments = args
            }
        }
    }
}