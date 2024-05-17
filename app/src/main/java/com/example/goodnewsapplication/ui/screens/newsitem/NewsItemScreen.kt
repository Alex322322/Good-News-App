package com.example.goodnewsapplication.ui.screens.newsitem

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.goodnewsapplication.R
import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.domain.news.NewsImage
import com.example.goodnewsapplication.ui.AppViewModelProvider
import com.example.goodnewsapplication.ui.screens.home.HomeViewModel
import com.example.goodnewsapplication.ui.screens.home.toNewsEntity

@Composable
fun NewsItemScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val newsItemUiState = viewModel.newsItemUiState.collectAsState()
    BackHandler {
        onBackPressed()
    }
    Scaffold(
        topBar = { NewsDetailsScreenTopBar(onBackButtonClicked = onBackPressed) },
        modifier = modifier,
    ) { padding ->
        NewsItemScreenBody(
            selectedNews = newsItemUiState.value.newsItemDetails.toNewsEntity(),
            onLikePressed = { viewModel.updateLikesNewsItem() },
            onReadLaterPressed = { viewModel.updateReadLaterNewsItem() },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun NewsItemScreenBody(
    selectedNews: NewsEntity,
    onLikePressed: () -> Unit,
    onReadLaterPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(
                top = dimensionResource(id = R.dimen.padding_small),
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium)
            )
    ) {
        Card(
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(id = R.dimen.image_item_screen_size))
                .padding(bottom = dimensionResource(id = R.dimen.padding_small))
        ) {
            Image(
                painter = painterResource(NewsImage.bindNewsWithImages(selectedNews.id).imageRes),
                contentDescription = NewsImage.bindNewsWithImages(selectedNews.id).imageDescription,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
                )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedNews.author,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
                )
                IconButton(onClick = { onReadLaterPressed() })
                {
                    Icon(
                        imageVector = if(selectedNews.later) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = stringResource(id = R.string.readlater_icon)
                    )
                }
            }
            Text(
                text = selectedNews.label,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
            )
            Text(
                text = selectedNews.text,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_small)
                    )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(
                    bottom = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium)
                )
            ) {
                Column {
                    Row (
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_extra_small))
                    ) {
                        Text(
                            text = "Rating: ${selectedNews.rating}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                        )
                        Text(
                            text = selectedNews.date,
                            style = MaterialTheme.typography.bodyMedium,
                        ) 
                    }
                    Text(
                        text = stringResource(id = R.string.like_press),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onLikePressed() }) {
                    Icon(
                        imageVector = if(selectedNews.liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.like_icon)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsDetailsScreenTopBar(
    onBackButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(),
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClicked,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.navigation_back)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.label),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
    )
}

@Preview
@Composable
private fun NewsDetailsScreenTopBarPreview() {
    NewsDetailsScreenTopBar(onBackButtonClicked = {})
}