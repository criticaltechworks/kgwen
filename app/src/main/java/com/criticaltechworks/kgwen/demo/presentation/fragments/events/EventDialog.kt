/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.events

import android.app.AlertDialog
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.*
import com.criticaltechworks.kgwen.demo.databinding.EventDialogBinding
import com.criticaltechworks.kgwen.demo.ktx.positiveButton
import com.criticaltechworks.kgwen.demo.model.Event
import com.criticaltechworks.kgwen.demo.viewmodels.EventsViewModel
import java.time.LocalDate

class EventDialog : DialogFragment() {
    private val viewModel: EventsViewModel by activityViewModels()
    private lateinit var binding: EventDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog = AlertDialog
        .Builder(requireActivity())
        .apply {
            setTitle("Create new event")
            createContent()
            setView(binding.root)
            setPositiveButton("Create") { _, _ -> addEvent() }
            setNegativeButton("Cancel") { _, _ -> }
        }
        .run { create() }

    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog).positiveButton.isEnabled = false
    }

    private fun createContent() {
        binding = EventDialogBinding.inflate(requireActivity().layoutInflater)
        binding.eventTitle.addTextChangedListener { editable ->
            (dialog as? AlertDialog)?.positiveButton?.isEnabled = editable.toString().isNotBlank()
        }
    }

    private fun addEvent() {
        viewModel.add(
            with(binding) {
                Event(
                    title = eventTitle.text.toString(),
                    description = eventDescription.text.toString(),
                    date = with(datePicker) { LocalDate.of(year, month + 1, dayOfMonth) },
                    tags = eventTags.text.toString().split(",").onEach { it.trim() }
                )
            }
        )
    }

    companion object {
        const val TAG = "EventDialog"
    }
}
