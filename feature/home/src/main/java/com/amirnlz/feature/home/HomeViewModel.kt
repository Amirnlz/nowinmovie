package com.amirnlz.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.usecase.GetFavoriteMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetPopularMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetTopRatedMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetTrendingMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
) : ViewModel() {
    private val _currentMovieType = MutableStateFlow(MovieType.Trending)
    val currentMovieType = _currentMovieType.asStateFlow()

    val pagingDataFlow: Flow<PagingData<Movie>> = _currentMovieType
        .flatMapLatest { movieType ->
            when (movieType) {
                MovieType.Trending -> getTrendingMoviesUseCase()
                MovieType.Popular -> getPopularMoviesUseCase()
                MovieType.TopRated -> getTopRatedMoviesUseCase()
                MovieType.Upcoming -> getUpcomingMoviesUseCase()
                MovieType.Favorite -> getFavoriteMoviesUseCase()
            }
        }
        .cachedIn(viewModelScope)

    fun onIntent(intent: HomeIntent) {
        _currentMovieType.value = intent.toMovieType()
    }
}