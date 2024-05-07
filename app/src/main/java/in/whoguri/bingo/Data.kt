package `in`.whoguri.bingo

class Data(
    val number: Int,
    val code: String,
    val bingos: Int,
    val selfValue: Int,
    var h: ArrayList<Int>,
    var v: ArrayList<Int>,
    var d: ArrayList<Int>,
    val group:String="",
    var finalValue: Int = -1,
    var finalValue2: Double = -1.0,
    var finalValue3: Double = -1.0,
    var finalValue4: Double = -1.0,
    var finalValue6: Double = -1.0,
    var finalValue7: Double = -1.0,
    var finalValue8: Double = -1.0,
    var isClicked: Boolean = false,
    var subHiddenH: Double = 0.0,
    var subHiddenV: Double = 0.0,
    var subHiddenD: Double = 0.0,
    var subHiddenC: Double = 0.0,
    var hidden: Double = 0.0,
    var hiddenPerc: Double = 0.0,
    var avrage: Double = 0.0,
    var genAvrage: Double = 0.0,

    )
