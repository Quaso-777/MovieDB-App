# 🎬 Movie App

A simple Android app built with Jetpack Compose that displays popular movies using [The Movie Database (TMDB) API](https://www.themoviedb.org/documentation/api).

A native Android application built to explore and master Jetpack Compose, modern MVVM architecture, and API integration using [The Movie Database (TMDB) API](https://www.themoviedb.org/documentation/api).

## Features

- Browse a list of popular movies (title, overview, poster)
- Tap a movie to view its details
- Loading, success, and error states handled in the UI
- Poster images loaded from TMDB via Coil

## Tech Stack

- **Kotlin**
- **Jetpack Compose** — UI
- **ViewModel + StateFlow** — state management
- **Retrofit** — networking
- **Coil** — image loading
- **Navigation Compose** — screen navigation
- **Coroutines** — async data fetching

## Architecture

The app follows a basic MVVM structure:

```
UI (Compose)  ───►  ViewModel  ───►  Repository  ───►  TMDB API (Retrofit)
     ▲                   │
     └── StateFlow ──────┘
```

- **`TmdbApi`** — Retrofit interface defining the API endpoints
- **`RetrofitClient`** — builds and configures the Retrofit instance
- **`MovieRepository`** — fetches data from the API
- **`MovieViewModel`** — exposes UI state (`Loading`, `Success`, `Error`) via `StateFlow`
- **`Movie` / `MovieResponse`** — data models mapped from the TMDB JSON response
- **Compose screens** — render UI based on the current state

## Screens

| Screen | Description |
|---|---|
| Movie List | Shows popular movies in a scrollable list with poster, title, and overview |
| Movie Detail | Shows details for a selected movie |

## Setup

1. Clone the repository.
2. Get a free API key from [TMDB](https://www.themoviedb.org/settings/api).
3. Open the project in Android Studio.
4. Add your API key securely. Open your `local.properties` file (create one in the root directory if it doesn't exist) and add the following line:
   ```properties
   TMDB_API_KEY="your_actual_api_key_here"
   ```
   *(Note: Ensure your app's `build.gradle.kts` is configured to read from `local.properties` via `BuildConfig` so the key isn't hardcoded).*
5. Build and run the app on an emulator or physical device.

## Dependencies

```kotlin
implementation("androidx.navigation:navigation-compose:2.8.0")
implementation("io.coil-kt:coil-compose:2.6.0")
// + standard Compose, ViewModel, Retrofit, Coroutines dependencies
```

## Possible Next Steps

- [ ] Fill in movie detail screen with full info (rating, release date, poster)
- [ ] Add search
- [ ] Add pagination / infinite scroll
- [ ] Add pull-to-refresh
- [ ] Add favorites/watchlist (local storage)

## License

Personal project — no license, use however you like.
