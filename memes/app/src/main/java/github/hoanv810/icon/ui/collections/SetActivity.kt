package github.hoanv810.icon.ui.collections

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.setupWithNavController
import github.hoanv810.icon.BuildConfig
import github.hoanv810.icon.R
import github.hoanv810.icon.ui.base.BaseActivity
import github.hoanv810.shared.util.viewModelProvider
import kotlinx.android.synthetic.main.sets_activity.*
import timber.log.Timber
import javax.inject.Inject

class SetActivity : BaseActivity() {

    private lateinit var viewModel: SetViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var pendingDestination: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sets_activity)

        viewModel = viewModelProvider(viewModelFactory)
        setupNavDrawer()
    }

    private fun setupNavDrawer() {
        setSupportActionBar(toolbar)

        supportActionBar?.let { actionBar ->
            actionBar.title = getString(R.string.label_collections)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val navController = findNavController(nav_host)

        navigationView.setCheckedItem(R.id.list_collection)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.list_collection -> pendingDestination = R.id.list_collection

                R.id.list_manage -> pendingDestination = R.id.action_list_collection_to_list_manage

                R.id.list_archive -> pendingDestination = R.id.action_list_collection_to_list_archive

                R.id.action_rate_app -> showRatingPlayStore()

                R.id.action_invite_friends -> inviteFriends()
            }

            drawer_layout.closeDrawers()
            false
        }

        drawer_layout.addDrawerListener(object : ActionBarDrawerToggle(this, drawer_layout,
                R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                if (pendingDestination != 0) {
                    navController.navigate(pendingDestination)
                    pendingDestination = 0
                }
            }
        })

        toolbar.setupWithNavController(navController, drawer_layout)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.list_collection -> navigationView.setCheckedItem(R.id.list_collection)
                R.id.list_manage -> navigationView.setCheckedItem(R.id.list_manage)
                R.id.list_archive -> navigationView.setCheckedItem(R.id.list_archive)
            }
            toolbar.title = destination.label
        }
    }

    private fun showRatingPlayStore() {
        val uri = Uri.parse(GOOGLE_PLAY_MARKET_URL + BuildConfig.APPLICATION_ID)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY xor Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            Timber.e(e)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_URL + BuildConfig.APPLICATION_ID)))
        }

        viewModel.trackRateAppClick()
    }

    private fun inviteFriends() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.invitation_message))
            type = "text/plain"
        }
        startActivity(sendIntent)

        viewModel.trackInvitePeople()
    }

    companion object {

        private const val GOOGLE_PLAY_MARKET_URL = "market://details?id="
        private const val GOOGLE_PLAY_STORE_URL = "ttp://play.google.com/store/apps/details?id="
    }
}