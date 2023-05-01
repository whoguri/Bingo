package `in`.whoguri.bingo

import java.text.DecimalFormat

fun Double.roundOffDecimal3(): Double {
    val df = DecimalFormat("#.###")
//        df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}

fun Double.roundOffDecimal2(): Double {
    val df = DecimalFormat("#.##")
//        df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}
