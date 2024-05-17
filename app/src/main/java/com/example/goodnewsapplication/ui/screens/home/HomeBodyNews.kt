package com.example.goodnewsapplication.ui.screens.home

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.goodnewsapplication.R
import com.example.goodnewsapplication.data.news.NewsEntity
import com.example.goodnewsapplication.domain.news.NewsImage

@Composable
fun HomeBody(
    newsList: List<NewsEntity>,
    onItemClick: (Int) -> Unit,
    onWeatherItemClick: () -> Unit,
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
            NewsList(
                newsList = newsList,
                onWeatherItemClick = onWeatherItemClick,
                onItemClick = { onItemClick(it.id) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun NewsList(
    newsList: List<NewsEntity>,
    onItemClick: (NewsEntity) -> Unit,
    onWeatherItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            WeatherItem(modifier = Modifier.clickable { onWeatherItemClick() })
        }
        items(items = newsList, key = { it.id }) { newsEntity ->
            Column {
                Divider(
                    color = MaterialTheme.colorScheme.secondary,
                    thickness = dimensionResource(id = R.dimen.divider_size)
                )
                NewsCardItem(
                    newsEntity = newsEntity,
                    modifier = Modifier.clickable { onItemClick(newsEntity) }
                )
            }

        }
    }
}

@Composable
fun NewsCardItem(
    newsEntity: NewsEntity,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
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
                    text = "${stringResource(id = R.string.rating)} ${newsEntity.rating}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                )
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

fun sharePostNews(
    context: Context,
    author: String,
    title: String
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, author)
        putExtra(Intent.EXTRA_TEXT, title)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.check_news_post)
        )
    )
}

@Preview(showSystemUi = true)
@Composable
private fun NewsCardItemPreview() {
    NewsCardItem(
        newsEntity = NewsEntity(
            id = 0,
            label = "Some News About Games And Other Stuff And Some More And Some More",
            author = "Random Gazette",
            text = "text",
            date = "1 April 2012",
            rating = 3.3,
            picture = "",
            liked = true,
            later = true
        ),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    )
}