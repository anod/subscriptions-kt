package info.anodsplace.subscriptions

import info.anodsplace.subscriptions.app.currency.Currency
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

class JvmCurrency : Currency {
    override fun getSymbol(code: String): String {
        return java.util.Currency.getInstance(code).symbol
    }

    override fun format(money: Float, code: String): String {
        val formatter = NumberFormat.getCurrencyInstance()
        if (formatter is DecimalFormat) {
            val df: DecimalFormat = formatter
            // use local/default decimal symbols with original currency symbol
            val dfs: DecimalFormatSymbols = DecimalFormat().decimalFormatSymbols
            dfs.currency = java.util.Currency.getInstance(code)
            df.decimalFormatSymbols = dfs
        }
        return formatter.format(money)
    }
}