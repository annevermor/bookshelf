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
    val id: String = "023843",
    val etag: String = "JHKSdfie",
    val selfLink: String = "selfLink",
    val volumeInfo: VolumeInfo = VolumeInfo()
)

@Serializable
data class VolumeInfo(
    val title: String = "Great adventure",
    val authors: List<String> = listOf("Anton Novikov", "Daria Budarina"),
    val publisher: String = "AFB House",
    val publishYear: Int = 2023,
    val description: String = "description placeholder",
    val imageLinks: Cover = Cover()
)

@Serializable
data class Cover(
    val smallThumbnail: String = "http://books.google.com/books/content?id=JyW2DwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
    val thumbnail: String = "http://books.google.com/books/content?id=JyW2DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
)
