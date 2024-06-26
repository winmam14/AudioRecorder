package at.winter.audioRecorder.data.remote

import at.winter.audioRecorder.utils.Joke
import retrofit2.Response
import retrofit2.http.GET

interface JokeApi {
    @GET("jokes/random")
    suspend fun makeRequest(): Response<Joke>
}

