package com.amirnlz.feature.search

import androidx.lifecycle.ViewModel
import com.amirnlz.core.domain.search.usecase.GetSearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMovieUseCase: GetSearchMovieUseCase
) : ViewModel()