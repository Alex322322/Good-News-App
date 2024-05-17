package com.example.goodnewsapplication.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.goodnewsapplication.R
import com.example.goodnewsapplication.domain.news.NavigationItemContent
import com.example.goodnewsapplication.domain.news.navigationItemContentList
import com.example.goodnewsapplication.ui.AppViewModelProvider
import com.example.goodnewsapplication.ui.navigation.GoodNewsContentType
import com.example.goodnewsapplication.ui.navigation.GoodNewsNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    goodNewsNavigationType: GoodNewsNavigationType,
    navController: NavController,
    goodNewsContentType: GoodNewsContentType,
    navigateToNewsItem: (Int) -> Unit,
    navigateToWeatherScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { HomeTopAppBar() },
        bottomBar = {
            if (goodNewsNavigationType == GoodNewsNavigationType.BOTTOM_NAVIGATION) {
                GoodNewsBottomNavigationBar(
                    navController = navController,
                    currentScreen = homeUiState.currentScreen,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else if (goodNewsNavigationType == GoodNewsNavigationType.NAVIGATION_RAIL) {
                GoodNewsBottomNavigationBar(
                    navController = navController,
                    currentScreen = homeUiState.currentScreen,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        },
    ) { padding ->
            HomeBody(
                newsList = homeUiState.newsList,
                onWeatherItemClick = navigateToWeatherScreen,
                onItemClick = navigateToNewsItem,
                modifier = modifier
                    .padding(padding)
                    .fillMaxWidth()
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior? =
        TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
) {
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        actions = {
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Profile is not yet implemented in this configuration",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(id = R.string.profile_icon)
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Search is not yet implemented in this configuration",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search_icon)
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.label),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
fun GoodNewsBottomNavigationBar(
    navController: NavController,
    currentScreen: String,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentScreen == navItem.goodNewsScreensRoutes,
                onClick = {
                    navController.navigate(navItem.goodNewsScreensRoutes)
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = stringResource(id = navItem.text)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = navItem.text),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            )
        }
    }
}

/*
@Composable
private fun GoodNewsNavigationRail(
    currentScreen: GoodNewsScreensType,
    onScreenPressed: ((GoodNewsScreensType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentScreen == navItem.goodNewsScreensType,
                onClick = { onScreenPressed(navItem.goodNewsScreensType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = stringResource(id = navItem.text)
                    )
                }
            )
        }
    }
}
*/


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
private fun HomeTopAppbarPreview() {
    HomeTopAppBar()
}