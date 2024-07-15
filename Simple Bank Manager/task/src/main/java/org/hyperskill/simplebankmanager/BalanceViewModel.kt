package org.hyperskill.simplebankmanager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BalanceViewModel : ViewModel() {
    val balance = MutableLiveData<Double>()
}