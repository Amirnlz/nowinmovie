import com.amirnlz.core.data.movie.api.MovieApiService
import com.amirnlz.core.data.movie.dto.MovieDetailsDto
import com.amirnlz.core.data.movie.dto.MovieListDto
import com.amirnlz.core.data.network.error.ErrorHandler
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) {

    suspend fun getPopularMovies(apiKey: String = ""): Result<MovieListDto> {
        return try {
            val response = movieApiService.getPopularMovies()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(ErrorHandler.handleException(e))
        }
    }

    suspend fun getMovieDetails(movieId: Long, apiKey: String = ""): Result<MovieDetailsDto> {
        return try {
            val response = movieApiService.getMovieDetails(movieId = movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(ErrorHandler.handleException(e))
        }
    }

    suspend fun getFavoriteMovies(accountId: Long): Result<MovieListDto> {
        return try {
            val response = movieApiService.getFavoriteMovies(accountId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(ErrorHandler.handleException(e))
        }
    }

    suspend fun addFavoriteMovie(accountId: Long, movieId: Long): Result<Unit> {
        return try {
            val response = movieApiService.addFavoriteMovie(accountId, movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(ErrorHandler.handleException(e))
        }
    }
}