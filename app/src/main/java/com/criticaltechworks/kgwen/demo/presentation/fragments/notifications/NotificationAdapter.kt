/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.notifications

import android.view.*
import androidx.core.view.isGone
import androidx.recyclerview.widget.*
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.databinding.NotificationItemBinding
import com.criticaltechworks.kgwen.demo.ktx.*
import com.criticaltechworks.kgwen.demo.model.Notification

class NotificationAdapter(
    val onMarkClick: (Int, Boolean) -> Unit,
    val onDeleteClick: (Int) -> Unit
) : ListAdapter<Notification, NotificationAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotificationItemBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .run { ViewHolder(this) }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(getItem(holder.adapterPosition)) {
                notificationBadge.isGone = read
                notificationTitle.text = title
                notificationContent.text = content
                notificationIcon.setImageResource(icon)
                notificationIcon.contentDescription = iconDescription
                readUnreadIcon.setImageResource(if (read) R.drawable.ic_unread else R.drawable.ic_read)
                with(markButton) {
                    setOnClickListener { onMarkClick(holder.adapterPosition, !read) }
                    contentDescription = markButtonDescription(holder.binding.root.context)
                }
                deleteNotificationButton.setOneTimeClickListener { onDeleteClick(holder.adapterPosition) }
            }
        }
    }

    class ViewHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem.id == newItem.id &&
                    oldItem.read == newItem.read &&
                    oldItem.title == newItem.title &&
                    oldItem.content == newItem.content &&
                    oldItem.icon == newItem.icon
    }
}
