package com.amirnlz.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import java.util.Locale


@Composable
fun ImageNetwork(
    modifier: Modifier = Modifier,
    imageSize: ImageSize = ImageSize.W500,
    imagePath: String,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val context = LocalContext.current
    val imageUrl = remember(imageSize, imagePath) {
        BuildConfig.BASE_URL + "/${imageSize.name.lowercase(Locale.getDefault())}/" + imagePath
    }

    val requestBuilder = remember(context, imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCacheKey(imageUrl)
            .diskCacheKey(imageUrl)
            .crossfade(300)
    }

    AsyncImage(
        model = requestBuilder.build(),
        contentDescription = "",
        contentScale = contentScale,
        modifier = modifier,

        )
}


enum class ImageSize {
    Original,
    W500,
    W400,
    W300,
}