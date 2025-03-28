package com.amirnlz.feature.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MovieDetailViewModel @Inject constructor(private val movieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {

    private var _state = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val state: StateFlow<MovieDetailUiState> = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MovieDetailUiState.Loading,
    )

    fun onIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.GetMovieDetails -> getMovieDetails(intent.movieId)
        }
    }

    private fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            _state.update { MovieDetailUiState.Loading }
            movieDetailsUseCase(movieId).onSuccess { details ->
                _state.update { MovieDetailUiState.Success(details) }
            }.onFailure { error ->
                _state.update { MovieDetailUiState.Error(error) }
            }
        }
    }
}