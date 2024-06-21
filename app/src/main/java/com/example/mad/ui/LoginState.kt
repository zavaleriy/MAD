package com.example.mad.ui

sealed class LoginState {
    object Load : LoginState()
    object ValidationError : LoginState()
    object ResponseError : LoginState()
    object UserRequest : LoginState()
}