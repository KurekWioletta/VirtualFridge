package com.example.virtualfridge.ui.login

import android.os.Bundle
import com.example.virtualfridge.R
import com.example.virtualfridge.ui.base.BaseActivity
import com.example.virtualfridge.ui.main.MainActivity
import com.example.virtualfridge.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

// needed to extend BaseActivity
class LoginActivity : BaseActivity() {

    // needed
    @Inject
    lateinit var presenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            presenter.loginClicked()
        }

        btnLoginGoogle.setOnClickListener {
            presenter.loginGoogleClicked()
        }

        tvRegister.setOnClickListener { openRegisterActivity() }
    }

    fun openMainActivity() = this.startActivity(MainActivity.getIntent(this))

    private fun openRegisterActivity() = this.startActivity(RegisterActivity.getIntent(this))

}
