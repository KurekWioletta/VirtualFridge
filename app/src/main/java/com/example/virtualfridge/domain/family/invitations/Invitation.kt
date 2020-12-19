package com.example.virtualfridge.domain.family.invitations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.virtualfridge.R
import com.example.virtualfridge.data.api.models.InvitationResponse
import com.example.virtualfridge.utils.BaseViewComponentsViewHolder
import com.example.virtualfridge.utils.BaseViewComponentsViewModel
import kotlinx.android.synthetic.main.family_item_invitation.view.*

data class InvitationViewModel(
    val invitationId: String,
    val familyName: String
) : BaseViewComponentsViewModel() {

    companion object {
        fun fromResponse(response: List<InvitationResponse>) = response.map {
            InvitationViewModel(it.id, it.familyName)
        }
    }

}

class InvitationViewHolder(
    private val acceptInvitationClick: ((String) -> Unit),
    private val declineInvitationClick: ((String) -> Unit),
    parent: ViewGroup
) : BaseViewComponentsViewHolder(
    // inflatowanie layoutu dla konkretnego viewHolderu
    LayoutInflater.from(parent.context).inflate(R.layout.family_item_invitation, parent, false)
) {
    override fun bind(item: BaseViewComponentsViewModel) {
        item as InvitationViewModel
        itemView.tvFamilyName.text = item.familyName
        itemView.btnAcceptInvitation.setOnClickListener {
            acceptInvitationClick.invoke(item.invitationId)
        }
        itemView.btnDeclineInvitation.setOnClickListener {
            declineInvitationClick.invoke(item.invitationId)
        }
    }
}
