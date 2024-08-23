package `in`.whoguri.bingo.helpers

import `in`.whoguri.bingo.Data
import `in`.whoguri.bingo.helpers.Logic.CORNERS
import `in`.whoguri.bingo.helpers.Logic.getSel

class Data_13_2(val name: String, val other: ArrayList<String>, var result: ArrayList<Pair<String, Double>> = arrayListOf())

object NewLogic13_2 {
    val GROUP_1 = arrayListOf(1, 2, 3, 4, 5)
    val GROUP_2 = arrayListOf(6, 7, 8, 9, 10)
    val GROUP_3 = arrayListOf(11, 12, 14, 15)
    val GROUP_4 = arrayListOf(16, 17, 18, 19, 20)
    val GROUP_5 = arrayListOf(21, 22, 23, 24, 25)
    val GROUP_B = arrayListOf(1, 6, 11, 16, 21)
    val GROUP_I = arrayListOf(2, 7, 12, 17, 22)
    val GROUP_N = arrayListOf(3, 8, 18, 23)
    val GROUP_G = arrayListOf(4, 9, 14, 19, 24)
    val GROUP_O = arrayListOf(5, 10, 15, 20, 25)
    val GROUP_C = arrayListOf(1, 5, 21, 25)
    val GROUP_D1 = arrayListOf(1, 7, 19, 25)
    val GROUP_D2 = arrayListOf(5, 9, 17, 21)

    val GROUP_ARRAY = arrayListOf(
        Triple("1", GROUP_1, "H"),
        Triple("2", GROUP_2, "H"),
        Triple("3", GROUP_3, "H"),
        Triple("4", GROUP_4, "H"),
        Triple("5", GROUP_5, "H"),
        Triple("B", GROUP_B, "V"),
        Triple("I", GROUP_I, "V"),
        Triple("N", GROUP_N, "V"),
        Triple("G", GROUP_G, "V"),
        Triple("O", GROUP_O, "V"),
        Triple("C", GROUP_C, "C"),
        Triple("B1-O5", GROUP_D1, "D"),
        Triple("O1-B5", GROUP_D2, "D"),
    )
    val GROUPS = arrayListOf(
        Data_13_2("X", arrayListOf("C", "B1-O5", "O1-B5")),
        Data_13_2("BO", arrayListOf("B", "O", "1", "5")),
        Data_13_2("IG", arrayListOf("I", "G", "2", "4")),
        Data_13_2("N3", arrayListOf("N", "3"))
    )

    fun calResult(list: ArrayList<Data>): ArrayList<Data_13_2> {
        var result = arrayListOf<Data_13_2>()
        if (list.size != 25)
            return result

        GROUPS.forEach { it.result.clear() }

        GROUP_ARRAY.forEachIndexed { i, item ->
            var total = 1.0
            val items = getSel(item.second, list).filter { item_s -> !item_s.isClicked }
            items.forEach { innerItem ->
                if (item.third != "H") {
                    val size = getSel(innerItem.h, list).filter { item_s -> !item_s.isClicked }.size
                    if (size != 0)
                        total = total + (1.0 / size)
                }
                if (item.third != "V") {
                    val size = getSel(innerItem.v, list).filter { item_s -> !item_s.isClicked }.size
                    if (size != 0)
                        total = total + (1.0 / size)
                }
                if (item.third != "D" && innerItem.d.size > 0) {
                    val size = getSel(innerItem.d, list).filter { item_s -> !item_s.isClicked }.size
                    if (size != 0)
                        total = total + (1.0 / size)
                }
                if (item.third != "C" && CORNERS.contains(innerItem.number)) {
                    val size = getSel(CORNERS, list).filter { item_s -> !item_s.isClicked }.size
                    if (size != 0)
                        total = total + (1.0 / size)
                }
            }
            GROUPS.find { it.other.contains(item.first) }?.result?.add(Pair(item.first, if (items.size > 0) (total / items.size).roundOffDecimal3() else 0.0))
        }
        val temp2 = arrayListOf<Data_13_2>()
        GROUPS.forEach {
            val temp = arrayListOf<Pair<String, Double>>()
            it.result.sortedByDescending { it.second }.forEach { it_ ->
                temp.add(it_)
            }
            it.result = temp
            temp2.add(it)
        }

        return temp2
    }
}