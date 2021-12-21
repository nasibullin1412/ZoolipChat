package com.homework.coursework.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.homework.coursework.databinding.ItemPeopleBinding
import com.homework.coursework.presentation.adapter.data.UserItem

class PeopleViewHolder(private val viewBinding: ItemPeopleBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(userItem: UserItem) {
        with(viewBinding) {
            imgUserAvatar.load(userItem.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvEmail.text = userItem.userMail
            tvUserName.text = userItem.name
            if (userItem.isAdmin) {
                tvAdmin.visibility = View.VISIBLE
            } else {
                tvAdmin.visibility = View.GONE
            }
        }
    }
}
