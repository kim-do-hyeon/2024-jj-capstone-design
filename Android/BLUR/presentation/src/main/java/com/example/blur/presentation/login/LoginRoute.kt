package com.example.blur.presentation.Login

/**
 * @author soohwan.ok
 */
sealed class LoginRoute(
    val name:String
) {
    object WelcomeScreen : LoginRoute("WelcomeScreen")

    object LoginScreen : LoginRoute("LoginScreen")

    object SignUpScreen : LoginRoute("SignUpScreen")

    object FindPasswordScreen : LoginRoute("FindPasswordScreen")
}