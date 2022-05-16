/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.events

import android.annotation.SuppressLint
import androidx.recyclerview.widget.*
import com.criticaltechworks.kgwen.demo.model.Event

abstract class EventAdapter<VH : RecyclerView.ViewHolder> : ListAdapter<Event, VH>(DiffCallback()) {
    @SuppressLint("NotifyDataSetChanged")
    fun update(events: List<Event>) {
        submitList(events)
        notifyDataSetChanged()
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Event, newItem: Event) =
            oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.tags == newItem.tags &&
                    oldItem.date == newItem.date
    }
}