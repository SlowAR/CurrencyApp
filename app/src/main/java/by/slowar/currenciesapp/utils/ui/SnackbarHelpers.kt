package by.slowar.currenciesapp.utils.ui

import android.view.View
import androidx.annotation.StringRes
import by.slowar.currenciesapp.R
import com.google.android.material.snackbar.Snackbar

fun showDismissibleSnackbar(view: View, @StringRes textId: Int) {
    val snackbar = Snackbar.make(view, textId, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction(R.string.ok_word) {
        snackbar.dismiss()
    }
    snackbar.show()
}

fun showDismissibleSnackbar(view: View, text: String) {
    val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction(R.string.ok_word) {
        snackbar.dismiss()
    }
    snackbar.show()
}