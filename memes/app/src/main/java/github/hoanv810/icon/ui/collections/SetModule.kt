package github.hoanv810.icon.ui.collections

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import github.hoanv810.icon.ui.archive.ArchiveFragment
import github.hoanv810.icon.ui.archive.ArchiveViewModel
import github.hoanv810.icon.ui.manage.ManageFragment
import github.hoanv810.icon.ui.icons.IconFragment
import github.hoanv810.icon.ui.icons.IconViewModel
import github.hoanv810.icon.ui.manage.ManageViewModel
import github.hoanv810.shared.di.FragmentScoped
import github.hoanv810.shared.di.ViewModelKey

/**
 * @author hoanv
 * @since 9/4/18
 */
@Module
internal abstract class SetModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSetFragment(): SetFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeIconFragment(): IconFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeArchiveFragment(): ArchiveFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeManageFragment(): ManageFragment

    @Binds
    @IntoMap
    @ViewModelKey(IconViewModel::class)
    internal abstract fun bindIconViewModel(viewModel: IconViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArchiveViewModel::class)
    internal abstract fun bindArchiveViewModel(viewModel: ArchiveViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ManageViewModel::class)
    internal abstract fun bindManageViewModel(viewModel: ManageViewModel): ViewModel

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [SetViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(SetViewModel::class)
    internal abstract fun bindSetViewModel(viewModel: SetViewModel): ViewModel
}