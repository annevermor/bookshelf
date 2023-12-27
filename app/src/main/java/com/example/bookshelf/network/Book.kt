package com.example.bookshelf.network

import kotlinx.serialization.Serializable

@Serializable
data class Books(
    val kind: String,
    val totalItems: Int,
    val items: List<Book>
)

@Serializable
data class Book(
    val kind: String = "kind",
    val id: String = "id",
    val etag: String = "etag",
    val selfLink: String = "selfLink",
    val volumeInfo: VolumeInfo = VolumeInfo()
)

@Serializable
data class VolumeInfo(
    val title: String = "title",
    val author: String = "author",
    val publisher: String = "publisher",
    val publishYear: Int = 2000
)
