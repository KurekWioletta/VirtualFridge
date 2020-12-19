package com.example.virtualfridge.utils

import android.content.Context
import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.models.ApiException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import javax.inject.Inject

// klasa sluzaca do parsowania error response z backendu (i tylko errorow)
// po stronie backendu rzucamy ApiException, ktore zawiera opis bledu, ktory pokazujemy
class ApiErrorParser @Inject constructor(
    private val context: Context,
    private val moshi: Moshi
) {
    fun parse(throwable: Throwable): String {
        return if (throwable is HttpException) {
            try {
                // do parsowania uzywamy moshi
                val jsonAdapter: JsonAdapter<ApiException> =
                    moshi.adapter<ApiException>(ApiException::class.java)
                val apiException =
                    jsonAdapter.fromJson(throwable.response()!!.errorBody()!!.string())

                if (apiException!!.message.isNullOrEmpty()) {
                    // jesli nie ma message to generyczny blad
                    context.getString(R.string.error_unknown)
                } else {
                    apiException!!.message!!
                }
            } catch (e: Exception) {
                context.getString(R.string.error_unknown)
            }
        } else {
            context.getString(R.string.error_unknown)
        }
    }
}