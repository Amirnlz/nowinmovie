package com.amirnlz.core.designsystem.component.movie

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubeVideoPlayer(videoId: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val youTubePlayerView = remember {
        YouTubePlayerView(context).apply {
            layoutParams = android.widget.FrameLayout.LayoutParams(
                MATCH_PARENT,
                MATCH_PARENT
            )
        }
    }

    AndroidView(
        factory = { youTubePlayerView },
        update = { view ->
            view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        },
        modifier = modifier
    )

    DisposableEffect(youTubePlayerView) {
        onDispose {
            youTubePlayerView.release()
        }
    }
}