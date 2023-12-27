package com.example.bookshelf.network

import retrofit2.http.GET

private const val apiKey = "AIzaSyAWy1ogOv_S3woCsxo1BRwQWTw8_p_yUXo"

interface BookApiService {
    @GET("volumes?q=Goldfinch&key=$apiKey")
    suspend fun getBooks(): Books
}