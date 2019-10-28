package github.hoanv810.icon.data.tracking

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author hoanv
 * @since 10/2/18
 */
@Module
class AnalyticsModule {

    @Provides
    @Singleton
    fun provideAnalytics(app: Application) : Analytics {
        return IconFirebaseAnalytics(FirebaseAnalytics.getInstance(app))
    }

}