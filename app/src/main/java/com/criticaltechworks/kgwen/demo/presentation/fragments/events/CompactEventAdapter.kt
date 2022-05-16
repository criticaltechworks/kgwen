/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.events

import android.view.*
import androidx.recyclerview.widget.*
import com.criticaltechworks.kgwen.demo.databinding.EventItemCompactBinding
import com.criticaltechworks.kgwen.demo.ktx.monthInitials

class CompactEventAdapter : EventAdapter<CompactEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EventItemCompactBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .run { ViewHolder(this) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(getItem(holder.adapterPosition)) {
                eventTitle.text = title
                eventDay.text = date.dayOfMonth.toString()
                eventMonth.text = date.monthInitials
            }
        }
    }

    class ViewHolder(val binding: EventItemCompactBinding) : RecyclerView.ViewHolder(binding.root)
}
