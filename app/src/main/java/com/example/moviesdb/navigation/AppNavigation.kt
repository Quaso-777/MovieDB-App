package com.example.moviesdb.navigation

import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesdb.ui.theme.MovieDetailsScreen
import com.example.moviesdb.ui.theme.*
import com.example.moviesdb.ui.theme.MovieScreen
import com.example.moviesdb.ui.theme.FavoriteScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.viewmodel.FavoritesViewModel
import com.example.moviesdb.viewmodel.MovieDetailsViewModel
import com.example.moviesdb.viewmodel.MovieViewModel
import com.example.moviesdb.viewmodel.SearchViewModel


data class BottomNavItem(
    val title: String, val route: String, val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Home", route = Screen.MovieList.route, icon = Icons.Default.Home
    ), BottomNavItem(
        title = "Search", route = Screen.Search.route, icon = Icons.Default.Search
    ), BottomNavItem(
        title = "Favorites", route = Screen.Favorites.route, icon = Icons.Default.Favorite
    )

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // 1. Observe the current back stack to know which screen we are on
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 2. Decide if the bottom bar should be shown
    // hide the bottom bar on detail screens
    val showBottomBar =
        currentRoute == Screen.MovieList.route || currentRoute == Screen.Favorites.route || currentRoute == Screen.Search.route

    Scaffold(bottomBar = {
        if (showBottomBar) {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(selected = isSelected, onClick = {
                        navController.navigate(item.route) {
                            // Pops the back stack so we don't build up a massive history of back-and-forth clicks
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Prevents multiple copies of the same screen
                            launchSingleTop = true
                            // Restores previous state (like scroll position) when re-selecting
                            restoreState = true
                        }
                    }, icon = {
                        Icon(
                            imageVector = item.icon, contentDescription = item.title
                        )
                    }, label = { Text(text = item.title) })
                }
            }
        }
    }, topBar = {
        if (showBottomBar) {
            val topBarTitle = when (currentRoute) {
                Screen.MovieList.route -> "Popular"
                Screen.Favorites.route -> "Favorites"
                else -> "Movies App"
            }
            TopAppBar(
                title = { Text(topBarTitle) }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40, titleContentColor = Color.Black
                )
            )
        }
    }) { innerPadding ->
        // 3. Apply the innerPadding to NavHost so the bottom bar doesn't overlap lists
        NavHost(
            navController = navController,
            startDestination = Screen.MovieList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // MOVIE LIST SCREEN
            composable(route = Screen.MovieList.route) {
                val listViewModel: MovieViewModel = hiltViewModel()
                MovieScreen(
                    viewModel = listViewModel, onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetails.createRoute(movieId))
                    })
            }

            // MOVIE DETAILS SCREEN
            composable(
                route = Screen.MovieDetails.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) {
                val detailsViewModel: MovieDetailsViewModel = hiltViewModel()
                MovieDetailsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = detailsViewModel,
                )
            }

            // FAVORITES SCREEN
            composable(route = Screen.Favorites.route) {
                val favoritesViewModel: FavoritesViewModel = hiltViewModel()
                FavoriteScreen(
                    viewModel = favoritesViewModel, onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetails.createRoute(movieId))
                    })
            }

            //SEARCH SCREEN
            composable(route = Screen.Search.route) {
                val searchViewModel: SearchViewModel = hiltViewModel()
                SearchScreen(
                    viewModel = searchViewModel, onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetails.createRoute(movieId))
                    })
            }
        }
    }
}