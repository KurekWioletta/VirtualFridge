package com.example.virtualfridge.domain.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {

    // to samo co w BaseActivity tylko dla fragmentow
    private var viewSubscriptions = CompositeDisposable()

    override fun onAttach(context: Context) {
        // wstrzykniecie fragmentu do drzewa daggerowego, musimy skorzystac z AndroidSupportInjection, poniewaz
        // nie ma jeszcze wsparcia w AndroidInjection dla fragmentow z androidx.fragment.app (jest wylacznie dla fragmentow z android.app)
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        viewSubscriptions.clear()
    }

    fun showLoading() = (activity as BaseActivity).showLoading()

    fun hideLoading() = (activity as BaseActivity).hideLoading()

    // TODO: Show alert
    fun showAlert(errorMessage: String) =
        Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show()

    fun registerViewSubscription(subscription: Disposable) = viewSubscriptions.add(subscription)
}