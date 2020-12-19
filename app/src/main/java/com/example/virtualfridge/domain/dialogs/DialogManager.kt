package com.example.virtualfridge.domain.dialogs

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import javax.inject.Inject

class DialogManager @Inject constructor(
    private val fragmentManager: FragmentManager
){

    // pokazujemy dialog
    fun showDialog(dialog: VfProgressDialog) {
        dialog.show(fragmentManager, dialog.dialogTag)
    }

    // chowamy dialog o konkretnym tagu
    fun dismissDialog(tag: String) {
        // dialogi to fragmenty wiec mozna je wychwycic przez fragmentManager
        val dialogFragment = fragmentManager.findFragmentByTag(tag) as DialogFragment?
        dialogFragment?.dismissAllowingStateLoss()
    }
}