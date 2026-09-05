# 🎬 Movie App

A native Android application built to explore and practice Jetpack Compose, MVVM architecture, and API integration using [The Movie Database (TMDB) API](https://www.themoviedb.org/documentation/api).

Side project — built for learning, not production polish.

<div align="center">
  <img src="https://via.placeholder.com/250x500.png?text=Movie+List+Screen" width="250" alt="Movie List Screen" />
  &nbsp;&nbsp;&nbsp;
  <img src="https://via.placeholder.com/250x500.png?text=Movie+Detail+Screen" width="250" alt="Movie Detail Screen" />
</div>

## ✨ Features

- Browse a list of popular movies (title, overview, poster)
- Tap a movie to view its details
- Loading, success, and error states handled in the UI
- Poster images loaded from TMDB via Coil

## 🛠 Tech Stack

- **Kotlin** — primary language
- **Jetpack Compose** — declarative UI toolkit
- **ViewModel + StateFlow** — state management and unidirectional data flow
- **Retrofit** — type-safe HTTP client for API networking
- **Coil** — image loading for Compose
- **Navigation Compose** — routing and screen transitions
- **Coroutines** — asynchronous data fetching

## 🏗 Architecture

The app follows a basic MVVM structure, separating UI from data logic:

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
- **Compose screens** — render UI based on the current state; navigation handled by `NavHost` with sealed-class routes

## 📱 Screens

| Screen | Description |
|---|---|
| **Movie List** | Shows popular movies in a scrollable list with poster, title, and overview |
| **Movie Detail** | Displays details for a selected movie by its ID (currently a placeholder — full detail view is a next step) |

## 🚀 Setup & Installation

1. Clone the repository.
2. Get a free API key from [TMDB](https://www.themoviedb.org/settings/api).
3. Open the project in Android Studio.
4. Add your API key securely. Open (or create) `local.properties` in the project root and add:
   ```properties
   TMDB_API_KEY="your_actual_api_key_here"
   ```
   Make sure `build.gradle.kts` reads this into `BuildConfig` so the key isn't hardcoded into source.
5. Build and run on an emulator or physical device.

## 🧠 What I Learned

- **Coroutines & StateFlow** — managing background network calls safely, and using `collectAsStateWithLifecycle()` to keep the UI lifecycle-aware
- **Jetpack Navigation** — structuring routes with sealed classes to avoid hardcoded strings, and passing arguments (like a movie ID) between screens
- **UI state modeling** — using a sealed `MovieUiState` (`Loading` / `Success` / `Error`) instead of exposing raw data, so the UI can react to every possible state explicitly

## 🔮 Possible Next Steps

- [ ] Build out the movie detail screen with full info (rating, release date, poster)
- [ ] Split screens into stateless + stateful composables for better previewability and testing
- [ ] Add search functionality
- [ ] Add pagination / infinite scroll to the movie list
- [ ] Implement pull-to-refresh
- [ ] Add favorites/watchlist backed by Room Database

## 📄 License

Personal project — feel free to use, study, or fork however you like!
