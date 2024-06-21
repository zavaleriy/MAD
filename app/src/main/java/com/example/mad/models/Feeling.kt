package com.example.mad.models

data class FeelingResponse(
    var success: Boolean? = null,
    val data: List<Feeling>? = null
)

data class Feeling(
    var id: Int? = null,
    var title: String? = null,
    var position: Int? = null,
    var image: String? = null
)
