package com.example.virtualfridge.domain.createNote

import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.FamilyApi
import com.example.virtualfridge.data.api.NotesApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.calendar.CalendarFragment
import com.example.virtualfridge.domain.calendar.CalendarFragment.FamilyMemberViewModel.Companion.fromResponse
import com.example.virtualfridge.domain.createNote.CreateNoteActivity.Companion.RC_CREATE_NOTE
import com.example.virtualfridge.domain.createNote.CreateNoteActivity.ValidationViewModel
import com.example.virtualfridge.utils.RxTransformerManager
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import javax.inject.Inject

class CreateNoteActivityPresenter @Inject constructor(
    private val view: CreateNoteActivity,
    private val notesApi: NotesApi,
    private val familyApi: FamilyApi,
    private val rxTransformerManager: RxTransformerManager,
    private val userDataStore: UserDataStore
) {
    fun init() =
        view.registerViewSubscription(
            familyApi.familyMembers(userDataStore.loggedInUser().id!!)
                .map { fromResponse(it) }
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnEach { view.hideLoading() }
                .subscribe({ familyMembers ->
                    view.setUpSpinner(familyMembers)
                }, {
                    view.showAlert("ERROR")
                    view.setUpSpinner(
                        listOf(
                            CalendarFragment.FamilyMemberViewModel("1", "Jan", "Kowalski"),
                            CalendarFragment.FamilyMemberViewModel("2", "Ania", "Kowalska")
                        )
                    )
                })
        )

    fun createNoteClicked(familyMemberId: String, note: String) {
        val validationViewModel = ValidationViewModel(
            note.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            view.registerViewSubscription(
                notesApi.createNote(userDataStore.loggedInUser().id!!, familyMemberId, note)
                    .compose { rxTransformerManager.applyIOScheduler(it) }
                    .doOnSubscribe { view.showLoading() }
                    .doOnEach { view.hideLoading() }
                    .subscribe({
                        view.setResult(RC_CREATE_NOTE)
                        view.finish()
                    }, {
                        view.setResult(RC_CREATE_NOTE)
                        view.finish()
                    })
            )
        }
    }
}