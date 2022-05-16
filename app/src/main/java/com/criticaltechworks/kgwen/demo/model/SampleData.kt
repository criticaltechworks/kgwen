/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.model

import com.criticaltechworks.kgwen.demo.R
import java.time.LocalDate

object SampleData {
    val EVENTS = listOf(
        Event(
            title = "Secret Meeting",
            description = "Important meeting with mysterious stakeholders where relevant topics " +
                    "will be addressed and impactful decisions will be taken in order to achieve " +
                    "the goals set in place from previous meetings",
            date = LocalDate.of(2022, 4, 21),
            tags = listOf("important", "mysterious", "meeting")
        ),
        Event(
            title = "Joyful Presentation",
            description = "A seriously joyful presentation about ice cream and cookies and how " +
                    "they can change the world. We'll also tackle the role of coffee as a " +
                    "powerful coadjuvant to world peace.",
            date = LocalDate.of(2022, 4, 22),
            tags = listOf("presentation", "joyful", "ice cream", "cookies")
        ),
        Event(
            title = "Tischfußball at the Office",
            description = " It doesn't matter whether you call it matrecos, Tischfußball, table " +
                    "soccer or something else entirely, this is a major event where people all " +
                    "over the company come together to celebrate their love for the same sport. " +
                    "Don't forget to bring your soccer cleats for full immersion!",
            date = LocalDate.of(2022, 4, 23),
            tags = listOf("matrecos", "tischfußball", "meet up")
        ),
        Event(
            title = "Arctic Circle Trip",
            description = "Business trip to the Arctic Circle for further discussions on the " +
                    "acquisition of CoolCoolants Corps. It will include a demo of the walk-in " +
                    "fridge unit which is set to revolutionize the segment of large house fridges.",
            date = LocalDate.of(2022, 4, 25),
            tags = listOf("business trip", "acquisition", "cool demo")
        ),
    )

    object Users {
        val StarProtegee = User(
            email = "starprotegee@ctw.com",
            name = "Star",
            surname = "Protegée",
            picture = R.drawable.star_protegee
        )

        val ScrumKnight = User(
            email = "scrumknight@ctw.com",
            name = "Scrum",
            surname = "Knight",
            picture = R.drawable.scrum_knight,
        )

        val All = listOf(
            StarProtegee,
            ScrumKnight
        )
    }

    val CONVERSATIONS = listOf(
        Conversation(
            user = Users.StarProtegee,
            messages = mutableListOf(
                Conversation.Message(
                    sender = Users.StarProtegee,
                    content = "Hi Scrum Knight! Do you know where I can find some armor that's " +
                            "good against Segmentation Fault?"
                ),
                Conversation.Message(
                    sender = Users.StarProtegee,
                    content = "I am really struggling 😅"
                ),
                Conversation.Message(
                    sender = Users.ScrumKnight,
                    content = "Hey Padawan! In the smart pointer a path to the Force you'll find."
                ),
                Conversation.Message(
                    sender = Users.StarProtegee,
                    content = "Thanks! 🙏"
                )
            )
        ),
        Conversation(
            user = Users.ScrumKnight,
            messages = mutableListOf(
                Conversation.Message(
                    sender = Users.ScrumKnight,
                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque " +
                            "sit amet ex urna. Duis viverra nisl non nisi iaculis, vel suscipit " +
                            "sem condimentum."
                ),
                Conversation.Message(
                    sender = Users.ScrumKnight,
                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque " +
                            "sit amet ex urna. Duis viverra nisl non nisi iaculis, vel suscipit " +
                            "sem condimentum."
                )
            )
        )
    )

    // Function, so a new id is generated every time
    fun notifications() = listOf(
        Notification.Call(user = Users.ScrumKnight),
        Notification.Message(
            user = Users.StarProtegee,
            message = "Hi there! Can I use a goto?"
        ),
        Notification.Event(
            title = "Daily Scrum",
            description = "Daily Scrum starts in 10 minutes"
        )
    )

    object Cars {
        private val M8 =
            Car(brand = Car.Brand.BMW, model = "M8 Competition", picture = R.drawable.m8)

        private val MiniPacesetter =
            Car(brand = Car.Brand.MINI, model = "Mini Pacesetter", R.drawable.mini_pacesetter)

        private val I4M50 =
            Car(brand = Car.Brand.BMW, model = "i4 M50", picture = R.drawable.i4)

        val ALL = listOf(M8, MiniPacesetter, I4M50)
    }
}
