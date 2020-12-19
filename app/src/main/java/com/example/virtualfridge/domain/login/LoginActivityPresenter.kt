package com.example.virtualfridge.domain.login

import android.content.Intent
import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.UserApi
import com.example.virtualfridge.data.api.models.mapToUser
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.login.google.GoogleLoginManager
import com.example.virtualfridge.utils.ApiErrorParser
import com.example.virtualfridge.utils.RxTransformerManager
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

// wszystkie zaleznosci musza zostac dostarczone w modulach daggerowych lub przez @Inject przy konstruktorze (np RxTransformerManager),
// poniewaz tutaj wstrzykujemy presenter, ktory bedzie wykorzystany w activity
class LoginActivityPresenter @Inject constructor(
    private val view: LoginActivity,
    private val userApi: UserApi,
    private val userDataStore: UserDataStore,
    private val rxTransformerManager: RxTransformerManager,
    private val apiErrorParser: ApiErrorParser,
    private val googleLoginManager: GoogleLoginManager
) {

    // inicjalizacja GoogleSignIn client
    fun init() = googleLoginManager.initializeSignInClient()

    // inicjalizacja GoogleSignIn client
    fun resume() = googleLoginManager.initializeSignInClient()

    // sprawdzamy czy user jest zalogowany pobierajac dane z cache i sprawdzajac zalogowane konto przez google
    fun checkForLoggedInUser() {
        if (userDataStore.user() != null) {
            view.openMainActivity()
        } else {
            // w sytuacji kiedy user jestt zalogowany w sesji google, ale nie mamy go w cache to wylogowujemy takiego usera
            if (googleLoginManager.userLoggedIn()) {
                googleLoginManager.logout()
            }
        }
    }

    // logowanie przez creditentials
    fun loginClicked(email: String, password: String) {
        val validationViewModel = LoginActivity.ValidationViewModel(
            email.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() },
            password.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            // Pobranie tokenu fla firebaseMessaging
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    // TODO: error tracking
                    view.showAlert(view.getString(R.string.login_login_error))
                    return@OnCompleteListener
                }

                // jesli token zostal poprawnie pobrany to logujemy usera
                view.registerViewSubscription(userApi.loginUser(email, password)
                    // po otrzymaniu response z backendu cachujemy usera
                    .doOnNext { userDataStore.cacheUser(it.mapToUser()) }
                    // nastepnie wysylamy na backend nasz token z firebase messaging aby mozna bylo poprawnie wysylac powiadomienia
                    .flatMap {
                        userApi.notifications(
                            userId = it.id,
                            messagingToken = task.result
                        )
                    }
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { view.showLoading() }
                    .doOnTerminate { view.hideLoading() }
                    .subscribe({
                        // po poprawnym zalogowaniu/rejestracji otwieramy glowne activity
                        view.openMainActivity()
                    }, {
                        view.showAlert(apiErrorParser.parse(it))
                    })
                )
            }).addOnFailureListener {
                view.showAlert(view.getString(R.string.login_login_error))
            }
        }
    }

    fun loginGoogleClicked() = googleLoginManager.requestLogin()

    fun handleGoogleLoginResult(intent: Intent?) = googleLoginManager.login(intent)
}