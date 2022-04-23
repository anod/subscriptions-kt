package info.anodsplace.subscriptions

import info.anodsplace.subscriptions.app.currency.Currency

class JsCurrency : Currency {
    override fun getSymbol(code: String): String {
        return when (code) {
            "USD" -> "$"
            "ILS" -> "₪"
            else -> code
        }
    }

    override fun format(money: Float, code: String): String {
        return "$money ${getSymbol(code)}"
    }
}