package com.example.mad.models

import java.util.UUID

data class UserLogin(
    var email: String? = null,
    var password: String? = null
)

data class User (
    var id: UUID? = null,
    var email: String? = null,
    var nickName: String? = null,
    var avatar: String? = null,
    var token: String? = null
)