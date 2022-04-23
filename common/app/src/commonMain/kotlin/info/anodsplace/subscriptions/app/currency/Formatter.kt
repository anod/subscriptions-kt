package info.anodsplace.subscriptions.app.currency

interface Currency {
    fun getSymbol(code: String) : String
    fun format(money: Float, code: String) : String
}