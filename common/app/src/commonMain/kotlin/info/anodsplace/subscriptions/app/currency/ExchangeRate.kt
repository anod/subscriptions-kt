package info.anodsplace.subscriptions.app.currency

class ExchangeRate {

    private val exchange = mapOf(
        "ILS" to 1f,
        "USD" to 3.20f
    )

    fun convert(price: Float, currency: String): Float {
        val rate = exchange[currency] ?: throw IllegalStateException("No exchange rate for $currency")
        return price * rate
    }
}