package com.example.virtualfridge.domain.family

import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.FamilyApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.family.FamilyFragment.ValidationViewModel
import com.example.virtualfridge.domain.family.invitations.InvitationViewModel
import com.example.virtualfridge.domain.family.invitations.InvitationViewModel.Companion.fromResponse
import com.example.virtualfridge.utils.RxTransformerManager
import com.example.virtualfridge.utils.isValidEmail
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import javax.inject.Inject

class FamilyFragmentPresenter @Inject constructor(
    private val view: FamilyFragment,
    private val familyApi: FamilyApi,
    private val userDataStore: UserDataStore,
    private val rxTransformerManager: RxTransformerManager
) {

    fun init() {
        userDataStore.familyName().also {
            if (it.isNullOrEmpty()) {
                view.showCreateFamily()
            } else {
                view.showLeaveFamily(it)
            }
        }

        view.registerViewSubscription(
            familyApi.invitations(userDataStore.loggedInUser().id!!)
                .map { fromResponse(it) }
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnEach { view.hideLoading() }
                .subscribe({ view.updateInvitations(it) }, {
                    // TODO: handle error message
                    view.showAlert("ERROR")
                    view.updateInvitations(
                        listOf(
                            InvitationViewModel("id1", "Mock1"),
                            InvitationViewModel("id2", "Mock2"),
                            InvitationViewModel("id3", "Mock3")
                        )
                    )
                })
        )
    }

    fun leaveFamilyClicked() = view.registerViewSubscription(
        familyApi.leaveFamily(userDataStore.loggedInUser().id!!)
            .compose { rxTransformerManager.applyIOScheduler(it) }
            .doOnSubscribe { view.showLoading() }
            .doOnEach { view.hideLoading() }
            .subscribe({
                userDataStore.removeFromFamily()
                init()
            }, {
                // TODO: handle error message
                view.showAlert("ERROR")
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
                familyApi.createFamily(userDataStore.loggedInUser().id!!, familyName)
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { view.showLoading() }
                    .doOnEach { view.hideLoading() }
                    .subscribe({ fName ->
                        userDataStore.addToFamily(fName)
                        init()
                    }, {
                        // TODO: handle error message
                        view.showAlert("ERROR")
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
                familyApi.inviteMember(userDataStore.loggedInUser().id!!, email)
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { view.showLoading() }
                    .doOnEach { view.hideLoading() }
                    .subscribe({
                        // TODO: show message
                        view.showAlert(view.getString(R.string.family_family_member_invited))
                    }, {
                        // TODO: handle error message
                        view.showAlert("ERROR")
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
                        // TODO: refresh
                        userDataStore.addToFamily(familyName)
                        init()
                    }, {
                        // TODO: handle error message
                        view.showAlert("ERROR")
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
                // TODO: refresh
                init()
            }, {
                // TODO: handle error message
                view.showAlert("ERROR")
            })
    )
}