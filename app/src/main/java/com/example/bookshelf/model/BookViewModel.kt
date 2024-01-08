package com.example.bookshelf.model

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookApplication
import com.example.bookshelf.data.BookRepository
import com.example.bookshelf.network.Book
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookUiState {
    data class Success(val books: List<Book>, ) : BookUiState
    data object Loading : BookUiState
    data object Error : BookUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {
    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)

    init {
        getBooks()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getBooks() {
        viewModelScope.launch {
            bookUiState = try {
                BookUiState.Success(bookRepository.getBooks().items)
            } catch (e: IOException) {
                BookUiState.Error
            } catch (e: HttpException) {
                BookUiState.Error
            }
        }
    }

    fun setCurrentBook(book: Book){
        TODO()
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (
                        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                                as BookApplication
                        )
                val bookRepository = application.container.bookRepository
                BookViewModel(bookRepository = bookRepository)
            }
        }
    }
}