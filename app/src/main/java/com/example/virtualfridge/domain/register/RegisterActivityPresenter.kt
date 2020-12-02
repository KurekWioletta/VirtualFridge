package com.example.virtualfridge.domain.register

import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.UserApi
import com.example.virtualfridge.data.api.models.mapToUser
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.register.RegisterActivity.ValidationViewModel
import com.example.virtualfridge.utils.*
import javax.inject.Inject

class RegisterActivityPresenter @Inject constructor(
    private val view: RegisterActivity,
    private val userApi: UserApi,
    private val userDataStore: UserDataStore,
    private val apiErrorParser: ApiErrorParser,
    private val rxTransformerManager: RxTransformerManager
) {

    fun registerUserClicked(
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
                userApi.registerUser(email, password, firstName, lastName)
                    .doOnNext { userDataStore.cacheUser(it.mapToUser()) }
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .subscribe({
                        view.openLoginActivity()
                    }, {
                        view.showAlert(apiErrorParser.parse(it))
                    })
            )
        }
    }

}
