/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.message

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.transition.TransitionSet
import com.criticaltechworks.kgwen.demo.databinding.FragmentConversationBinding
import com.criticaltechworks.kgwen.demo.ktx.*

class ConversationFragment : Fragment() {
    private lateinit var binding: FragmentConversationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TransitionSet()
            .addTransition(inflateTransition(android.R.transition.fade))
            .addTransition(inflateTransition(android.R.transition.slide_right))
            .apply { duration = resources.shortAnimationTime }
            .also {
                enterTransition = it
                exitTransition = it
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConversationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startEnterTransitionOnPreDraw()
    }
}
