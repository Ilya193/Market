package ru.ikom.market.core

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.util.DebugLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.ikom.feature_basket.di.featureBasketModule
import ru.ikom.feature_menu.di.featureMenuModule

class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, featureMenuModule, featureBasketModule)
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .crossfade(true)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.02)
                    .directory(cacheDir)
                    .build()
            }
            .build()
    }
}