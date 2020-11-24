package com.example.virtualfridge.domain.createNote

import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.NotesApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.calendar.CalendarFragment
import com.example.virtualfridge.domain.createNote.CreateNoteActivity.Companion.RC_CREATE_NOTE
import com.example.virtualfridge.domain.createNote.CreateNoteActivity.ValidationViewModel
import com.example.virtualfridge.utils.RxTransformerManager
import com.example.virtualfridge.utils.validate
import com.example.virtualfridge.utils.validationResult
import javax.inject.Inject

class CreateNoteActivityPresenter @Inject constructor(
    private val view: CreateNoteActivity,
    private val notesApi: NotesApi,
    private val rxTransformerManager: RxTransformerManager,
    private val userDataStore: UserDataStore
) {
    fun init() {
        view.setUpSpinner(
            listOf(
                CalendarFragment.FamilyMember("1", "Jan", "Kowalski"),
                CalendarFragment.FamilyMember("2", "Ania", "Kowalska")
            )
        )
    }

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
                    .doOnTerminate { view.hideLoading() }
                    .subscribe({
                        view.setResult(RC_CREATE_NOTE)
                        view.finish()
                    }, {
                        view.setResult(RC_CREATE_NOTE)
                        view.finish()
                        view.showAlert("ERROR")
                    })
            )
        }
    }
}