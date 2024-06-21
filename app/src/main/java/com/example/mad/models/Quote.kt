package com.example.mad.models

data class QuoteResponse(
    var success: Boolean? = null,
    val data: List<Quote>? = null
)

data class Quote(
    var id: Int? = null,
    var title: String? = null,
    var image: String? = null,
    var description: String? = null
)
