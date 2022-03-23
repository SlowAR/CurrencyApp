package by.slowar.currenciesapp.di.app

import android.app.Application
import by.slowar.currenciesapp.di.currencies.CurrenciesComponent
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class, RemoteApiModule::class, LocalDbModule::class, ConfigsModule::class])
@AppScope
interface AppComponent {

    val currenciesComponent: CurrenciesComponent

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(context: Application): Builder

        fun create(): AppComponent
    }
}