package com.example.bookshelf.ui.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.BookUiState
import com.example.bookshelf.model.BookViewModel
import com.example.bookshelf.network.Book



@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BookApp() {
    val bookViewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)
    //val mockData = List(100) { Book(kind = it.toString()) }
    BookScreen(uiState = bookViewModel.bookUiState)
}

@Composable
fun BookScreen(uiState: BookUiState) {
    when(uiState){
        is BookUiState.Loading -> LoadingScreen()
        is BookUiState.Success -> SuccessScreen(books = uiState.books)
        is BookUiState.Error -> ErrorScreen()
    }
}

@Composable
fun SuccessScreen(books: List<Book>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(items = books, key = { book -> book.id }) { book ->
            BookCard(book = book)
        }
    }
}


@Composable
fun BookCard(book: Book) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("TODO")
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.error),
                placeholder = painterResource(R.drawable.loading),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = book.volumeInfo.title,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = book.volumeInfo.author,
                modifier = Modifier.padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            )
        }
    }
}

@Composable
fun LoadingScreen(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(R.drawable.loading), contentDescription = null)
    }
}

@Composable
fun ErrorScreen(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(R.drawable.error), contentDescription = null)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BookScreenPreview() {
    val mockData = List(100) { Book(kind = it.toString()) }
    SuccessScreen(books = mockData)
}