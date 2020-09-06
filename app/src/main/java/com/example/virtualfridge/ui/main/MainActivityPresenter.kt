package com.example.virtualfridge.ui.main

import com.example.virtualfridge.data.api.ExampleApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val view: MainActivity,
    private val exampleApi: ExampleApi
) {

    // TODO: proper error handler - not working at all
    fun onSendClicked(username: String): Disposable = exampleApi.getUser(username)
        .compose {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) }
        .doOnSubscribe { view.showLoading() }
        .doOnTerminate { view.hideLoading() }
        .doOnError {it ->
            view.showResult("user doesn't exist")
        }
        .subscribe ({ view.showResult("user exist") }, {})

}