/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.*
import androidx.lifecycle.*
import com.criticaltechworks.kgwen.demo.presentation.composables.*
import com.criticaltechworks.kgwen.demo.presentation.activities.MainActivity
import com.criticaltechworks.kgwen.demo.viewmodels.MessagesViewModel

/**
 * Compose [View] wrapper for [Conversation] composable.
 */
class ConversationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val viewModel: MessagesViewModel by lazy {
        ViewModelProvider(context as MainActivity).get()
    }

    init {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    }

    // Workaround for exitTransition which inadvertently calls super.
    override fun addView(child: View?, index: Int) {}

    @Composable
    override fun Content() {
        AppTheme {
            viewModel.conversation?.let { conversation ->
                Conversation(conversation = conversation, onSend = { viewModel.send(it) })
            }
        }
    }
}
