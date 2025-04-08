package com.amirnlz.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.usecase.GetSearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMovieUseCase: GetSearchMovieUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val query: StateFlow<String> = _searchQuery.asStateFlow()

    val searchMovies: Flow<PagingData<Movie>> = _searchQuery
        .flatMapLatest { query ->
            delay(3000)
            if (query.isEmpty()) {
                emptyFlow()
            } else {
                getSearchMovieUseCase(query)
            }
        }.cachedIn(viewModelScope)


    fun onIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.ChangeQuery -> onQueryChange(intent.query)

        }
    }

    private fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

}