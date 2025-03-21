import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("latest/{base}")
    suspend fun getExchangeRates(@Path("base") baseCurrency: String): ExchangeRateResponse
}

data class ExchangeRateResponse(
    val conversion_rates: Map<String, Double>?
)
