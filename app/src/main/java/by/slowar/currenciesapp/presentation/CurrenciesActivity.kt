package by.slowar.currenciesapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.slowar.currenciesapp.R
import by.slowar.currenciesapp.databinding.ActivityCurrenciesBinding
import by.slowar.currenciesapp.presentation.currencieslist.CurrenciesListFragment

class CurrenciesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrenciesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrenciesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.currenciesListItem -> {
                    switchFragment(CurrenciesListFragment.newInstance())
                    true
                }
                R.id.favouritesCurrenciesItem -> {
                    //switchFragment(currencyConverterFragment)
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.selectedItemId = R.id.currenciesListItem
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}