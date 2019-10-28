package github.hoanv810.icon.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import github.hoanv810.icon.App
import github.hoanv810.icon.data.api.ApiModule
import github.hoanv810.icon.data.firestore.FirestoreModule
import github.hoanv810.icon.data.tracking.AnalyticsModule
import github.hoanv810.icon.util.scheduler.SchedulerModule
import github.hoanv810.shared.di.ViewModelModule
import javax.inject.Singleton

/**
 * @author hoanv
 * @since 4/5/18.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ApiModule::class,
    SchedulerModule::class,
    ViewModelModule::class,
    ActivityBindingModule::class,
    FirestoreModule::class,
    AnalyticsModule::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}