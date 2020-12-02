package com.example.virtualfridge.domain.family

import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.FamilyApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.family.FamilyFragment.ValidationViewModel
import com.example.virtualfridge.domain.family.invitations.InvitationViewModel.Companion.fromResponse
import com.example.virtualfridge.utils.*
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class FamilyFragmentPresenter @Inject constructor(
    private val view: FamilyFragment,
    private val familyApi: FamilyApi,
    private val userDataStore: UserDataStore,
    private val apiErrorParser: ApiErrorParser,
    private val rxTransformerManager: RxTransformerManager
) {

    private val triggerRefresh = BehaviorSubject.createDefault<String>("")

    fun init() {
        refreshFamily()
        view.registerViewSubscription(
            triggerRefresh.flatMap {
                familyApi.invitations(userDataStore.loggedInUser().id)
                    .map { fromResponse(it) }
                    .compose { rxTransformerManager.applyIOScheduler(it) }
            }
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnEach { view.hideLoading() }
                .subscribe({ view.updateInvitations(it) }, {
                    view.showAlert(apiErrorParser.parse(it))
                })
        )
    }

    fun leaveFamilyClicked() = view.registerViewSubscription(
        familyApi.leaveFamily(userDataStore.loggedInUser().id)
            .compose { rxTransformerManager.applyIOScheduler(it) }
            .doOnSubscribe { view.showLoading() }
            .doOnEach { view.hideLoading() }
            .subscribe({
                userDataStore.removeFromFamily()
                refreshFamily()
            }, {
                view.showAlert(apiErrorParser.parse(it))
            })
    )

    fun createFamilyClicked(familyName: String) {
        val validationViewModel = ValidationViewModel(
            familyName.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() },
            null
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            view.registerViewSubscription(
                familyApi.createFamily(userDataStore.loggedInUser().id, familyName)
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { view.showLoading() }
                    .doOnEach { view.hideLoading() }
                    .subscribe({ fName ->
                        userDataStore.addToFamily(fName)
                        refreshFamily()
                    }, {
                        view.showAlert(apiErrorParser.parse(it))
                    })
            )
        }
    }

    fun inviteMemberClicked(email: String) {
        val validationViewModel = ValidationViewModel(
            null,
            email.validate(view.getString(R.string.error_email)) { it.isValidEmail() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            view.registerViewSubscription(
                familyApi.inviteMember(userDataStore.loggedInUser().id, email)
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { view.showLoading() }
                    .doOnEach { view.hideLoading() }
                    .subscribe({
                        // TODO: show message
                        view.invitationSent()
                        view.showAlert(view.getString(R.string.family_family_member_invited))
                    }, {
                        view.showAlert(apiErrorParser.parse(it))
                    })
            )
        }
    }

    fun acceptInvitationClicked(invitationId: String) {
        if (userDataStore.familyName().isNullOrEmpty()) {
            view.registerViewSubscription(
                familyApi.acceptInvitation(invitationId)
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { view.showLoading() }
                    .doOnEach { view.hideLoading() }
                    .subscribe({ familyName ->
                        userDataStore.addToFamily(familyName)
                        refreshFamily()
                        triggerRefresh()
                    }, {
                        view.showAlert(apiErrorParser.parse(it))
                    })
            )
        } else {
            view.showAlert(view.getString(R.string.family_leave_family_alert))
        }
    }

    fun declineInvitationClicked(invitationId: String) = view.registerViewSubscription(
        familyApi.declineInvitation(invitationId)
            .compose { rxTransformerManager.applyIOScheduler(it) }
            .doOnSubscribe { view.showLoading() }
            .doOnEach { view.hideLoading() }
            .subscribe({
                triggerRefresh()
            }, {
                view.showAlert(apiErrorParser.parse(it))
            })
    )

    private fun triggerRefresh() = triggerRefresh.onNext("onNext")

    private fun refreshFamily() =
        userDataStore.familyName().also {
            if (it.isNullOrEmpty()) {
                view.showCreateFamily()
            } else {
                view.showLeaveFamily(it)
            }
        }
}