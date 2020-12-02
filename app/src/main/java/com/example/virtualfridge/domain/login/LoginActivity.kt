package com.example.virtualfridge.domain.login

import android.content.Intent
import android.os.Bundle
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.login.google.GoogleLoginListener
import com.example.virtualfridge.domain.login.google.GoogleLoginListener.Companion.RC_GOOGLE_LOGIN_REQUEST
import com.example.virtualfridge.domain.main.MainActivity
import com.example.virtualfridge.domain.register.RegisterActivity
import com.example.virtualfridge.utils.BaseValidationViewModel
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity : BaseActivity(), GoogleLoginListener {

    @Inject
    lateinit var presenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            presenter.loginClicked(etEmail.text.toString(), etPassword.text.toString())
        }
        btnLoginGoogle.setOnClickListener {
            presenter.loginGoogleClicked()
        }
        tvRegister.setOnClickListener {
            openRegisterActivity()
        }

        presenter.init()
    }

    override fun onStart() {
        super.onStart()
        presenter.checkForLoggedInUser()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_LOGIN_REQUEST) {
            presenter.handleGoogleLoginResult(data)
        }
    }

    override fun openGoogleLoginRequest(intent: Intent) =
        startActivityForResult(intent, RC_GOOGLE_LOGIN_REQUEST);

    override fun showGoogleLoginError() = showAlert(getString(R.string.login_google_error))

    override fun openMainActivity() {
        finish()
        startActivity(MainActivity.getIntentWithClearStack(this))
    }

    private fun openRegisterActivity() = startActivity(RegisterActivity.getIntent(this))

    fun showValidationResults(validationViewModel: ValidationViewModel) {
        etEmail.error = validationViewModel.emailError
        etPassword.error = validationViewModel.passwordError
    }

    data class ValidationViewModel(
        val emailError: String?,
        val passwordError: String?
    ) : BaseValidationViewModel() {
        override fun toList() = listOf(emailError, passwordError)
    }

    companion object {
        fun getIntent(activity: BaseActivity) = Intent(activity, LoginActivity::class.java)

        fun getIntentWithClearStack(activity: BaseActivity) =
            Intent(
                activity,
                MainActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}
