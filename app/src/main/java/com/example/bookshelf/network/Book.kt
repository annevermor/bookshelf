package com.example.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val kind: String,
)
