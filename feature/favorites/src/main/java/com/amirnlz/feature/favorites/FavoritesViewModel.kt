package com.amirnlz.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.usecase.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val uiState: Flow<PagingData<Movie>> = _uiState.cachedIn(viewModelScope)

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { pagingData ->
                _uiState.value = pagingData
            }
        }
    }
}