package github.hoanv810.icon.di

import android.app.Application
import dagger.Module
import dagger.Provides
import github.hoanv810.icon.App

/**
 * @author hoanv
 * @since 4/5/18.
 */
@Module
class AppModule {

    @Provides
    fun provideApp(application: App): Application = application
}