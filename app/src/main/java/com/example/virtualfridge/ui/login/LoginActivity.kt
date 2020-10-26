package com.example.virtualfridge.ui.login

import android.content.Intent
import android.os.Bundle
import com.example.virtualfridge.R
import com.example.virtualfridge.ui.base.BaseActivity
import com.example.virtualfridge.ui.login.google.GoogleLoginListener
import com.example.virtualfridge.ui.login.google.GoogleLoginListener.Companion.RC_GOOGLE_LOGIN_REQUEST
import com.example.virtualfridge.ui.main.MainActivity
import com.example.virtualfridge.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity : BaseActivity(), GoogleLoginListener, LoginListener {

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

        presenter.init()
    }

    override fun onStart() {
        super.onStart()
        presenter.checkForLoggedInUser()
    }

    // TODO: Create automatic result handler with dagger
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_LOGIN_REQUEST) {
            presenter.handleGoogleLoginResult(data)
        }
    }

    override fun openGoogleLoginRequest(intent: Intent) = startActivityForResult(intent, RC_GOOGLE_LOGIN_REQUEST);

    override fun showLoginError() {
        // TODO: error dialog
    }

    override fun openMainActivity() = startActivity(MainActivity.getIntent(this))

    private fun openRegisterActivity() = startActivity(RegisterActivity.getIntent(this))

}
