package com.example.goodnewsapplication.ui.screens.topnews

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.goodnewsapplication.R
import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.domain.news.navigationItemContentList
import com.example.goodnewsapplication.ui.AppViewModelProvider
import com.example.goodnewsapplication.ui.navigation.GoodNewsContentType
import com.example.goodnewsapplication.ui.navigation.GoodNewsNavigationType
import com.example.goodnewsapplication.ui.screens.home.GoodNewsBottomNavigationBar
import com.example.goodnewsapplication.ui.screens.home.HomeTopAppBar
import com.example.goodnewsapplication.ui.screens.home.sharePostNews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNewsScreen(
    navController: NavController,
    goodNewsNavigationType: GoodNewsNavigationType,
    goodNewsContentType: GoodNewsContentType,
    navigateToNewsItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopNewsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val topNewsUiState by viewModel.topNewsUiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { HomeTopAppBar() },
        bottomBar = {
            if (goodNewsNavigationType == GoodNewsNavigationType.BOTTOM_NAVIGATION) {
                GoodNewsBottomNavigationBar(
                    navController = navController,
                    currentScreen = topNewsUiState.currentScreen,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else if (goodNewsNavigationType == GoodNewsNavigationType.NAVIGATION_RAIL) {
                GoodNewsBottomNavigationBar(
                    navController = navController,
                    currentScreen = topNewsUiState.currentScreen,
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
        TopNewsBody(
            newsList = topNewsUiState.newsList,
            onItemClick = navigateToNewsItem, //navigateToItemUpdate,
            modifier = modifier
                .padding(padding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun TopNewsBody(
    newsList: List<NewsEntity>,
    onItemClick: (Int) -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
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
                    text = stringResource(R.string.no_news_description),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_large),
                            bottom = dimensionResource(id = R.dimen.padding_large)
                        )
                )
                Image(
                    imageVector = Icons.Default.CloudOff,
                    contentDescription = stringResource(R.string.error),
                    modifier = modifier.size(dimensionResource(R.dimen.error_image_size))
                )
            }
        } else {
            Column {
                Text(
                    text = stringResource(id = R.string.top_five_news),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        top = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    )
                )
                TopNewsList(
                    newsList = newsList,
                    onItemClick = { onItemClick(it.id) },
                    modifier = Modifier.padding(paddingValues)
                )
            }

        }
    }
}

@Composable
fun TopNewsList(
    newsList: List<NewsEntity>,
    onItemClick: (NewsEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = newsList, key = { it.id }) { newsEntity ->
            Column {
                TopNewsCardItem(
                    newsEntity = newsEntity,
                    modifier = Modifier
                        .clickable { onItemClick(newsEntity) }
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.padding_small),
                            vertical = dimensionResource(id = R.dimen.padding_extra_small)
                        )
                )
            }

        }
    }
}

@Composable
fun TopNewsCardItem(
    newsEntity: NewsEntity,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium),
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(5f)
                        .padding(end = dimensionResource(id = R.dimen.padding_small))
                ) {
                    Row (
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.padding_medium),
                            bottom = dimensionResource(id = R.dimen.padding_small)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.rating),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_extra_small))
                        )
                        Text(
                            text = newsEntity.rating.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Text(
                        text = newsEntity.author,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = newsEntity.label,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = {
                            sharePostNews(
                                context = context,
                                author = newsEntity.author,
                                title = newsEntity.label
                            )
                        },
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
}