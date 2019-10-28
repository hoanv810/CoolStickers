package github.hoanv810.icon.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import github.hoanv810.icon.ui.icons.IconModule
import github.hoanv810.icon.ui.collections.SetActivity
import github.hoanv810.icon.ui.collections.SetModule
import github.hoanv810.shared.di.ActivityScoped

/**
 * @author hoanv
 * @since 8/24/18
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                SetModule::class,
                IconModule::class
            ])
    internal abstract fun setActivity(): SetActivity
}