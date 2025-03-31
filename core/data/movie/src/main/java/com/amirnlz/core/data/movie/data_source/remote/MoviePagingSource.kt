package com.amirnlz.core.data.movie.data_source.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amirnlz.core.data.movie.mapper.mapToMovie
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.network.dto.MovieListDto
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val loader: suspend (page: Int) -> MovieListDto
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = loader(pageNumber)
            val items = response.results.map { it.mapToMovie() }

            val nextKey = if (items.isEmpty() || pageNumber >= response.totalPages) {
                null
            } else {
                pageNumber + 1
            }
            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1

            LoadResult.Page(
                data = items,
                prevKey = prevKey,
                nextKey = nextKey
            )


        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}