package github.hoanv810.icon.ui.icons

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import github.hoanv810.icon.ui.share.ShareDialogFragment
import github.hoanv810.icon.ui.share.ShareDialogViewModel
import github.hoanv810.shared.di.ChildFragmentScoped
import github.hoanv810.shared.di.ViewModelKey

/**
 * @author hoanv
 * @since 9/4/18
 */
@Module
internal abstract class IconModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeShareDialogFragment(): ShareDialogFragment

    @Binds
    @IntoMap
    @ViewModelKey(ShareDialogViewModel::class)
    internal abstract fun bindShareDialogViewModel(viewModel: ShareDialogViewModel): ViewModel
}