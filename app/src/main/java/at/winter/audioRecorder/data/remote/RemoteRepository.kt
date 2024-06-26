package at.winter.audioRecorder.data.remote

import android.util.Log

private const val TAG = "ApiRequest"

class RemoteRepository(
    val client: JokeApi
) {
    suspend fun getJoke():String{
        val response = client.makeRequest()
        Log.i(TAG, response.body().toString())
        return try {
            if (response.isSuccessful){
                Log.i(TAG, response.body().toString())
                response.body()?.value ?: "Chuck Norris does not \"style\" his hair. It lays perfectly in place out of sheer terror."
            } else {
                "Chuck Norris does not \"style\" his hair. It lays perfectly in place out of sheer terror."
            }
        } catch (ex: Exception){
            Log.e(TAG, ex.toString())
            "Chuck Norris does not \"style\" his hair. It lays perfectly in place out of sheer terror."
        }

    }
}

