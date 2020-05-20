package com.ballchalu.application

import com.ballchalu.BuildConfig
import com.ballchalu.R
import com.ballchalu.base.di.DaggerAppComponent
import com.ballchalu.mqtt.MqttConnection
import com.ballchalu.utils.ThemeHelper
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.util.ConstantsBase
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


class App : DaggerApplication() {
    @Inject
    lateinit var mqttConnection: MqttConnection
    @Inject
    lateinit var sharedPref: SharedPreferenceStorage

    override fun onCreate() {
        super.onCreate()
        instance = this
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Play-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
        mqttConnection.connectToClient()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        sharedPref.theme?.let { ThemeHelper.applyTheme(it) }

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    companion object {
        lateinit var instance: App
    }
}