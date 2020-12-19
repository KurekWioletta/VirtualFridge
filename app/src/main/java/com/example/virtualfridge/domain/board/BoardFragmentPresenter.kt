package com.example.virtualfridge.domain.board

import com.example.virtualfridge.data.api.NotesApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.board.notes.NotesViewModel.Companion.fromResponse
import com.example.virtualfridge.utils.ApiErrorParser
import com.example.virtualfridge.utils.RxTransformerManager
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class BoardFragmentPresenter @Inject constructor(
    private val view: BoardFragment,
    private val notesApi: NotesApi,
    private val userDataStore: UserDataStore,
    private val apiErrorParser: ApiErrorParser,
    private val rxTransformerManager: RxTransformerManager
) {

    // hot and cold observables rxJava
    // https://medium.com/@luukgruijs/understanding-hot-vs-cold-observables-62d04cf92e03
    // ten obiekt bedzie nam emitowal eventy kiedy bedzie trzeba odswiezyc liste notatek
    // jest kilka rodzai subject - BehaviourSubject oznacza ze pierwsza emisja eventu rowniez bedzie wzieta pod uwage nawet jesli
    // zasubskrybujemy sie do niego pozniej, potrzebujemy tak zeby na starcie apki tez cos sie pokazalo
    private val triggerRefresh = BehaviorSubject.createDefault("")

    // zapytanie na backend o notatki
    fun init() =
        // mamy subskrybcje triggerRefresh, ktora jesli wyemituje event
        view.registerViewSubscription(triggerRefresh.flatMap {
            // zostaje zmapowana do Observable<List<NoteResponse>> ktore otrzymujemy z zapyttania na backend
            notesApi.notes(userDataStore.loggedInUser().id)
                // mapujemy sobie to na ViewModel do RecyclerVIEW
                .map { fromResponse(it) }
                // wrzucamy powyzsze operatory na IO thread, a ponizsze na mainThread
                .compose { rxTransformerManager.applyIOScheduler(it) }
        }
            // wrzucamy powyzsze operatory na IO thread, a ponizsze na mainThread
            .compose { rxTransformerManager.applyIOScheduler(it) }
            // pokazujemy progressBar  w momencie subskrybcji
            .doOnSubscribe { view.showLoading() }
            // chowamy progressBar w momencie otrzymania wyemitowanego eventy
            .doOnEach { view.hideLoading() }
            // na koniec robimy update notatek z otrzymanych ViewModeli lub lapiemy error
            .subscribe({ view.updateNotes(it) }, {
                view.showAlert(apiErrorParser.parse(it))
            })
        )

    // na odswiezeniu boarda emitujemy event dla obiektu triggerRefresh ktory jest obserwowany (w init() zaczelismy go obserwowac)
    // wiec caly ciag rxowy zdefiniowany w init() bedzie uruchoomiony jakby ponownie
    fun refreshBoard() = triggerRefresh.onNext("")

    fun deleteNoteClicked(noteId: String) =
        view.registerViewSubscription(
            // zapytanie na backend o usuniecie notki
            notesApi.deleteNote(noteId)
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnEach { view.hideLoading() }
                .subscribe({
                    // jak jest sukces to sobie odswiezamy board raz jeszcze
                    refreshBoard()
                }, {
                    view.showAlert(apiErrorParser.parse(it))
                })
        )

}