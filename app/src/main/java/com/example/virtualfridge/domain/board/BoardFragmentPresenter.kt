package com.example.virtualfridge.domain.board

import com.example.virtualfridge.data.api.NotesApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.board.notes.NotesViewModel.Companion.fromResponse
import com.example.virtualfridge.utils.RxTransformerManager
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class BoardFragmentPresenter @Inject constructor(
    private val view: BoardFragment,
    private val notesApi: NotesApi,
    private val userDataStore: UserDataStore,
    private val rxTransformerManager: RxTransformerManager
) {

    private val triggerRefresh = BehaviorSubject.createDefault("")

    fun init() =
        view.registerViewSubscription(triggerRefresh.flatMap {
            notesApi.notes(userDataStore.loggedInUser().id!!)
                .map { fromResponse(it) }
                .compose { rxTransformerManager.applyIOScheduler(it) }
        }
            .compose { rxTransformerManager.applyIOScheduler(it) }
            .doOnSubscribe { view.showLoading() }
            .doOnEach { view.hideLoading() }
            .subscribe({ view.updateNotes(it) }, {
                // TODO: handle error message
                view.showAlert("ERROR")
            })
        )

    fun refreshBoard() = triggerRefresh.onNext("")

    fun deleteNoteClicked(noteId: String) =
        view.registerViewSubscription(
            notesApi.deleteNote(noteId)
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnEach { view.hideLoading() }
                .subscribe({
                    refreshBoard()
                }, {
                    // TODO: handle error message
                    view.showAlert("ERROR")
                })
        )

}