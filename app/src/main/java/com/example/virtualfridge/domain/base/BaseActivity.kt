package com.example.virtualfridge.domain.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualfridge.domain.dialogs.DialogManager
import com.example.virtualfridge.domain.dialogs.VfProgressDialog
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var dialogManager: DialogManager

    private var viewSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewSubscriptions.clear()
    }

    fun showLoading() = dialogManager.showDialog(VfProgressDialog())

    fun hideLoading() = dialogManager.dismissDialog(VfProgressDialog.TAG)

    fun registerViewSubscription(subscription: Disposable) = viewSubscriptions.add(subscription)
}
