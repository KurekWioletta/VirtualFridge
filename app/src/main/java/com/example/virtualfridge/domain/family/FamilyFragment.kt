package com.example.virtualfridge.domain.family

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualfridge.domain.base.BaseFragment
import com.example.virtualfridge.domain.family.invitations.InvitationViewModel
import com.example.virtualfridge.utils.BaseValidationViewModel
import com.example.virtualfridge.utils.ViewComponentsAdapter
import com.example.virtualfridge.utils.ViewComponentsAdapter.Companion.INVITATIONS
import com.example.virtualfridge.utils.gone
import com.example.virtualfridge.utils.visible
import kotlinx.android.synthetic.main.fragment_family.*
import javax.inject.Inject

class FamilyFragment : BaseFragment() {

    @Inject
    lateinit var presenter: FamilyFragmentPresenter

    private lateinit var adapter: ViewComponentsAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = ViewComponentsAdapter(INVITATIONS, {
            presenter.acceptInvitationClicked(invitationId = it)
        }, {
            presenter.declineInvitationClicked(invitationId = it)
        })
        return inflater.inflate(com.example.virtualfridge.R.layout.fragment_family, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvInvitations.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvInvitations.adapter = adapter

        btnLeaveFamily.setOnClickListener {
            presenter.leaveFamilyClicked()
        }
        btnCreateFamily.setOnClickListener {
            presenter.createFamilyClicked(etFamilyName.text.toString())
        }
        btnInviteMember.setOnClickListener {
            presenter.inviteMemberClicked(etInviteEmail.text.toString())
        }

        presenter.init()
    }

    fun showLeaveFamily(familyName: String) {
        createFamilyContainer.gone()
        leaveFamilyContainer.visible()
        tvYourFamily.text = familyName
    }

    fun showCreateFamily() {
        createFamilyContainer.visible()
        leaveFamilyContainer.gone()
    }

    fun invitationSent() {
        etInviteEmail.text.clear()
        // TODO: Show message
    }

    fun updateInvitations(items: List<InvitationViewModel>) = adapter.setItems(items)

    fun showValidationResults(validationViewModel: ValidationViewModel) {
        etFamilyName.error = validationViewModel.familyNameError
        etInviteEmail.error = validationViewModel.inviteEmailError
    }

    data class ValidationViewModel(
        val familyNameError: String?,
        val inviteEmailError: String?
    ) : BaseValidationViewModel() {
        override fun toList() = listOf(familyNameError, inviteEmailError)
    }
}