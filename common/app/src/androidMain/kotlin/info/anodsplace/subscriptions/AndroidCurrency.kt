package info.anodsplace.subscriptions

import android.icu.util.CurrencyAmount
import info.anodsplace.subscriptions.app.currency.Currency

class AndroidCurrency : Currency {
    override fun getSymbol(code: String): String {
        val instance = android.icu.util.Currency.getInstance(code)
        return instance.symbol
    }

    override fun format(money: Float, code: String): String {
        val currency =android.icu.util.Currency.getInstance(code)
        val formatter = android.icu.text.NumberFormat.getCurrencyInstance()
        return formatter.format(CurrencyAmount(money, currency))
    }
}