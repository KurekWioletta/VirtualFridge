package com.example.virtualfridge.domain.board

import com.example.virtualfridge.data.api.NotesApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.board.notes.NotesViewModel
import com.example.virtualfridge.utils.RxTransformerManager
import javax.inject.Inject

class BoardFragmentPresenter @Inject constructor(
    private val view: BoardFragment,
    private val notesApi: NotesApi,
    private val userDataStore: UserDataStore,
    private val rxTransformerManager: RxTransformerManager
) {
    fun init() =
        view.registerViewSubscription(
            notesApi.notes(userDataStore.loggedInUser().id!!)
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .subscribe({
                    view.updateNotes(
                        listOf(
                            NotesViewModel(
                                "111",
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            ),
                            NotesViewModel(
                                "111",
                                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            ),
                            NotesViewModel(
                                "111",
                                "Ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            ),
                            NotesViewModel(
                                "111",
                                "Ggiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            )
                        )
                    )
                }, {
                    // TODO: handle error message
                    view.showAlert("ERROR")
                    // TODO: remove
                    view.updateNotes(
                        listOf(
                            NotesViewModel(
                                "111",
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            ),
                            NotesViewModel(
                                "111",
                                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            ),
                            NotesViewModel(
                                "111",
                                "Ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            ),
                            NotesViewModel(
                                "111",
                                "Ggiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            )
                        )
                    )
                })
        )

    fun deleteNoteClicked(noteId: String) =
        view.registerViewSubscription(
            notesApi.deleteNote(noteId)
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .subscribe({
                    init()
                }, {
                    // TODO: handle error message
                    view.showAlert("ERROR")
                })
        )

}