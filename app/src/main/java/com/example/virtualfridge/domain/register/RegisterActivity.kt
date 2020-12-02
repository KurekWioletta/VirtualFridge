package com.example.virtualfridge.domain.register

import android.content.Intent
import android.os.Bundle
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.login.LoginActivity
import com.example.virtualfridge.utils.BaseValidationViewModel
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var presenter: RegisterActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            presenter.registerUserClicked(
                etEmail.text.toString(),
                etPassword.text.toString(),
                etFirstName.text.toString(),
                etLastName.text.toString()
            )
        }
    }

    fun showValidationResults(validationViewModel: ValidationViewModel) {
        etEmail.error = validationViewModel.emailError
        etPassword.error = validationViewModel.passwordError
        etFirstName.error = validationViewModel.firstNameError
        etLastName.error = validationViewModel.lastNameError
    }

    fun openLoginActivity() {
        finish()
        startActivity(LoginActivity.getIntentWithClearStack(this))
    }

    companion object {
        fun getIntent(activity: BaseActivity) = Intent(activity, RegisterActivity::class.java)
    }

    data class ValidationViewModel(
        val emailError: String?,
        val passwordError: String?,
        val firstNameError: String?,
        val lastNameError: String?
    ) : BaseValidationViewModel() {
        override fun toList() = listOf(emailError, passwordError, firstNameError, lastNameError)
    }
}
