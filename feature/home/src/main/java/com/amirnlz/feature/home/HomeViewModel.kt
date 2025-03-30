package com.amirnlz.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirnlz.core.domain.movie.model.MovieList
import com.amirnlz.core.domain.movie.usecase.GetFavoriteMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetPopularMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetTopRatedMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetTrendingMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
) : ViewModel() {
    private var _state =
        MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    val state: StateFlow<HomeUiState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = HomeUiState.Loading,
    )
    private var _movieTypeState = MutableStateFlow(MovieType.Trending)
    val movieTypeState = _movieTypeState.asStateFlow()

    fun onIntent(intent: HomeIntent) {
        _movieTypeState.update { intent.toMovieType() }
        intent.execute(this)
    }


    private fun HomeIntent.execute(viewModel: HomeViewModel) {
        when (this) {
            HomeIntent.FavoriteMovies ->
                viewModel.getMovies { viewModel.getFavoriteMoviesUseCase() }

            HomeIntent.PopularMovies ->
                viewModel.getMovies { viewModel.getPopularMoviesUseCase() }

            HomeIntent.TopRatedMovies ->
                viewModel.getMovies { viewModel.getTopRatedMoviesUseCase() }

            HomeIntent.TrendingMovies ->
                viewModel.getMovies { viewModel.getTrendingMoviesUseCase() }

            HomeIntent.UpcomingMovies ->
                viewModel.getMovies { viewModel.getUpcomingMoviesUseCase() }
        }
    }


    private fun getMovies(useCase: suspend () -> Result<MovieList>) {
        viewModelScope.launch {
            _state.update { HomeUiState.Loading }
            useCase().onSuccess { movies ->
                _state.update { HomeUiState.Success(movies) }
            }.onFailure { throwable ->
                _state.update { HomeUiState.Error(throwable) }
            }
        }
    }


}