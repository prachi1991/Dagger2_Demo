package com.ballchalu.base.di

import com.ballchalu.ui.login.LoginActivity
import com.ballchalu.ui.navigation.NavigationActivity
import com.ballchalu.ui.navigation.NavigationModule
import com.ballchalu.ui.splash.SplashActivity
import com.ccpp.shared.core.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be [AppComponent]. You never
 * need to tell [AppComponent] that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that [AppComponent] exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the
 * specified modules and be aware of a scope annotation [@ActivityScoped].
 * When Dagger.Android annotation processor runs it will create 2 subcomponents for us.
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            NavigationModule::class
        ]
    )
    internal abstract fun mainActivity(): SplashActivity

    internal abstract fun loginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            NavigationModule::class
        ]
    )
    internal abstract fun navigationActivity(): NavigationActivity
}
