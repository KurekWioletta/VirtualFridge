package com.example.virtualfridge.domain.login

import android.content.Intent
import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.ExampleApi
import com.example.virtualfridge.data.api.models.mapToUser
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.login.google.GoogleLoginManager
import com.example.virtualfridge.utils.RxTransformerManager
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import javax.inject.Inject

class LoginActivityPresenter @Inject constructor(
    private val view: LoginActivity,
    private val exampleApi: ExampleApi,
    private val userDataStore: UserDataStore,
    private val rxTransformerManager: RxTransformerManager,
    private val googleLoginManager: GoogleLoginManager
) {

    fun init() = googleLoginManager.initializeSignInClient()

    fun resume() = googleLoginManager.initializeSignInClient()

    fun checkForLoggedInUser() {
        if (userDataStore.getUser() != null) {
            view.openMainActivity()
        } else {
            if (googleLoginManager.userLoggedIn()) {
                googleLoginManager.logout()
            }
        }
    }

    fun loginClicked(email: String, password: String) {
        val validationViewModel = LoginActivity.ValidationViewModel(
            email.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() },
            password.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            view.registerViewSubscription(exampleApi.loginUser(email, password)
                .doOnNext { userDataStore.cacheUser(it.mapToUser()) }
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .subscribe({
                    view.finish()
                }, {
                    // TODO: error handling
                })
            )
        }
    }

    fun loginGoogleClicked() = googleLoginManager.requestLogin()

    fun handleGoogleLoginResult(intent: Intent?) = googleLoginManager.login(intent)
}