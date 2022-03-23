package by.slowar.currenciesapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.slowar.currenciesapp.databinding.ActivityCurrenciesBinding

class CurrenciesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrenciesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrenciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}