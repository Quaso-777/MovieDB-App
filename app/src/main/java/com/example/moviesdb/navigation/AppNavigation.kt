package com.example.moviesdb.navigation

import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesdb.ui.theme.MovieDetailsScreen
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


data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Home",
        route = Screen.MovieList.route,
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        title = "Favorites",
        route = Screen.Favorites.route,
        icon = Icons.Default.Favorite
    )
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // 1. Observe the current back stack to know which screen we are on
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 2. Decide if the bottom bar should be shown
    // hide the bottom bar on detail screens
    val showBottomBar = currentRoute == Screen.MovieList.route || currentRoute == Screen.Favorites.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    // These three lines are best practices for bottom nav routing:
                                    // Pops the back stack so we don't build up a massive history of back-and-forth clicks
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Prevents multiple copies of the same screen
                                    launchSingleTop = true
                                    // Restores previous state (like scroll position) when re-selecting
                                    restoreState = true
                                }
                            },
                            icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                            label = { Text(text = item.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
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
                    viewModel = listViewModel,
                    onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetails.createRoute(movieId))
                    }
                )
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
                    viewModel = favoritesViewModel,
                    onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetails.createRoute(movieId))
                    }
                )
            }
        }
    }
}