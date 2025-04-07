package com.amirnlz.feature.search


sealed interface SearchIntent {
    data class ChangeQuery(val query: String) : SearchIntent
}