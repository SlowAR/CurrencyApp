package by.slowar.currenciesapp

import android.app.Application
import by.slowar.currenciesapp.di.app.AppComponent
import by.slowar.currenciesapp.di.app.DaggerAppComponent

class CurrenciesApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appContext(this).create()
    }
}