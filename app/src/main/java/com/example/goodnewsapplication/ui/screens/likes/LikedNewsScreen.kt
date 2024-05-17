package com.example.goodnewsapplication.ui.screens.likes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.goodnewsapplication.R
import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.domain.news.NewsImage
import com.example.goodnewsapplication.domain.news.navigationItemContentList
import com.example.goodnewsapplication.ui.AppViewModelProvider
import com.example.goodnewsapplication.ui.navigation.GoodNewsContentType
import com.example.goodnewsapplication.ui.navigation.GoodNewsNavigationType
import com.example.goodnewsapplication.ui.screens.home.GoodNewsBottomNavigationBar
import com.example.goodnewsapplication.ui.screens.home.HomeTopAppBar
import com.example.goodnewsapplication.ui.screens.home.sharePostNews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedNewsScreen(
    navController: NavController,
    goodNewsNavigationType: GoodNewsNavigationType,
    goodNewsContentType: GoodNewsContentType,
    navigateToNewsItem: (Int) -> Unit,
    navigateToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LikedNewsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val likedNewsUiState by viewModel.likedNewsUiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { HomeTopAppBar() },
        bottomBar = {
            if (goodNewsNavigationType == GoodNewsNavigationType.BOTTOM_NAVIGATION) {
                GoodNewsBottomNavigationBar(
                    navController = navController,
                    currentScreen = likedNewsUiState.currentScreen,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else if (goodNewsNavigationType == GoodNewsNavigationType.NAVIGATION_RAIL) {
                GoodNewsBottomNavigationBar(
                    navController = navController,
                    currentScreen = likedNewsUiState.currentScreen,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                /*
                GoodNewsNavigationRail(
                    currentScreen = homeUiState.currentScreen,
                    onScreenPressed = onScreenPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                 */
            }
        },
    ) { padding ->
        LikedNewsBody(
            newsList = likedNewsUiState.newsList,
            navigateToHomeScreen = navigateToHomeScreen,
            onItemClick = navigateToNewsItem, //navigateToItemUpdate,
            modifier = modifier
                .padding(padding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun LikedNewsBody(
    newsList: List<NewsEntity>,
    onItemClick: (Int) -> Unit,
    navigateToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (newsList.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.no_liked_news_description),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_large),
                            bottom = dimensionResource(id = R.dimen.padding_large)
                        )
                )
                Button(onClick = navigateToHomeScreen) {
                    Text(text = stringResource(R.string.home_screen_button))
                }
            }
        } else {
            LikedNewsList(
                newsList = newsList,
                onItemClick = { onItemClick(it.id) },
                modifier = Modifier
                    .padding(paddingValues)
            )
        }
    }
}

@Composable
fun LikedNewsList(
    newsList: List<NewsEntity>,
    onItemClick: (NewsEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = newsList, key = { it.id }) { newsEntity ->
            LikedNewsCardItem(
                newsEntity = newsEntity,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_small),
                        vertical = dimensionResource(id = R.dimen.padding_extra_small)
                    )
                    .clickable { onItemClick(newsEntity) }
            )
        }
    }
}


@Composable
fun LikedNewsCardItem(
    newsEntity: NewsEntity,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    top = dimensionResource(id = R.dimen.padding_small)
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(
                        text = newsEntity.author,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = newsEntity.label,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Image(
                    modifier = modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .weight(1f)
                        .clip(MaterialTheme.shapes.extraSmall),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(NewsImage.bindNewsWithImages(newsEntity.id).imageRes),
                    contentDescription = NewsImage.bindNewsWithImages(newsEntity.id).imageDescription
                )
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = newsEntity.date,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = {
                        sharePostNews(
                            context = context,
                            author = newsEntity.author,
                            title = newsEntity.label
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = stringResource(id = R.string.share_icon)
                    )
                }
            }
        }
    }
}