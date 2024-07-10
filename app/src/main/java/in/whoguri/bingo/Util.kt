package `in`.whoguri.bingo

import android.util.Log
import java.math.BigInteger
import java.math.RoundingMode

fun Double.roundOffDecimal(n: Int, mode: RoundingMode = RoundingMode.HALF_EVEN): Double {
    try {
        val roundedUp = this.toBigDecimal().setScale(n, mode).toDouble()
        return roundedUp

//        val df = DecimalFormat("#.###")
//        return df.format(this).toDouble()
    } catch (e: Exception) {
        return 0.0
    }
}

fun Double.roundOffDecimal4() = this.roundOffDecimal(4, RoundingMode.CEILING)
fun Double.roundOffDecimal3() = this.roundOffDecimal(3)
fun Double.roundOffDecimal2() = this.roundOffDecimal(2)
