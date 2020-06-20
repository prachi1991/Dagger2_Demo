package com.ballchalu.base.di

import com.ballchalu.ui.bc_coins.BcCoinModule
import com.ballchalu.ui.contest.ContestModule
import com.ballchalu.ui.create_bet.CreateBetModule
import com.ballchalu.ui.how_to_play.HowToPlayModule
import com.ballchalu.ui.ledgers.BcCoinsLedgersModule
import com.ballchalu.ui.login.LoginModule
import com.ballchalu.ui.login.container.LoginActivity
import com.ballchalu.ui.login.forget.ForgetPasswordModule
import com.ballchalu.ui.login.signin.SignInModule
import com.ballchalu.ui.login.signup.SignUpModule
import com.ballchalu.ui.match.details.MatchDetailsModule
import com.ballchalu.ui.match_listing.MatchListingModule
import com.ballchalu.ui.match_listing.recent.DeclaredMatchModule
import com.ballchalu.ui.navigation.NavigationActivity
import com.ballchalu.ui.navigation.NavigationModule
import com.ballchalu.ui.profile.changepassword.ChangePasswordModule
import com.ballchalu.ui.profile.container.ProfileContainerModule
import com.ballchalu.ui.profile.details.ProfileModule
import com.ballchalu.ui.profile.edit.EditProfileModule
import com.ballchalu.ui.profile.menu.ProfileListModule
import com.ballchalu.ui.splash.SplashActivity
import com.ballchalu.ui.splash.SplashModule
import com.ballchalu.ui.winners.WinnerModule
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
            SplashModule::class
        ]
    )
    internal abstract fun mainActivity(): SplashActivity


    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            LoginModule::class,
            SignInModule::class,
            SignUpModule::class,
            ForgetPasswordModule::class
        ]
    )
    internal abstract fun loginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            NavigationModule::class,
            MatchDetailsModule::class,
            ContestModule::class,
            BcCoinsLedgersModule::class,
            BcCoinModule::class,
            MatchListingModule::class,
            CreateBetModule::class,
            WinnerModule::class,
            DeclaredMatchModule::class,
            ProfileModule::class,
            ProfileListModule::class,
            ProfileContainerModule::class,
            EditProfileModule::class,
            ChangePasswordModule::class,
            HowToPlayModule::class
        ]
    )
    internal abstract fun navigationActivity(): NavigationActivity
}
