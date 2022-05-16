/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.criticaltechworks.kgwen.demo.databinding.ActivityLoginBinding
import com.criticaltechworks.kgwen.demo.model.Profile

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var loggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoginButton()
    }

    override fun finish() {
        setResult(if (loggedIn) SUCCESS else FAILURE)
        super.finish()
    }

    private fun setupLoginButton() {
        with(binding) {
            emailField.addTextChangedListener { editable ->
                loginButton.isEnabled = !editable.isNullOrBlank() && passwordField.text.isNotEmpty()
            }
            passwordField.addTextChangedListener { editable ->
                loginButton.isEnabled = !editable.isNullOrEmpty() && emailField.text.isNotBlank()
            }
            with(loginButton) {
                isEnabled = false
                setOnClickListener {
                    loggedIn = Profile
                        .login(emailField.text.toString(), passwordField.text.toString())
                    if (loggedIn) finish() else showLoginError()
                }
            }
        }
    }

    private fun showLoginError() {
        Toast
            .makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        const val SUCCESS = 32
        const val FAILURE = -32
    }
}
