package com.example.bookshelf.data

import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookApiService
import com.example.bookshelf.network.Books

interface BookRepository {
    suspend fun getBooks(): Books
}

class DefaultBookRepository(
    private val bookApiService: BookApiService
): BookRepository {
    override suspend fun getBooks(): Books = bookApiService.getBooks()
}