package by.slowar.currenciesapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.slowar.currenciesapp.R
import by.slowar.currenciesapp.databinding.ActivityCurrenciesBinding
import by.slowar.currenciesapp.presentation.currencieslist.CurrenciesListFragment
import by.slowar.currenciesapp.presentation.currencieslist.dialog.CurrenciesDialogFragment
import by.slowar.currenciesapp.presentation.currencieslist.sort.SortDialogFragment

class CurrenciesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrenciesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrenciesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val allCurrenciesFragment = CurrenciesListFragment.newInstance()
        val favouriteCurrenciesFragment = CurrenciesListFragment.newInstance(true)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.currenciesListItem -> {
                    switchFragment(allCurrenciesFragment)
                    true
                }
                R.id.favouritesCurrenciesItem -> {
                    switchFragment(favouriteCurrenciesFragment)
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.selectedItemId = R.id.currenciesListItem
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortMenuItem -> {
                SortDialogFragment.newInstance()
                    .show(supportFragmentManager, CurrenciesDialogFragment.DIALOG_TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}