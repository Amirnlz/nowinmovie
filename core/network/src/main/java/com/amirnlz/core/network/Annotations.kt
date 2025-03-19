package com.amirnlz.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl