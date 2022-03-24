package by.slowar.currenciesapp.utils.ui

import android.widget.RadioButton
import android.widget.RadioGroup

fun RadioGroup.getSelectedId(): Int {
    val radioButton: RadioButton = this.findViewById(this.checkedRadioButtonId)
    return this.indexOfChild(radioButton)
}

fun RadioGroup.selectById(id: Int) {
    val radioButtonId = this.getChildAt(id).id
    this.check(radioButtonId)
}