package `in`.whoguri.bingo

import android.util.Log
import `in`.whoguri.bingo.Logic.CORNERS
import `in`.whoguri.bingo.Logic.getHV
import `in`.whoguri.bingo.Logic.getSel

class Data_13(val name: String, val result: Double, val noWhite: Int, val noWhitePlus: Int)
object NewLogic2 {
    val GROUP_IN = arrayListOf(2, 3, 12, 22, 23)
    val GROUP_NG = arrayListOf(3, 4, 14, 23, 24)
    val GROUP_IG = arrayListOf(2, 4, 12, 14, 22, 24)
    val GROUP_23 = arrayListOf(6, 8, 10, 11, 15)
    val GROUP_34 = arrayListOf(11, 15, 16, 18, 20)
    val GROUP_24 = arrayListOf(6, 8, 10, 16, 18, 20)

    val GROUP_ARRAY = arrayListOf<Pair<String, ArrayList<Int>>>(
        Pair("IN", GROUP_IN),
        Pair("NG", GROUP_NG),
        Pair("IG", GROUP_IG),
        Pair("23", GROUP_23),
        Pair("34", GROUP_34),
        Pair("24", GROUP_24)
    )

    fun calResult13(list: ArrayList<Data>): ArrayList<Data_13> {
        var result = arrayListOf<Data_13>()
        if (list.size != 25)
            return result
        GROUP_ARRAY.forEachIndexed { i, item ->
            val noWhite = getSel(item.second, list).filter { item_w -> !item_w.isClicked  }.size // && !item.second.contains(item_w.number)

            var word = ""
            var other = 0

            getSel(item.second, list).filter { item_s -> !item_s.isClicked }.forEach {
                word = word + it.code
//                if (arrayOf("IN", "NG", "IG").contains(item.first))
                    other = other + getSel(it.h, list).filter { item_o -> !item_o.isClicked && !item.second.contains(item_o.number)}.size //
//                else
                    other = other + getSel(it.v, list).filter { item_o -> !item_o.isClicked && !item.second.contains(item_o.number)}.size //
            }
            val uniqueChars = mutableSetOf<Char>()

            for (char in word) {
                if (char.isLetterOrDigit()) {
                    uniqueChars.add(char)
                }
            }
            var noBingo = uniqueChars.size

            var res = 0.0
            if (noBingo > 0)
                res = (noWhite.toDouble() / noBingo).roundOffDecimal2()

            result.add(Data_13(item.first, res, noWhite, noWhite + other))
        }
        val temp = arrayListOf<Data_13>()
        val sortedList = result.sortedWith(compareByDescending<Data_13> { it.result }.thenByDescending { it.noWhitePlus })
        //.thenByDescending { it.noWhite }

        sortedList.forEach {
            temp.add(it)
            Log.e(">>>> ", it.name + ": " + it.result +" "+it.noWhite +" " +it.noWhitePlus)
        }

        return temp
    }
}