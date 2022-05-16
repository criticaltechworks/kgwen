/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.composables

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.*
import androidx.compose.ui.semantics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.model.*

//region Composables
/** A complete conversation screen with conversation bubbles and message field. */
@Composable
fun Conversation(conversation: Conversation, onSend: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val lastMessageIndex = conversation.messages.lastIndex.coerceAtLeast(0)

    LaunchedEffect(key1 = Unit) { listState.scrollToItem(lastMessageIndex) }
    LaunchedEffect(lastMessageIndex) { listState.animateScrollToItem(lastMessageIndex) }

    Column {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = 20.dp),
            modifier = Modifier
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures {
                        isFocused = false
                        localFocusManager.clearFocus()
                    }
                }
        ) {
            itemsIndexed(conversation.messages) { index, message ->
                ConversationBubble(
                    message = message,
                    received = message.receivedIn(conversation),
                    lastSender = conversation.messages.getOrNull(index - 1)?.sender
                )
            }
        }

        MessageField(isFocused = isFocused, onFocusChange = { isFocused = it }, onSend = onSend)
    }
}

/**
 * A single conversation bubble that is displayed on the leading edge if it was [received], or on
 * the trailing edge, otherwise. For received messages, if the [lastSender] is not repeated, a
 * profile picture will be displayed.
 */
@Composable
fun ConversationBubble(message: Conversation.Message, received: Boolean, lastSender: User?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .padding(bottom = 5.dp)
    ) {
        if (received) {
            ProfilePicture(user = message.sender, lastSender)

            Spacer(modifier = Modifier.width(5.dp))

            Box(modifier = Modifier.weight(0.75f)) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.25f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            Spacer(modifier = Modifier.weight(0.25f))
        } else {
            Spacer(modifier = Modifier.weight(0.25f))

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.weight(0.75f)
            ) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(AppColors.PrimaryDark)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

/** An image adequate for displaying profile pictures beside text in a conversation bubble. */
@Composable
fun ProfilePicture(user: User, lastUser: User?) {
    if (user.email != lastUser?.email) {
        Image(
            painterResource(id = user.picture),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
        )
    } else {
        Spacer(modifier = Modifier.size(35.dp))
    }
}

/** The input controls for typing a new message. */
@Composable
fun MessageField(isFocused: Boolean, onFocusChange: (Boolean) -> Unit, onSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
                .withDescription(R.string.message_attachement)
        ) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = null,
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.secondary)
                    .padding(10.dp)
            )
        }

        Card(
            shape = RoundedCornerShape(25.dp),
            elevation = 3.dp,
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(if (isFocused) 1f else 0.75f)
                .padding(10.dp),
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text(stringResource(R.string.new_message)) },
                trailingIcon = {
                    Column {
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            enabled = message.isNotEmpty(),
                            onClick = {
                                onSend(message)
                                message = ""
                            },
                            modifier = Modifier.withDescription(R.string.send_message)
                        ) {
                            Icon(Icons.Rounded.Send, contentDescription = null)
                        }
                    }
                },
                maxLines = if (isFocused) 5 else 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    trailingIconColor = MaterialTheme.colors.primaryVariant
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onFocusChange(false) }
                ),
                modifier = Modifier
                    .onFocusEvent { onFocusChange(it.isFocused) }
                    .height(if (isFocused) 125.dp else 55.dp)
            )
        }
    }
}
//endregion

//region Previews
@Composable
fun MessageFieldPreview() {
    AppTheme {
        Column {
            MessageField(isFocused = false, onFocusChange = { }, onSend = { })
            MessageField(isFocused = true, onFocusChange = { }, onSend = { })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageFieldLightPreview() {
    MessageFieldPreview()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MessageFieldDarkPreview() {
    MessageFieldPreview()
}

@Composable
fun ConversationPreview() {
    AppTheme {
        Conversation(conversation = SampleData.CONVERSATIONS.first(), onSend = { })
    }
}

@Preview(showBackground = true)
@Composable
fun ConversationLightPreview() {
    ConversationPreview()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ConversationDarkPreview() {
    ConversationPreview()
}
//endregion
