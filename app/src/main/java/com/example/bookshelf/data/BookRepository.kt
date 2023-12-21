package com.example.bookshelf.data

import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookApiService

interface BookRepository {
    suspend fun getBooks(): List<Book>
}

class DefaultBookRepository(
    private val bookApiService: BookApiService
): BookRepository {
    override suspend fun getBooks(): List<Book> = bookApiService.getBooks()
}