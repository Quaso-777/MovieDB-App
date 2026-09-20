package com.example.moviesdb.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesdb.data.api.Tmdbapi
import com.example.moviesdb.data.model.Movie

class MoviePagingSource(
    private val api: Tmdbapi
): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.getPopularMovies(currentPage)

            LoadResult.Page(
                data = response.results,
                prevKey = if (currentPage == 1) null else currentPage-1,
                nextKey = if (response.results.isEmpty()) null else currentPage+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}