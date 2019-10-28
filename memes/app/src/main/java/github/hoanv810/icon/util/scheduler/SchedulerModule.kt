package github.hoanv810.icon.util.scheduler

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author hoanv
 * @since 7/17/17.
 *
 * Provides common Schedulers used by Rxjava
 */
@Module
class SchedulerModule {

    @Singleton
    @Provides
    @Named("io")
    fun provideIo(): Scheduler = Schedulers.io()

    @Singleton
    @Provides
    @Named("computation")
    fun provideComputation(): Scheduler = Schedulers.computation()

    @Singleton
    @Provides
    @Named("ui")
    fun provideUi(): Scheduler = AndroidSchedulers.mainThread()
}
