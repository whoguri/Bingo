package `in`.whoguri.bingo

import java.text.DecimalFormat

fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.###")
//        df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}
