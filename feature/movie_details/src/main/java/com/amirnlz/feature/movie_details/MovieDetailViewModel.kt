package com.amirnlz.feature.movie_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.usecase.GetMovieCreditsUseCase
import com.amirnlz.core.domain.movie.usecase.GetMovieDetailsUseCase
import com.amirnlz.core.domain.movie.usecase.GetMovieFavoriteStateUseCase
import com.amirnlz.core.domain.movie.usecase.GetMovieRecomUseCase
import com.amirnlz.core.domain.movie.usecase.ToggleFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieFavoriteStateUseCase: GetMovieFavoriteStateUseCase,
    private val getMovieRecomUseCase: GetMovieRecomUseCase,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val movieId: Long = checkNotNull(savedStateHandle["movieId"]) {
        "movieId is required in SavedStateHandle"
    }
    private var _uiState = MutableStateFlow(MovieDetailsUiState(loading = true))
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()


    init {
        observeFavoriteState()
        loadMovieDetailsAndCredits()
    }

    private fun observeFavoriteState() {
        viewModelScope.launch {
            getMovieFavoriteStateUseCase(movieId).collect { isFavorite ->
                _uiState.update { it.copy(favoriteState = isFavorite) }
            }
        }
    }

    private fun loadMovieDetailsAndCredits() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }

            val movieDetailsDeferred = async { getMovieDetailsUseCase(movieId) }
            val movieCreditsDeferred = async { getMovieCreditsUseCase(movieId) }
            val movieRecommendationsDeferred = async { getMovieRecomUseCase(movieId) }

            val movieDetailsResult = movieDetailsDeferred.await()
            val movieCreditsResult = movieCreditsDeferred.await()
            val movieRecommendationsResult = movieRecommendationsDeferred.await()

            _uiState.update {
                it.copy(
                    movieDetails = movieDetailsResult.getOrNull(),
                    movieCredits = movieCreditsResult.getOrNull(),
                    movieRecommendations = movieRecommendationsResult.getOrDefault(emptyList()),
                    loading = false,
                    error = movieDetailsResult.exceptionOrNull()
                        ?: movieCreditsResult.exceptionOrNull()
                )
            }
        }
    }


    fun onIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.ChangeMovieFavorite -> changeMovieFavoriteState(intent.movieDetails)
        }
    }

    private fun changeMovieFavoriteState(movieDetails: MovieDetails) {
        viewModelScope.launch {
            toggleFavoriteMovieUseCase(movieDetails).onSuccess { state ->
                Log.d(
                    "MovieDetailViewModel",
                    "Movie favorite state changed successfully to $state"
                )
                _uiState.update { it.copy(favoriteState = state) }
            }.onFailure { throwable ->
                Log.e(
                    "MovieDetailViewModel", "Failed to change movie favorite state",
                    throwable
                )
                _uiState.update { it.copy(error = throwable) }
            }
        }
    }
}