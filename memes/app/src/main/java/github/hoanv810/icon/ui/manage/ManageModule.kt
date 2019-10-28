package github.hoanv810.icon.ui.manage

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import github.hoanv810.shared.di.ChildFragmentScoped
import github.hoanv810.shared.di.ViewModelKey

/**
 * @author hoanv
 * @since 11/1/18
 */
@Module
internal abstract class ManageModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeEditSetFragment(): ManageFragment

    @Binds
    @IntoMap
    @ViewModelKey(ManageViewModel::class)
    internal abstract fun bindEditSetViewModel(viewModel: ManageViewModel): ViewModel
}