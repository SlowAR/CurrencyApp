package by.slowar.currenciesapp.di.currencies

import by.slowar.currenciesapp.di.ScreenScope
import by.slowar.currenciesapp.presentation.currencieslist.CurrenciesListFragment
import by.slowar.currenciesapp.presentation.currencieslist.dialog.CurrenciesDialogFragment
import dagger.Subcomponent

@Subcomponent(modules = [CurrenciesRepositoryModule::class, CurrenciesDataSourceModule::class])
@ScreenScope
interface CurrenciesComponent {

    fun inject(fragment: CurrenciesListFragment)

    fun inject(fragment: CurrenciesDialogFragment)
}