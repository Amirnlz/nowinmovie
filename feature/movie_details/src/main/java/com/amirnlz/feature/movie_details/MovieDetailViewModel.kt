package com.amirnlz.feature.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirnlz.core.domain.movie.usecase.GetMovieCreditsUseCase
import com.amirnlz.core.domain.movie.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailsUseCase: GetMovieDetailsUseCase,
    private val movieCreditsUseCase: GetMovieCreditsUseCase
) :
    ViewModel() {

    private var _movieDetailsState =
        MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val movieDetailsState: StateFlow<MovieDetailsUiState> = _movieDetailsState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MovieDetailsUiState.Loading,
    )

    private var _movieCreditsState =
        MutableStateFlow<MovieCreditsUiState>(MovieCreditsUiState.Loading)
    val movieCreditsState: StateFlow<MovieCreditsUiState> = _movieCreditsState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MovieCreditsUiState.Loading,
    )

    fun onIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.GetMovieDetails -> getMovieDetails(intent.movieId)
            is MovieDetailIntent.GetMovieCredits -> getMovieCredits(intent.movieId)
        }
    }

    private fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            _movieDetailsState.update { MovieDetailsUiState.Loading }
            movieDetailsUseCase(movieId).onSuccess { details ->
                _movieDetailsState.update { MovieDetailsUiState.Success(details) }
            }.onFailure { error ->
                _movieDetailsState.update { MovieDetailsUiState.Error(error) }
            }
        }
    }

    private fun getMovieCredits(movieId: Long) {
        viewModelScope.launch {
            _movieCreditsState.update { MovieCreditsUiState.Loading }
            movieCreditsUseCase(movieId).onSuccess { details ->
                _movieCreditsState.update { MovieCreditsUiState.Success(details) }
            }.onFailure { error ->
                _movieCreditsState.update { MovieCreditsUiState.Error(error) }
            }
        }
    }
}