package com.example.virtualfridge.domain.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.virtualfridge.R

// TODO: extract base logic to be able to handle multiple dialogs properly
class VfProgressDialog: AppCompatDialogFragment() {

    val dialogTag: String
        get() = TAG

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, theme)
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        return builder.show()
    }

    companion object {
        const val TAG = "ProgressDialog"
    }
}