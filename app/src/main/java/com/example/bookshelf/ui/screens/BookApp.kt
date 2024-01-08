package com.example.bookshelf.ui.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.BookUiState
import com.example.bookshelf.model.BookViewModel
import com.example.bookshelf.network.Book

enum class Screen{
    List,
    Desc
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BookApp() {
    val bookViewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.List.name) {
        composable(route = Screen.List.name) {
            BookScreen(
                uiState = bookViewModel.bookUiState,
                onCardClicked = {navController.navigate(route = Screen.Desc.name)}
            )
        }
        composable(route = Screen.Desc.name) {
            BookDesc(book = TODO())
        }
    }
    //BookScreen(uiState = bookViewModel.bookUiState)
}

@Composable
fun BookScreen(uiState: BookUiState, onCardClicked: () -> Unit) { when (uiState) {
        is BookUiState.Loading -> LoadingScreen()
        is BookUiState.Success -> SuccessScreen(books = uiState.books, onCardClicked = onCardClicked)
        is BookUiState.Error -> ErrorScreen()
    }
}

@Composable
fun SuccessScreen(books: List<Book>, onCardClicked: () -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(items = books, key = { book -> book.id }) { book ->
            BookCard(book = book, onCardClicked = onCardClicked)
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    onCardClicked: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onCardClicked() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(
                        book.volumeInfo.imageLinks.thumbnail.replace(
                            "http", "https"
                        )
                    )
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.baseline_error_24),
                placeholder = painterResource(R.drawable.download),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // book title
                Text(
                    text = book.volumeInfo.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                // authors
                book.volumeInfo.authors.forEach { author ->
                    Text(
                        text = author,
                        //fontWeight = FontWeight.Thin,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(
                            top = 0.dp,
                            bottom = 8.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                    )
                }
            }

        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(R.drawable.download),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(100.dp),
            contentDescription = null
        )
    }
}

@Composable
fun ErrorScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Image(painter = painterResource(R.drawable.error), contentDescription = null)
        Icon(
            painterResource(R.drawable.baseline_error_24),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(100.dp),
            contentDescription = null
        )
    }
}

@Composable
fun BookDesc(book: Book) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(book.volumeInfo.imageLinks.thumbnail.replace("http", "https"))
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.baseline_error_24),
            placeholder = painterResource(R.drawable.download),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(0.8f)
                .padding(8.dp)
        )
        Text(text = book.volumeInfo.title)
        Row {
            book.volumeInfo.authors.forEach { author->
                Text(text = author, modifier = Modifier.padding(8.dp))
            }
        }
        Text(text = book.volumeInfo.description)
        Text(text = book.volumeInfo.publisher)
        Text(text = book.volumeInfo.publishYear.toString() + " year")
    }
}

val mockData = List(100) { Book(id = it.toString()) }

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BookScreenPreview() { SuccessScreen(books = mockData, onCardClicked = {}) }

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BookDescPreview() { BookDesc(book = mockData[0]) }