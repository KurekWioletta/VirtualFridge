package com.example.virtualfridge.domain.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

open class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun showAlert(errorMessage: String) {
        // TODO: Show alert
        Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show()
    }
}