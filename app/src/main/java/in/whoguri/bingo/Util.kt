package `in`.whoguri.bingo

import android.util.Log
import java.text.DecimalFormat

fun Double.roundOffDecimal3(): Double {
    try {
        val df = DecimalFormat("#.###")
        return df.format(this).toDouble()
    } catch (e: Exception) {
        return 0.0
    }
}

fun Double.roundOffDecimal2(): Double {
    try {
        val df = DecimalFormat("#.##")
        return df.format(this).toDouble()
    } catch (e: Exception) {
        Log.e(">>>>", this.toString())
        return 0.0
    }
}
