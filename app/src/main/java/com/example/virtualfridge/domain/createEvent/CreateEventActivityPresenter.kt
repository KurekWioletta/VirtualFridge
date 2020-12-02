package com.example.virtualfridge.domain.createEvent

import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.EventsApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.domain.createEvent.CreateEventActivity.Companion.RC_CREATE_EVENT
import com.example.virtualfridge.utils.*
import javax.inject.Inject

class CreateEventActivityPresenter @Inject constructor(
    private val view: CreateEventActivity,
    private val eventsApi: EventsApi,
    private val userDataStore: UserDataStore,
    private val apiErrorParser: ApiErrorParser,
    private val rxTransformerManager: RxTransformerManager
) {

    fun createEventClicked(
        title: String,
        description: String,
        place: String,
        startDate: String,
        endDate: String
    ) {
        val validationViewModel = CreateEventActivity.ValidationViewModel(
            title.validate(view.getString(R.string.error_field_required)) { it.isNotEmpty() },
            startDate.validate(view.getString(R.string.error_date)) { it.isValidDate() },
            endDate.validate(view.getString(R.string.error_date)) { it.isValidDate() }
        )

        view.showValidationResults(validationViewModel)
        if (validationViewModel.validationResult()) {
            view.registerViewSubscription(eventsApi.createEvent(
                userDataStore.loggedInUser().id,
                title,
                description,
                place,
                startDate,
                endDate
            )
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .doOnSubscribe { view.showLoading() }
                .doOnEach { view.hideLoading() }
                .subscribe({
                    view.setResult(RC_CREATE_EVENT)
                    view.finish()
                }, {
                    view.showAlert(apiErrorParser.parse(it))
                })
            )
        }
    }
}