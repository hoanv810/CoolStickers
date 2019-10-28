package github.hoanv810.icon.ui.share

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import github.hoanv810.icon.GlideApp
import github.hoanv810.icon.R
import github.hoanv810.icon.domain.gson.Icon
import github.hoanv810.icon.util.FileUtils.ShareTarget.*
import github.hoanv810.shared.util.viewModelProvider
import github.hoanv810.shared.view.CustomDimDialogFragment
import kotlinx.android.synthetic.main.fragment_share_dialog.*
import javax.inject.Inject

/**
 * @author hoanv
 * @since 8/10/18
 */
class ShareDialogFragment : CustomDimDialogFragment(), HasSupportFragmentInjector, View.OnClickListener {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ShareDialogViewModel
    private lateinit var icon: Icon

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_share_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)

        icon = arguments?.getParcelable(ICON)!!

        initViewContents()
    }

    private fun initViewContents() {
        GlideApp.with(this)
                .load(icon.imagePath())
                .into(iv_icon)

        button_twitter.setOnClickListener(this)
        button_facebook.setOnClickListener(this)
        button_messenger.setOnClickListener(this)
        button_whatsapp.setOnClickListener(this)
        button_instagram.setOnClickListener(this)
        button_telegram.setOnClickListener(this)
        button_kakao.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        activity?.let {
            when (v?.id) {
                R.id.button_twitter -> viewModel.shareImage(it, icon, TWITTER)
                R.id.button_facebook -> viewModel.shareImage(it, icon, FACEBOOK)
                R.id.button_messenger -> viewModel.shareImage(it, icon, MESSENGER)
                R.id.button_whatsapp -> viewModel.shareImage(it, icon, WHATSAPP)
                R.id.button_instagram -> viewModel.shareImage(it, icon, INSTAGRAM)
                R.id.button_telegram -> viewModel.shareImage(it, icon, TELEGRAM)
                R.id.button_kakao -> viewModel.shareImage(it, icon, KAKAOTALK)
            }
        }
    }

    companion object {

        private const val ICON = "icon"
        const val DIALOG_SHARE_PREFERENCE = "dialog_share_preference"

        fun newInstance(icon: Icon): ShareDialogFragment {
            return ShareDialogFragment().apply {
                arguments = Bundle().apply { putParcelable(ICON, icon) }
            }
        }
    }
}