package com.example.virtualfridge.ui.register

import android.content.Intent
import android.os.Bundle
import com.example.virtualfridge.R
import com.example.virtualfridge.ui.base.BaseActivity
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var presenter: RegisterActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    companion object {
        fun getIntent(activity: BaseActivity) = Intent(activity, RegisterActivity::class.java)
    }
}
