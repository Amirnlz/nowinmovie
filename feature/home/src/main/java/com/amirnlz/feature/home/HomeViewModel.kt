package com.amirnlz.feature.home

import androidx.lifecycle.ViewModel
import com.amirnlz.core.domain.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

}