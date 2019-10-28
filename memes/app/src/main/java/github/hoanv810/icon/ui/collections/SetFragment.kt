package github.hoanv810.icon.ui.collections

import android.database.DataSetObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import github.hoanv810.icon.R
import github.hoanv810.icon.domain.event.InvalidateCollection
import github.hoanv810.shared.util.activityViewModelProvider
import kotlinx.android.synthetic.main.sets_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * @author hoanv
 * @since 11/7/18
 */

class SetFragment : DaggerFragment() {

    private lateinit var pagerAdapter: SetPagerAdapter
    private lateinit var viewModel: SetViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.sets_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pagerAdapter = SetPagerAdapter(activity!!, childFragmentManager)
        pagerAdapter.registerDataSetObserver(object : DataSetObserver() {
            override fun onChanged() {
                invalidateTabLayout()
            }
        })

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        initContentViews()
        initViewModels()
    }

    private fun invalidateTabLayout() {
        for (index in 0..tabLayout.tabCount) {
            tabLayout.getTabAt(index)?.customView = pagerAdapter.getCustomTabView(index)
        }
    }

    private fun initContentViews() {
        btnRetry.setOnClickListener {
            invalidateLoading(false)
            llError.visibility = View.INVISIBLE

            viewModel.loadCollections()
        }
    }

    private fun initViewModels() {
        viewModel = activityViewModelProvider(viewModelFactory)
        viewModel.apply {
            setsData.observe(viewLifecycleOwner, Observer { sets ->
                sets?.let {
                    pagerAdapter.setItems(it)
                    invalidateLoading(true)
                }
            })

            errorStatus.observe(viewLifecycleOwner, Observer {
                if (it) {
                    invalidateLoading(true)
                    llError.visibility = View.VISIBLE
                }
            })
        }

        viewModel.loadCollections()
    }

    private fun invalidateLoading(hide: Boolean) {
        progressBar.visibility = if (hide) View.GONE else View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onInvalidateCollection(event: InvalidateCollection) {
        viewModel.invalidateCollection()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}