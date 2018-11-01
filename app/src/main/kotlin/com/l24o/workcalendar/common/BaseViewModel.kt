package com.l24o.findmylove.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    val errorMessage = MutableLiveData<Event<String>>()
    val message = MutableLiveData<Event<String>>()

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun Disposable.disposeOnCleared(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}