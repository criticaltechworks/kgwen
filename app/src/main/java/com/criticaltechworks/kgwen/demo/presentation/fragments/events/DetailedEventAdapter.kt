/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.events

import android.view.*
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.criticaltechworks.kgwen.demo.databinding.EventItemDetailedBinding
import com.criticaltechworks.kgwen.demo.ktx.monthInitials
import com.google.android.material.chip.Chip

class DetailedEventAdapter : EventAdapter<DetailedEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EventItemDetailedBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .run { ViewHolder(this) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(getItem(holder.adapterPosition)) {
                eventDay.text = date.dayOfMonth.toString()
                eventMonth.text = date.monthInitials
                eventTitle.text = title
                eventDescription.text = description
                eventTags.removeAllViews()
                tags
                    .apply {
                        eventTags.isGone = isEmpty()
                        takeIf { isNotEmpty() }
                    }
                    .forEach {
                        if (it.isBlank()) return@forEach
                        Chip(eventTags.context)
                            .apply { text = it }
                            .also { eventTags.addView(it) }
                    }
            }
        }
    }

    class ViewHolder(val binding: EventItemDetailedBinding) : RecyclerView.ViewHolder(binding.root)
}