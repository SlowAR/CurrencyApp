package by.slowar.currenciesapp.di.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(context: Application): Builder

        fun create(): AppComponent
    }
}