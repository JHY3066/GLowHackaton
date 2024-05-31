package com.example.glowhackaton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glowhackaton.model.ResponseAllMarket

class MarketViewModel : ViewModel() {
    private val _marketResponseLiveData = MutableLiveData<ResponseAllMarket>()
    val marketResponseLiveData: LiveData<ResponseAllMarket>
        get() = _marketResponseLiveData

    fun setMarketResponse(response: ResponseAllMarket) {
        _marketResponseLiveData.postValue(response)
    }
}