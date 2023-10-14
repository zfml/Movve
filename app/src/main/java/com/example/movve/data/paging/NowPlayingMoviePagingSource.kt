package com.example.movve.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movve.data.remote.MovieApi
import com.example.movve.data.remote.MovieDto

class NowPlayingMoviePagingSource (
    private val movieApi: MovieApi
): PagingSource<Int,MovieDto>() {
    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val currentPage = params?.key ?: 1

        return try{
            val response = movieApi.getAllNowPlayingMovies(currentPage)
            LoadResult.Page(
                data = response.results,
                prevKey = if(currentPage == 1) null else currentPage - 1,
                nextKey =  if(response.results.isEmpty()) null else currentPage + 1
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}