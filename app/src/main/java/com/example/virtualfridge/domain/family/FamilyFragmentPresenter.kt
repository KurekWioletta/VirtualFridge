package com.example.virtualfridge.domain.family

import com.example.virtualfridge.R
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.family.FamilyFragment.ValidationViewModel
import com.example.virtualfridge.domain.family.invitations.InvitationViewModel
import com.example.virtualfridge.utils.isValidEmail
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import javax.inject.Inject

class FamilyFragmentPresenter @Inject constructor(
    private val view: FamilyFragment,
    private val userDataStore: UserDataStore
) {

    fun reload() {
        userDataStore.familyName().also {
            if (it.isNullOrEmpty()) {
                view.showCreateFamily()
            } else {
                view.showLeaveFamily(it)
            }
        }
        // TODO: get invitations from backend
        view.updateInvitations(
            listOf(
                InvitationViewModel("id1", "Mock1"),
                InvitationViewModel("id2", "Mock2"),
                InvitationViewModel("id3", "Mock3")
            )
        )
    }

    fun leaveFamilyClicked() {
        // TODO: call leave family
        // TODO: refresh view
        userDataStore.removeFromFamily()
        reload()
    }

    fun createFamilyClicked(familyName: String) {
        val validationViewModel = ValidationViewModel(
            familyName.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() },
            null
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            // TODO: Create family
            // TODO: use familyName from response
            userDataStore.addToFamily(familyName)
            reload()
        }
    }

    fun inviteMemberClicked(email: String) {
        val validationViewModel = ValidationViewModel(
            null,
            email.validate(view.getString(R.string.error_email)) { it.isValidEmail() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            // TODO: Invite member
        }
    }

    fun acceptInvitationClicked(invitationId: String) {
        userDataStore.familyName().also {
            if (it.isNullOrEmpty()) {
                // TODO: accept invitation
                reload()
            } else {
                view.showAlert(view.getString(R.string.family_leave_family_alert))
            }
        }
    }

    fun declineInvitationClicked(invitationId: String) {
        reload()
    }
}