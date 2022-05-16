/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.criticaltechworks.kgwen.demo.model.*

/** A conversation preview that to be used as an entry in a message list. */
@Composable
fun ConversationItem(conversation: Conversation, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = conversation.user.picture),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .shadow(elevation = 10.dp, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = conversation.user.displayName,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colors.primary
                )

                Spacer(Modifier.weight(1f))

                Icon(Icons.Rounded.ChevronRight, contentDescription = "")
            }

            Spacer(Modifier.height(5.dp))


            Text(
                text = conversation.messages.lastOrNull()?.content ?: "",
                style = MaterialTheme.typography.caption,
                maxLines = 3,
                modifier = Modifier.alpha(ContentAlpha.disabled)
            )
        }
    }
}

@Composable
private fun ConversationEntryPreview() {
    AppTheme {
        ConversationItem(conversation = SampleData.CONVERSATIONS.first())
    }
}

@Preview(showBackground = true)
@Composable
fun ConversationEntryLightPreview() {
    ConversationEntryPreview()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ConversationEntryDarkPreview() {
    ConversationEntryPreview()
}
