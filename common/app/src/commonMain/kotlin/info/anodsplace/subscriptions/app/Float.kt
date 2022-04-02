package info.anodsplace.subscriptions.app

import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.format(numOfDec: Int = 2): String {
    val integerDigits = this.toInt()
    val floatDigits = ((this - integerDigits) * 10f.pow(numOfDec)).roundToInt()
    return "${integerDigits}.${floatDigits}"
}