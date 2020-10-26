package com.example.virtualfridge.ui.register

import android.util.Patterns
import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.ExampleApi
import com.example.virtualfridge.ui.register.RegisterActivity.ValidationViewModel
import com.example.virtualfridge.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivityPresenter @Inject constructor(
    private val view: RegisterActivity,
    private val exampleApi: ExampleApi,
    private val rxTransformerManager: RxTransformerManager
) {

    fun onRegisterUserClicked(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        val validationViewModel = ValidationViewModel(
            email.validate(view.getString(R.string.error_email)) { it.isValidEmail() },
            password.validate(view.getString(R.string.error_password)) { it.isValidPassword() },
            firstName.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() },
            lastName.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            view.registerViewSubscription(
                exampleApi.registerUser(email, password, firstName, lastName)
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .subscribe()
            )
        }
    }

}
