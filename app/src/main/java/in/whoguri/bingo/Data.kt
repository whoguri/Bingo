package `in`.whoguri.bingo

class Data(
    var number: Int,
    var code: String,
    var hideValue: Int,
    var selfValue: Int,
    var h: ArrayList<Int>,
    var v: ArrayList<Int>,
    var d: ArrayList<Int>,
    var finalValue: Int = -1,
    var finalValue2: Double = -1.0,
    var finalValue3: Double = -1.0,
    var isClicked: Boolean = false,
)
