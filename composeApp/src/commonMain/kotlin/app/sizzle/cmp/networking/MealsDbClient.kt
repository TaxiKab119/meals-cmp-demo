package app.sizzle.cmp.networking

import app.sizzle.cmp.data.MealsResponse
import app.sizzle.cmp.util.NetworkError
import app.sizzle.cmp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class MealsDbClient(
    private val httpClient: HttpClient
) {
    suspend fun getBeefMeals() : Result<MealsResponse, NetworkError> {
        val response = try {
            httpClient.get(urlString = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Beef")
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        /**
         * result is a list of meals
         */
        return when (response.status.value) {
            // anything in the 200s is a successful response
            in 200..299 -> {
                try {
                    val beefMeals = response.body<MealsResponse>()
                    Result.Success(beefMeals)
                } catch (e: SerializationException) {
                    Result.Error(NetworkError.SERIALIZATION)
                }
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}