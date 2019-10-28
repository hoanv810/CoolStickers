package github.hoanv810.icon

import android.content.Context
import androidx.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import github.hoanv810.icon.di.DaggerAppComponent
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import java.io.File

/**
 * @author hoanv
 * @since 1/22/17
 */
class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }

        if (BuildConfig.ENABLE_ANALYTIC) {
            val fabric = Fabric.Builder(this)
                    .kits(Crashlytics())
                    .debuggable(true)
                    .build()
            Fabric.with(fabric)
        }

        // need a dot before application id to make this directory hidden
        FILES_DIR = filesDir.absolutePath + File.separator
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {

        lateinit var FILES_DIR: String
    }
}