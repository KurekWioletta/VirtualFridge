package com.example.virtualfridge.domain.dialogs

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import javax.inject.Inject

class DialogManager @Inject constructor(
    private val fragmentManager: FragmentManager
){

    fun showDialog(dialog: VfProgressDialog) {
        dialog.show(fragmentManager, dialog.dialogTag)
    }

    fun dismissDialog(tag: String) {
        val dialogFragment = fragmentManager.findFragmentByTag(tag) as DialogFragment?
        dialogFragment?.dismissAllowingStateLoss()
    }
}