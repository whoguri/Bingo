package `in`.whoguri.bingo

import android.util.Log

object NewLogic {
    fun calResult11(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculateHidden11(list, list[i - 1], i)
        }
        for (i in 1..25) {
            var isD = true
            val data = mList[i - 1]
            val selected = Logic.getSel(getMarked(data.number), list).filter { !it.isClicked }
            if (Logic.CORNERS.contains(data.number) && data.d.size > 0) {
                if (selected.size <= 1)
                    isD = false
            } else if (Logic.CORNERS.contains(data.number) || data.d.size > 0) {
                if (selected.size <= 3)
                    isD = false
            }
            mList[i - 1] = calculate11(list, list[i - 1], i, isD)
        }
        return mList
    }

    fun calculate11(list: ArrayList<Data>, data: Data, clicked: Int, isD: Boolean): Data {
        var v = 0.0
        val vs = Logic.getSel(data.v, list).filter { !it.isClicked }
        if (vs.size == 1) {
            v = 1.0
        } else {
            var count = 1
            v = data.subHiddenV
            vs.forEach {
                if (clicked == 5)
                    Log.e("O1 v", v.toString())
                v += it.subHiddenH
                count++
            }
            if (clicked == 5)
                Log.e("O1 v", v.toString()+" : "+count)
            v = (v / count).roundOffDecimal3()
        }
        //other
        val row4 = Logic.getSel(arrayListOf(16, 17, 18, 19, 20), list).filter { !it.isClicked }

//        if (vs.find { v_el -> row4.find { it.number == v_el.number } != null } != null) {
        if(isD){
            val avrg = (v / vs.size).roundOffDecimal3()
            v = avrg
            if (clicked == 5)
                Log.e("::::::::>>>", v.toString())

            vs.forEach {
                if (it.number != clicked) {
                    val rows = Logic.getSel(it.h, list).filter { !it.isClicked }
                    if (rows.size == 1) {
                        v += avrg
                    } else {
                        val avrg2 = (avrg / rows.size).roundOffDecimal3()
                        rows.forEach { r ->
                            if (r.number == it.number) {
                                v = (v + avrg2).roundOffDecimal3()
                            } else {
                                v = (v + (r.hidden * avrg2)).roundOffDecimal3()
                            }
                        }
                    }
                }
            }
        }
        v = v.roundOffDecimal3()
        if (clicked == 5)
            Log.e("O1 v", v.toString())

        var h = 0.0
        var hs: List<Data>
        if (isD)
            hs = Logic.getSel(data.h, list).filter { it.number == clicked || (!it.isClicked &&  !Logic.CORNERS.contains(it.number)) }
        else
            hs = Logic.getSel(data.h, list).filter { !it.isClicked }

        if (hs.size == 1) {
            h = 1.0
        } else {
            var count = 1
            h = data.subHiddenH
            hs.forEach {
                h = h + it.subHiddenV
                count++
            }
            if (clicked == 5)
                Log.e(":>>>", count.toString() + " : " + h)

            h = (h / count).roundOffDecimal3()
        }



        if (clicked == 5)
            Log.e("O1 h", h.toString())

        var d = 0.0
        if (isD && data.d.size > 0) {
            val allDs = Logic.getSel(arrayListOf(1, 5, 7, 9, 17, 19, 21, 25), list).filter { !it.isClicked }
            if (allDs.size > 0) {
                var t = data.hidden
                allDs.forEach {
                    t += it.hidden
                }
                t = t / (allDs.size + 1)
//                if (clicked == 5)
//                    Log.e("O1 h", " " + (t / (allDs.size +1)).roundOffDecimal3())

                t = t / 2
                d = t
                val ds = Logic.getSel(data.d, list).filter { !it.isClicked }

                ds.forEach {
                    if (it.number != clicked) {
                        val rows = Logic.getSel(it.h, list).filter { !it.isClicked }
                        if (rows.size == 1) {
                            d += t
                        } else {
                            val avrg2 = (t / rows.size).roundOffDecimal3()
                            rows.forEach { r ->
                                if (r.number == it.number) {
                                    d = (d + avrg2).roundOffDecimal3()
                                } else {
                                    d = (d + (r.hidden * avrg2)).roundOffDecimal3()
                                }
                            }
                        }
                    }
                }

            }
        }
        if (clicked == 5)
            Log.e("O1 d", d.toString())

        var c = 0.0
        if (Logic.CORNERS.contains(clicked)) {
            val cs = Logic.getSel(Logic.CORNERS, list).filter { !it.isClicked }
            if (cs.size == 1) {
                c = 1.0
            } else {
                var count = 1
                c = data.subHiddenC
                cs.forEach {
                    c = c + it.subHiddenC
                    count++
                }
                c = (c / count).roundOffDecimal3()
            }
        }
        if (clicked == 5)
            Log.e("O1 c", c.toString())
        data.finalValue2 = (h + v + c + d).roundOffDecimal3()
        return data
    }

    fun subValue_11(v: Int): Double {
        return when (v) {
            5 -> 0.2
            4 -> 0.4
            3 -> 0.6
            2 -> 0.8
            else -> 1.0
        }
    }

    fun getMarked(v: Int): ArrayList<Int> {
        return when (v) {
            7 -> arrayListOf<Int>(2, 6, 8, 10, 12, 22)
            9 -> arrayListOf<Int>(4, 6, 8, 10, 14, 24)
            17 -> arrayListOf<Int>(2, 12, 16, 18, 20, 22)
            19 -> arrayListOf<Int>(4, 14, 16, 18, 20, 24)

            1 -> arrayListOf(2, 3, 4, 6, 11, 16)
            5 -> arrayListOf(2, 3, 4, 10, 15, 20)
            21 -> arrayListOf(6, 11, 16, 22, 23, 24)
            25 -> arrayListOf(10, 15, 20, 22, 23, 24)
            else -> arrayListOf()
        }
    }

    fun calculateHidden11(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val h: Double = subValue_11(Logic.getSel(data.h, list).filter { item -> !item.isClicked }.size)
        val v: Double = subValue_11(Logic.getSel(data.v, list).filter { item -> !item.isClicked }.size)
        val d: Double = subValue_11(Logic.getSel(data.d, list).filter { item -> !item.isClicked }.size)
        var c = 0.0
        if (Logic.CORNERS.contains(clicked)) {
            c = subValue_11(Logic.getSel(Logic.CORNERS, list).filter { item -> !item.isClicked }.size)
        }

        data.hidden = (h + v + d + c).roundOffDecimal3()

        data.subHiddenH = h.roundOffDecimal3()
        data.subHiddenV = v.roundOffDecimal3()
        data.subHiddenD = d.roundOffDecimal3()
        data.subHiddenC = c.roundOffDecimal3()

        val numbers = listOf(data.subHiddenH, data.subHiddenV) //, data.subHiddenD, data.subHiddenC)
        data.hidden = numbers[0]

        for (number in numbers) {
            if (number > data.hidden) {
                data.hidden = number
            }
        }
        data.hidden
        return data
    }

    fun calResult10_Group(list: ArrayList<Data>): Pair<ArrayList<Pair<String, Double>>, ArrayList<Data>> {
        val mList = list
        val group_BO = arrayListOf<Double>()
        val group_15 = arrayListOf<Double>()

        Logic.getRows().forEach {
            var group_BO_count = 0.0
            var group_15_count = 0.0

            if (Logic.getSel(it, list).filter { !it.isClicked && it.group == "B-O" }.size > 0) {
                it.forEach { no ->
                    val item = mList.find { it.number == no }
                    if (item != null && item.group != "1-5" && !item.isClicked) {
                        group_BO_count++
                    }
                }
            }
            if (Logic.getSel(it, list).filter { !it.isClicked && it.group == "1-5" }.size > 0) {
                it.forEach { no ->
                    val item = mList.find { it.number == no }

                    if (item != null && item.group != "B-O" && !item.isClicked) {
                        group_15_count++
                    }
                }
            }
            if (group_BO_count != 0.0)
                group_BO.add((1 / group_BO_count).roundOffDecimal3())
            if (group_15_count != 0.0)
                group_15.add((1 / group_15_count).roundOffDecimal3())
        }
        Logic.getCols().forEach {
            var group_BO_count = 0.0
            var group_15_count = 0.0

            if (Logic.getSel(it, list).filter { !it.isClicked && it.group == "B-O" }.size > 0) {
                it.forEach { no ->
                    val item = mList.find { it.number == no }
                    if (item != null && item.group != "1-5" && !item.isClicked) {
                        group_BO_count++
                    }
                }
            }
            if (Logic.getSel(it, list).filter { !it.isClicked && it.group == "1-5" }.size > 0) {
                it.forEach { no ->
                    val item = mList.find { it.number == no }
                    if (item != null && item.group != "B-O" && !item.isClicked) {
                        group_15_count++
                    }
                }
            }
            if (group_BO_count != 0.0)
                group_BO.add((1 / group_BO_count).roundOffDecimal3())
            if (group_15_count != 0.0)
                group_15.add((1 / group_15_count).roundOffDecimal3())
        }

        val result = arrayListOf<Pair<String, Double>>()

        val avg_BO = if (group_BO.size > 0) (group_BO.sum() / group_BO.size).roundOffDecimal3() else 0.0
        val avg_15 = if (group_15.size > 0) (group_15.sum() / group_15.size).roundOffDecimal3() else 0.0

        result.add(Pair("B-O", avg_BO))
        result.add(Pair("1-5", avg_15))
        return Pair(result, mList)
    }

    fun calculateHidden10(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val h: Double = (1.0 / Logic.getAll(data, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        data.hidden = h
        return data
    }


    fun calResult9(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculateGA(list, list[i - 1], i)
        }
        for (i in 1..25) {
            mList[i - 1] = calculateHidden9(list, list[i - 1], i)
        }
        for (i in 1..25) {
            mList[i - 1] = calculateAverage9(list, list[i - 1], i)
        }

        for (i in 1..25) {
            val data = list[i - 1]
            if (!data.isClicked) {
                mList[i - 1] = calculate9(list, data, i)
            }
        }
        return mList
    }

    fun calculateGA(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val all = Logic.getAll(data, list).filter { !it.isClicked }
        data.genAvrage = (data.bingos.toDouble() / all.size).roundOffDecimal3()
        Log.e(">>genAvrage " + data.number, data.genAvrage.toString())

        return data
    }

    fun calculateHidden9(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val h: Double = (1.0 / Logic.getSel(data.h, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val v: Double = (1.0 / Logic.getSel(data.v, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val d: Double = (1.0 / Logic.getSel(data.d, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        var c = 0.0
        if (Logic.CORNERS.contains(clicked)) {
            c = (1.0 / Logic.getSel(Logic.CORNERS, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        }

        data.hidden = (h + v + d + c).roundOffDecimal3()

        if (data.code === "o5") {
            Log.e(">>>HIDDEN", data.hidden.toString() + " :: " + v)
        }
        data.subHiddenH = h
        data.subHiddenV = v
        data.subHiddenD = d
        data.subHiddenC = c
        return data
    }

    fun calculateAverage9(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0.0
        var count = 0
        val all = Logic.getAll(data, list).filter { !it.isClicked && it.number != data.number }
        all.forEach {
            if (!data.h.contains(it.number)) {
                total += it.genAvrage
                count++
                if (data.number === 20) {
                    Log.e(">>hid", it.code + "  >> " + it.hidden.toString())
                }
            }
        }

        if (count != 0)
            data.avrage = (total / count).roundOffDecimal3()
        else {
//            all.forEach {
//                total += it.hidden
//                count++
//                if (data.number === 25) {
//                    Log.e(">>hid2", it.hidden.toString())
//                }
//            }
            data.avrage = 0.0 //(total / count).roundOffDecimal3()
        }

        if (data.number === 25) {
            Log.e(">>avg ", data.avrage.toString() + " " + total + " / " + count)
        }
        return data
    }

    fun calculate9(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0.0
        var subTotal = 0.0
        var count = 0

        data.h.forEach {
            val d = list.filter { item -> item.number == it }.first()
            if (!d.isClicked) {
                if (d.number != clicked) {
                    if (data.number === 21) {
                        Log.e(">>>", d.code + ">> " + d.avrage + ":" + d.hidden)

                    }
                    if (d.avrage != 0.0) {
                        subTotal += ((d.avrage + d.hidden) / 2).roundOffDecimal3()
                    } else
                        subTotal += d.hidden
                    count++
                } else {
                    Log.e("<<<", d.code + ">> " + d.avrage + ":" + d.hidden)

                }

            }
        }
        if (data.number === 21) {
            Log.e(">>>", data.subHiddenH.toString() + ">> " + subTotal + ":" + count)
        }
        if (count != 0)
            total += (data.subHiddenH * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
        else
            total = (total + 1).roundOffDecimal3()
        subTotal = 0.0
        count = 0
        if (data.number === 21) {
            Log.e(">>>", total.toString())
        }
        data.v.forEach {
            val d = list.filter { item -> item.number == it }.first()
            if (!d.isClicked) {
                if (d.number != clicked) {
                    if (d.avrage != 0.0)
                        subTotal += d.avrage
                    else
                        subTotal += d.hidden
                    count++
                }

            }
        }
        if (count != 0)
            total += (data.subHiddenV * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
        else
            total = (total + 1).roundOffDecimal3()

        subTotal = 0.0
        count = 0

        if (data.number === 21) {
            Log.e(">>>", total.toString())
        }
        if (data.d.size > 0) {
            data.d.forEach {
                val d = list.filter { item -> item.number == it }.first()
                if (!d.isClicked) {
                    if (d.number != clicked) {
                        subTotal += d.avrage
                        count++
                    }

                }
            }
            if (count != 0)
                total += (data.subHiddenD)// * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
            else
                total = (total + 1).roundOffDecimal3()

            subTotal = 0.0
            count = 0
        }
        if (data.number === 21) {
            Log.e(">>>", total.toString())
        }

        if (Logic.CORNERS.contains(clicked)) {
            Logic.CORNERS.forEach {
                val d = list.filter { item -> item.number == it }.first()
                if (!d.isClicked) {
                    if (d.number != clicked) {
                        subTotal += d.avrage
                        count++
                    }

                }
            }

            if (count != 0) {
                total += (data.subHiddenC) //* (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
            } else
                total = (total + 1).roundOffDecimal3()

            subTotal = 0.0
            count = 0
        }
        if (data.number === 21) {
            Log.e(">>>", total.toString())
        }
        data.finalValue9 = total.roundOffDecimal3()
        return data
    }

    fun calAverage9(list: ArrayList<Data>): ArrayList<String> {
        val lines = Logic.createLines()
        val result = arrayListOf<Pair<String, Double>>()
        lines.forEach {
            var temp = 0.0
            val tempArray = arrayListOf<Double>()
            it.second.forEach {
                val data = list.find { e -> e.number == it }
                if (data != null && data.finalValue9 != -1.0 && !data.isClicked) {
                    temp += data.finalValue9
                    tempArray.add(data.finalValue9)
                }
            }
            if (tempArray.size > 0) {
                temp /= tempArray.size
            }
            result.add(Pair(it.first, temp))
        }
        val result2 = arrayListOf<String>()
        result.sortedByDescending { it.second }.forEach {
            result2.add(it.first)
        }
        return result2
    }

    fun calculateHidden9_dep(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val h: Double = (1.0 / Logic.getSel(data.h, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val v: Double = (1.0 / Logic.getSel(data.v, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val d: Double = (1.0 / Logic.getSel(data.d, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        var c = 0.0
        if (Logic.CORNERS.contains(clicked)) {
            c = (1.0 / Logic.getSel(Logic.CORNERS, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        }

        data.hidden = (h + v + d + c).roundOffDecimal3()

        if (data.code === "o5") {
            Log.e(">>>HIDDEN", data.hidden.toString() + " :: " + v)
        }
        data.subHiddenH = h
        data.subHiddenV = v
        data.subHiddenD = d
        data.subHiddenC = c
        return data
    }

    fun calculateAverage9_dep(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0.0
        var count = 0
        val all = Logic.getAll(data, list).filter { !it.isClicked && it.number != data.number }
        all.forEach {
            if (!data.h.contains(it.number)) {
                total += it.hidden
                count++
                if (data.number === 20) {
                    Log.e(">>hid", it.code + "  >> " + it.hidden.toString())
                }
            }
        }

        if (count != 0)
            data.avrage = (total / count).roundOffDecimal3()
        else {
//            all.forEach {
//                total += it.hidden
//                count++
//                if (data.number === 25) {
//                    Log.e(">>hid2", it.hidden.toString())
//                }
//            }
            data.avrage = 0.0 //(total / count).roundOffDecimal3()
        }

        if (data.number === 20) {
            Log.e(">>avg ", data.avrage.toString() + " " + total + " / " + count)
        }
        return data
    }

    fun calculate9_dep(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0.0
        var subTotal = 0.0
        var count = 0

        var temp = 0.0
        data.h.forEach {
            val d = list.filter { item -> item.number == it }.first()
            if (!d.isClicked) {
                if (d.number != clicked) {
                    if (data.number === 19) {
                        Log.e(">>>", d.code + ">> " + d.avrage + ":" + d.hidden)

                    }
                    if (d.avrage != 0.0) {
                        temp = d.hidden
                        subTotal += d.avrage

                    } else
                        subTotal += d.hidden
                    count++
                } else {
                    Log.e("<<<", d.code + ">> " + d.avrage + ":" + d.hidden)

                }

            }
        }
        if (data.number === 19) {
            Log.e(">>>", data.subHiddenH.toString() + ">> " + subTotal + ":" + count)
        }
        if (count == 1)
            total += (data.subHiddenH * ((subTotal + temp) / 2).roundOffDecimal3()).roundOffDecimal3()
        else if (count != 0)
            total += (data.subHiddenH * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
        else
            total = (total + 1).roundOffDecimal3()
        subTotal = 0.0
        count = 0
        if (data.number === 19) {
            Log.e(">>>", total.toString())
        }
        data.v.forEach {
            val d = list.filter { item -> item.number == it }.first()
            if (!d.isClicked) {
                if (d.number != clicked) {
                    if (d.avrage != 0.0)
                        subTotal += d.avrage
                    else
                        subTotal += d.hidden
                    count++
                }

            }
        }
        if (count != 0)
            total += (data.subHiddenV * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
        else
            total = (total + 1).roundOffDecimal3()

        subTotal = 0.0
        count = 0

        if (data.number === 19) {
            Log.e(">>>", total.toString())
        }
        if (data.d.size > 0) {
            data.d.forEach {
                val d = list.filter { item -> item.number == it }.first()
                if (!d.isClicked) {
                    if (d.number != clicked) {
                        subTotal += d.avrage
                        count++
                    }

                }
            }
            if (count != 0)
                total += (data.subHiddenD)// * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
            else
                total = (total + 1).roundOffDecimal3()

            subTotal = 0.0
            count = 0
        }
        if (data.number === 19) {
            Log.e(">>>", total.toString())
        }

        if (Logic.CORNERS.contains(clicked)) {
            Logic.CORNERS.forEach {
                val d = list.filter { item -> item.number == it }.first()
                if (!d.isClicked) {
                    if (d.number != clicked) {
                        subTotal += d.avrage
                        count++
                    }

                }
            }

            if (count != 0) {
                total += (data.subHiddenC) //* (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
            } else
                total = (total + 1).roundOffDecimal3()

            subTotal = 0.0
            count = 0
        }
        if (data.number === 19) {
            Log.e(">>>", total.toString())
        }
        data.finalValue9 = total.roundOffDecimal3()
        return data
    }

}