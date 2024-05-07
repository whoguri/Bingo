package `in`.whoguri.bingo

import android.util.Log
import java.math.RoundingMode

fun Double.roundOffDecimal3(): Double {
    try {
        val roundedUp = this.toBigDecimal().setScale(3, RoundingMode.HALF_EVEN).toDouble()
        return roundedUp

//        val df = DecimalFormat("#.###")
//        return df.format(this).toDouble()
    } catch (e: Exception) {
        return 0.0
    }
}

fun Double.roundOffDecimal2(): Double {
    try {
        val roundedUp = this.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        return roundedUp
//        val df = DecimalFormat("#.##")
//        return df.format(this).toDouble()
    } catch (e: Exception) {
        Log.e(">>>>", e.message +":::"+this.toString())
        return 0.0
    }
}
