package `in`.whoguri.bingo.helpers

import android.util.Log
import `in`.whoguri.bingo.Data
import `in`.whoguri.bingo.helpers.Logic.CORNERS
import `in`.whoguri.bingo.helpers.Logic.getHV
import `in`.whoguri.bingo.helpers.Logic.getSel

object NewLogic {
    fun calResult12(list: ArrayList<Data>): ArrayList<Data> {
        if (list.size != 25)
            return Logic.getData()
        val mList = list
        for (i in 1..25) {
            val data = list[i - 1]
            val h: Double = (1.0 / getSel(data.h, list).filter { item -> !item.isClicked }.size).roundOffDecimal4()
            val v: Double = (1.0 / getSel(data.v, list).filter { item -> !item.isClicked }.size).roundOffDecimal4()
            data.subHiddenH = h
            data.subHiddenV = v
            data.hidden = h + v
            data.avrage = (data.hidden / 2).roundOffDecimal4()
            if (CORNERS.contains(i)) {
                data.avrage = (data.avrage * 2).roundOffDecimal4()
            } else if (data.d.size > 0) {
                data.avrage = (data.avrage * 1.5).roundOffDecimal4()
            }
            mList[i - 1] = data
        }
        for (i in 1..25) {
            val data = mList[i - 1]
            var count = 0
            var total = 0.0
            getHV(data, mList).forEach { itt ->
                if (!itt.isClicked && itt.number != data.number) {
                    getHV(itt, mList).forEach {
                        if (!it.isClicked && it.number != data.number) {
                            if(i==12) Log.e(">>>",it.avrage.toString() + data.avrage )
                            if (itt.number == it.number) {
                                total = total + ((it.avrage + data.avrage) / 2).roundOffDecimal4()
                            } else {
                                total = total + ((data.avrage + ((it.avrage + itt.avrage) / 2).roundOffDecimal4()) / 2).roundOffDecimal4()
                            }
                            count++
                        }
                    }
                }
            }
            if (i == 12) Log.e("::>>> B", data.code + " : " + total + " : " + count)
            if (count > 0)
                data.finalValue2 = (total / count).roundOffDecimal4()
            mList[i - 1] = data
        }

        return mList
    }

    fun calResult11P(list: ArrayList<Data>): ArrayList<Data> {
        if (list.size != 25)
            return Logic.getData()
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculateHidden11p(list, list[i - 1])
        }
        for (i in 1..25) {
            mList[i - 1] = calculate11P(list, list[i - 1], i, 16)
        }
        return mList
    }

    fun calResult11(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        if (list.size != 25)
            return Logic.getData()
        for (i in 1..25) {
            mList[i - 1] = calculateHidden11(list, list[i - 1], i)
        }
        var isD = true
        for (i in 1..25) {
            val data = mList[i - 1]
            val selected = Logic.getSel(getMarked(data.number), list).filter { !it.isClicked }
            if (Logic.CORNERS.contains(data.number) && data.d.size > 0) {
                if (selected.size <= 1)
                    isD = false
            } else if (Logic.CORNERS.contains(data.number) || data.d.size > 0) {
                if (selected.size <= 3)
                    isD = false
            }
        }
        for (i in 1..25) {
            mList[i - 1] = calculate11(list, list[i - 1], i, isD, 4)
        }
        return mList
    }

    fun isInvisible(mList: ArrayList<Data>, data: Data, clicked: Int): Boolean {
        var isInv = false
        if (data.number == clicked)
            return isInv
//    for (i in 1..25) {
//        val data = mList[i - 1]
        val selected = Logic.getSel(getMarked(data.number), mList).filter { !it.isClicked }
        if (data.number == 5)
            Log.e(">>>>>>>>>>>>", selected.size.toString())

        if (Logic.CORNERS.contains(data.number) && data.d.size > 0) {
            if (selected.size <= 3)
                isInv = true
        } else if (Logic.CORNERS.contains(data.number) || data.d.size > 0) {
            if (selected.size <= 1)
                isInv = true
        }
        if (data.number == 5)
            Log.e(">>>>>>>>>>>>", isInv.toString())
//    }
        return isInv
    }

    fun calculate11(
        list: ArrayList<Data>,
        data: Data,
        clicked: Int,
        isD: Boolean,
        logN: Int? = null
    ): Data {
        var v = 0.0
        var vs: List<Data> =
            Logic.getSel(data.v, list).filter { !it.isClicked && !isInvisible(list, it, clicked) }
//        if (isD) {
//            vs = Logic.getSel(data.v, list).filter { !it.isClicked }
//        } else {
//            vs = Logic.getSel(data.v, list).filter { !it.isClicked && (!Logic.DIAGONALS.contains(it.number) || it.number == clicked) }
//        }
//        if (clicked == logN)
//            Log.e("VV: " + clicked, vs.size.toString())

        if (vs.size == 1) {
            v = 1.0
        } else {
            var count = 2
            v = data.subHiddenV
            if (clicked == logN)
                Log.e("V1: " + clicked, v.toString() + count)
            v = v + data.subHiddenH
            if (clicked == logN)
                Log.e("V2: " + clicked, v.toString() + count)

            if (data.d.size > 0) {
                count++
                v = v + data.subHiddenD
            }
            if (Logic.CORNERS.contains(data.number)) {
                count++
                v = v + data.subHiddenC
            }
            var hasOther = false

            vs.forEach {
                if (it.number != data.number) {
                    val hsInner = Logic.getSel(it.h, list)
                        .filter { !it.isClicked && !isInvisible(list, it, clicked) }
                    if (hsInner.size > 1) {
                        hasOther = true
                    }
                    if (Logic.CORNERS.contains(it.number) || it.d.size > 0) {
                        v = v + it.hidden
                    } else {
                        v = v + it.subHiddenH
                    }
                    if (clicked == logN)
                        Log.e("V3: " + clicked, v.toString() + count)

                    count++
                }
            }
            v = (v / count).roundOffDecimal3()

            if (clicked == logN)
                Log.e("VV: " + clicked, v.toString() + count)
            if (hasOther) {
                var avrg = (v / vs.size).roundOffDecimal3()
                v = 0.0

                vs.forEach {
                    if (it.number == data.number) {
                        v += avrg
                    } else {
                        val hsInner = Logic.getSel(it.h, list)
                            .filter { !it.isClicked && !isInvisible(list, it, clicked) }
                        var subAvrg = (avrg / hsInner.size).roundOffDecimal3()
                        hsInner.forEach { innerIt ->
                            if (it.number == innerIt.number) {
                                v += subAvrg
                            } else {
                                if (Logic.CORNERS.contains(innerIt.number) || innerIt.d.size > 0) {
                                    v = v + (subAvrg * innerIt.hidden).roundOffDecimal3()
                                } else {
                                    v = v + (subAvrg * innerIt.subHiddenV).roundOffDecimal3()
                                }
                            }
                        }
                    }
                }
                v = v.roundOffDecimal3()
            }
        }

        var h = 0.0
        var hs: List<Data>
//        if (isD) {
        hs = Logic.getSel(data.h, list).filter { !it.isClicked && !isInvisible(list, it, clicked) }
//        } else {
//            hs = Logic.getSel(data.h, list).filter { !it.isClicked && (!Logic.DIAGONALS.contains(it.number) || it.number == clicked) }
//        }

        if (hs.size == 1) {
            h = 1.0
        } else {
            var count = 2
            h = data.subHiddenV
            h = h + data.subHiddenH

            if (data.d.size > 0) {
                count++
                h = h + data.subHiddenD
            }
            if (Logic.CORNERS.contains(data.number)) {
                count++
                h = h + data.subHiddenC
            }
//            h = (h / count).roundOffDecimal3()

//has other
            var hasOther = false
            hs.forEach {
                if (it.number != data.number) {

                    val vsInner = Logic.getSel(it.v, list)
                        .filter { !it.isClicked && !isInvisible(list, it, clicked) }
                    if (vsInner.size > 1) {
                        hasOther = true
//                        vsInner.forEach {
//                            if (clicked == logN)
//                                Log.e("fuckkkkk: " + it.code, it.subHiddenV.toString() + ", " + it.subHiddenH)
//                        }
                    }
//                    else {
                    if (Logic.CORNERS.contains(it.number) || it.d.size > 0) {
                        h = h + it.hidden
                    } else {
                        h = h + it.subHiddenV
                    }
                    count++
//                    }
                }
            }
            h = (h / count).roundOffDecimal3()

//            if (clicked == logN)
//                Log.e("h: " + clicked, h.toString() + ", " + count)

//has other
            if (hasOther) {
                var avrg = (h / hs.size).roundOffDecimal3()
//                if (clicked == logN)
//                    Log.e("h: " + clicked, avrg.toString() + ", " + hs.size)

                h = 0.0
                hs.forEach {
                    if (it.number == data.number) {
                        h += avrg
                    } else {
                        val vsInner = Logic.getSel(it.v, list)
                            .filter { !it.isClicked && !isInvisible(list, it, clicked) }
                        var subAvrg = (avrg / vsInner.size).roundOffDecimal3()
                        vsInner.forEach { innerIt ->
                            if (it.number == innerIt.number) {
                                h += subAvrg
                            } else {
                                h = h + (subAvrg * innerIt.subHiddenH).roundOffDecimal3()
                            }
                        }
                    }
                }
            }
        }

        var d = 0.0
        if (data.d.size > 0) {

            var ds: List<Data>
//            if (isD) {
            ds = Logic.getSel(data.d, list)
                .filter { !it.isClicked && !isInvisible(list, it, clicked) }
//            } else {
//                hs = Logic.getSel(data.h, list).filter { !it.isClicked && (!Logic.DIAGONALS.contains(it.number) || it.number == clicked) }
//            }

            if (ds.size == 1) {
                d = 1.0
            } else {
                var count = 2
                d = data.subHiddenV
                d = d + data.subHiddenH

                if (data.d.size > 0) {
                    count++
                    d = d + data.subHiddenD
                }
                if (Logic.CORNERS.contains(data.number)) {
                    count++
                    d = d + data.subHiddenC
                }
//            h = (h / count).roundOffDecimal3()

//has other
                var hasOther = false
                ds.forEach {
                    if (it.number != data.number) {
                        val vsInner = Logic.getSel(it.d, list)
                            .filter { !it.isClicked && !isInvisible(list, it, clicked) }
                        if (vsInner.size > 1) {
                            hasOther = true
//                        vsInner.forEach {
//                            if (clicked == logN)
//                                Log.e("fuckkkkk: " + it.code, it.subHiddenV.toString() + ", " + it.subHiddenH)
//                        }
                        }
//                    else {
                        if (Logic.CORNERS.contains(it.number) || it.d.size > 0) {
                            d = d + it.hidden
                        } else {
                            d = d + it.subHiddenV
                        }
                        count++
//                    }
                    }
                }
                d = (d / count).roundOffDecimal3()

                if (clicked == logN)
                    Log.e("d: " + clicked, d.toString() + ", " + count)

//has other
                if (hasOther) {
                    var avrg = (d / ds.size).roundOffDecimal3()
                    if (clicked == logN)
                        Log.e("d: avg", avrg.toString() + ", " + hs.size)

                    d = 0.0
                    ds.forEach {
                        if (it.number == data.number) {
                            d += avrg
                            if (clicked == logN)
                                Log.e("d: avg 2", d.toString())

                        } else {
                            val dsInner = Logic.getSel(it.d, list)
                                .filter { !it.isClicked && !isInvisible(list, it, clicked) }
                            var subAvrg = (avrg / dsInner.size).roundOffDecimal3()
                            dsInner.forEach { innerIt ->
                                if (it.number == innerIt.number) {
                                    d += subAvrg
                                    if (clicked == logN)
                                        Log.e("d: avg 3", subAvrg.toString())

                                } else {
                                    d = d + (subAvrg * innerIt.subHiddenD).roundOffDecimal3()
                                    if (clicked == logN)
                                        Log.e(
                                            "d: avg 3",
                                            subAvrg.toString() + " * " + innerIt.subHiddenD
                                        )

                                }
                            }
                        }
                    }
                }
            }
            d = d.roundOffDecimal3()
        }

        var c = 0.0
        if (Logic.CORNERS.contains(data.number)) {
            var cs: List<Data>
//            if (isD) {
            cs = Logic.getSel(Logic.CORNERS, list)
                .filter { !it.isClicked && !isInvisible(list, it, clicked) }
//            } else {
//                hs = Logic.getSel(data.h, list).filter { !it.isClicked && (!Logic.DIAGONALS.contains(it.number) || it.number == clicked) }
//            }

            if (cs.size == 1) {
                c = 1.0
            } else {
                var count = 2
                c = data.subHiddenV
                c = c + data.subHiddenH

                if (data.d.size > 0) {
                    count++
                    c = c + data.subHiddenD
                }
                if (Logic.CORNERS.contains(data.number)) {
                    count++
                    c = c + data.subHiddenC
                }
//            h = (h / count).roundOffDecimal3()

//has other
                var hasOther = false
                cs.forEach {
                    if (it.number != data.number) {
                        val vsInner = Logic.getSel(Logic.DIAGONALS, list)
                            .filter { !it.isClicked && !isInvisible(list, it, clicked) }
                        if (vsInner.size > 1) {
                            hasOther = true
//                        vsInner.forEach {
//                            if (clicked == logN)
//                                Log.e("fuckkkkk: " + it.code, it.subHiddenV.toString() + ", " + it.subHiddenH)
//                        }
                        }
//                    else {
                        if (Logic.CORNERS.contains(it.number) || it.d.size > 0) {
                            c = c + it.hidden
                        } else {
                            c = c + it.subHiddenV
                        }
                        count++
//                    }
                    }
                }
                c = (c / count).roundOffDecimal3()

                if (clicked == logN)
                    Log.e("c: " + clicked, c.toString() + ", " + count)

//has other
                if (hasOther) {
                    var avrg = (c / cs.size).roundOffDecimal3()
//                if (clicked == logN)
//                    Log.e("h: " + clicked, avrg.toString() + ", " + hs.size)

                    h = 0.0
                    cs.forEach {
                        if (it.number == data.number) {
                            c += avrg
                        } else {
                            val csInner = Logic.getSel(Logic.CORNERS, list)
                                .filter { !it.isClicked && !isInvisible(list, it, clicked) }
                            var subAvrg = (avrg / csInner.size).roundOffDecimal3()
                            csInner.forEach { innerIt ->
                                if (it.number == innerIt.number) {
                                    c += subAvrg
                                } else {
                                    c = c + (subAvrg * innerIt.subHiddenC).roundOffDecimal3()
                                }
                            }
                        }
                    }
                }
            }
        }

        if (clicked == logN)
            Log.e("f " + clicked, v.toString() + ", " + h + ", " + d + ", " + c)

        data.finalValue2 = (h + v + c + d).roundOffDecimal3()
        return data
    }

    fun calculate11P(list: ArrayList<Data>, data: Data, clicked: Int, logN: Int? = null): Data {
        var v = 0.0
        var vs: List<Data>
        vs = Logic.getSel(data.v, list).filter { !it.isClicked }

        if (vs.size == 1) {
            v = 1.0
        } else {
            var count = 2
            v = data.subHiddenV
            v = v + data.subHiddenH

            vs.forEach {
                if (it.number != data.number) {
                    val hsInner = Logic.getSel(it.h, list).filter { !it.isClicked }
                    if (hsInner.size > 1) {
                        var innerTotal: Double = it.subHiddenV + it.subHiddenH
                        var innerCount = 2

                        hsInner.forEach { inner ->
                            if (inner.number != it.number) {
                                innerTotal += inner.subHiddenV
                                innerCount++
                            }
                        }
                        v = v + (innerTotal / innerCount)
                        count++

                    } else {
                        v = v + it.subHiddenH
                        count++
                    }
                }
            }
            v = (v / count).roundOffDecimal3()
        }


        var h = 0.0
        var hs: List<Data>
        hs = Logic.getSel(data.h, list).filter { !it.isClicked }

        if (hs.size == 1) {
            h = 1.0
        } else {
            var count = 2
            h = data.subHiddenV
            h = h + data.subHiddenH

            hs.forEach {
                if (it.number != data.number) {

                    val vsInner = Logic.getSel(it.v, list).filter { !it.isClicked }
                    if (vsInner.size > 1) {
                        var innerTotal: Double = it.subHiddenV + it.subHiddenH
                        var innerCount = 2

                        vsInner.forEach { inner ->
                            if (inner.number != it.number) {
                                innerTotal += inner.subHiddenH
                                innerCount++
                            }
                        }
                        h = h + (innerTotal / innerCount)
                        count++

                    } else {
                        h = h + it.subHiddenV
                        count++
                    }
                }
            }
            h = (h / count).roundOffDecimal3()
        }

        if (clicked == logN)
            Log.e("f " + clicked, v.toString() + ", " + h)

        data.finalValue2 = (h + v).roundOffDecimal3()
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
        val h: Double = subValue_11(
            getSel(data.h, list)
                .filter { item -> !item.isClicked && !isInvisible(list, item, clicked) }.size
        )
        val v: Double = subValue_11(
            getSel(data.v, list)
                .filter { item -> !item.isClicked && !isInvisible(list, item, clicked) }.size
        )
        var d = 0.0
        if (data.d.size > 0) {
            d = subValue_11(
                getSel(data.d, list)
                    .filter { item -> !item.isClicked && !isInvisible(list, item, clicked) }.size
            )
        }
        var c = 0.0
        if (Logic.CORNERS.contains(clicked)) {
            c = subValue_11(
                getSel(CORNERS, list)
                    .filter { item -> !item.isClicked && !isInvisible(list, item, clicked) }.size
            )
        }

        data.subHiddenH = h.roundOffDecimal3()
        data.subHiddenV = v.roundOffDecimal3()
        data.subHiddenD = d.roundOffDecimal3()
        data.subHiddenC = c.roundOffDecimal3()

        if (data.number == 4) {
            Log.e(">>> h", data.subHiddenH.toString())
            Log.e(">>> v", data.subHiddenV.toString())
            Log.e(">>> d", data.subHiddenD.toString())
            Log.e(">>> c", data.subHiddenC.toString())
        }
        val numbers = listOf(data.subHiddenH, data.subHiddenV, data.subHiddenD, data.subHiddenC)
        data.hidden = numbers[0]

        for (number in numbers) {
            if (number > data.hidden) {
                data.hidden = number
            }
        }
        data.hidden
        return data
    }

    fun calculateHidden11p(list: ArrayList<Data>, data: Data): Data {
        val h: Double = subValue_11(getSel(data.h, list).filter { item -> !item.isClicked }.size)
        val v: Double = subValue_11(getSel(data.v, list).filter { item -> !item.isClicked }.size)
        var d = 0.0
        var c = 0.0

        data.subHiddenH = h.roundOffDecimal3()
        data.subHiddenV = v.roundOffDecimal3()
        data.subHiddenD = d.roundOffDecimal3()
        data.subHiddenC = c.roundOffDecimal3()

        val numbers = listOf(data.subHiddenH, data.subHiddenV, data.subHiddenD, data.subHiddenC)
        data.hidden = numbers[0]
        for (number in numbers) {
            if (number > data.hidden) {
                data.hidden = number
            }
        }
        data.hidden
        return data
    }

    fun calResult10_GroupNew(list: ArrayList<Data>): Pair<ArrayList<Pair<String, Double>>, ArrayList<Data>> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1].hidden = 0.0
        }

        if (mList.find { !it.isClicked && (it.code == "b3" || it.code == "o3") }
                .toString() != "null") {
            mList[12 - 1].group = "B-O,1-5"
            mList[14 - 1].group = "B-O,1-5"
        } else {
            mList[12 - 1].group = "1-5"
            mList[14 - 1].group = "1-5"
        }
        if (mList.find { !it.isClicked && (it.code == "n2" || it.code == "n4") }
                .toString() != "null") {
            mList[3 - 1].group = "B-O,1-5"
            mList[23 - 1].group = "B-O,1-5"
        } else {
            mList[3 - 1].group = "1-5"
            mList[23 - 1].group = "1-5"
        }
        if (mList.find { !it.isClicked && (it.code == "i3" || it.code == "g3") }
                .toString() != "null") {
            mList[11 - 1].group = "1-5,B-O"
            mList[15 - 1].group = "1-5,B-O"
        } else {
            mList[11 - 1].group = "B-O"
            mList[15 - 1].group = "B-O"
        }
        if (mList.find { !it.isClicked && (it.code == "n1" || it.code == "n5") }
                .toString() != "null") {
            mList[8 - 1].group = "1-5,B-O"
            mList[18 - 1].group = "1-5,B-O"
        } else {
            mList[8 - 1].group = "B-O"
            mList[18 - 1].group = "B-O"
        }

        val result = arrayListOf<Pair<String, Double>>()
//        Log.e("10 ## " + (group_15.sum() / group_15.size), group_15.sum().toString() + " : " + group_15.size)
        val avg_BO = mList.filter { !it.isClicked && it.group.contains("B-O") }.size.toDouble()
        val avg_15 = mList.filter { !it.isClicked && it.group.contains("1-5") }.size.toDouble()

        result.add(Pair("B-O", avg_BO))
        result.add(Pair("1-5", avg_15))
        return Pair(result, mList)
    }

    fun calResult10_Group(list: ArrayList<Data>): Pair<ArrayList<Pair<String, Double>>, ArrayList<Data>> {
        val mList = list
        val group_BO = arrayListOf<Double>()
        val group_15 = arrayListOf<Double>()
        for (i in 1..25) {
            mList[i - 1].hidden = 0.0
        }

        Logic.getRows().forEachIndexed { index, it ->
            var group_BO_count = 0.0
            var group_15_count = 0.0

            if (index == 1 || index == 2 || index == 3)
                if (Logic.getSel(it, list)
                        .filter { !it.isClicked && it.group.contains("B-O") }.size > 0
                ) {
                    it.forEach { no ->
                        val item = mList.find { it.number == no }
                        if (item != null && !item.isClicked) {
                            group_BO_count++
                        }
                    }
                }
            if (index == 0 || index == 2 || index == 4)
                if (Logic.getSel(it, list)
                        .filter { !it.isClicked && it.group.contains("1-5") }.size > 0
                ) {
                    it.forEach { no ->
                        val item = mList.find { it.number == no }
                        if (item != null && !item.isClicked) {
                            group_15_count++
                        }
                    }
                }
            if (group_BO_count != 0.0)
                group_BO.add((1 / group_BO_count).roundOffDecimal3())
            if (group_15_count != 0.0)
                group_15.add((1 / group_15_count).roundOffDecimal3())
            if (group_BO_count != 0.0)
                Log.e("10 ## R" + (index + 1), group_BO_count.toString() + " : " + group_15.size)

        }
        Logic.getCols().forEachIndexed { index, it ->
            var group_BO_count = 0.0
            var group_15_count = 0.0

            if (index == 0 || index == 2 || index == 4)
                if (Logic.getSel(it, list)
                        .filter { !it.isClicked && it.group.contains("B-O") }.size > 0
                ) {
                    it.forEach { no ->
                        val item = mList.find { it.number == no }
                        if (item != null && !item.isClicked) {
                            group_BO_count++
                        }
                    }
                }
            if (index == 1 || index == 2 || index == 3)
                if (Logic.getSel(it, list)
                        .filter { !it.isClicked && it.group.contains("1-5") }.size > 0
                ) {
                    it.forEach { no ->
                        val item = mList.find { it.number == no }
                        if (item != null && !item.isClicked) {
                            group_15_count++
                        }
                    }
                }
            if (group_BO_count != 0.0)
                group_BO.add((1 / group_BO_count).roundOffDecimal3())
            if (group_15_count != 0.0)
                group_15.add((1 / group_15_count).roundOffDecimal3())
            if (group_BO_count != 0.0)
                Log.e("10 ## C" + (index + 1), group_BO_count.toString() + " : " + group_15.size)

        }

        val result = arrayListOf<Pair<String, Double>>()
//        Log.e("10 ## " + (group_15.sum() / group_15.size), group_15.sum().toString() + " : " + group_15.size)
        val avg_BO =
            if (group_BO.size > 0) (group_BO.sum() / group_BO.size).roundOffDecimal3() else 0.0
        val avg_15 =
            if (group_15.size > 0) (group_15.sum() / group_15.size).roundOffDecimal3() else 0.0

        result.add(Pair("B-O", avg_BO))
        result.add(Pair("1-5", avg_15))
        return Pair(result, mList)
    }

    fun calculateHidden10(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val h: Double = (1.0 / Logic.getAll(data, list)
            .filter { item -> !item.isClicked }.size).roundOffDecimal3()
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
        val h: Double = (1.0 / Logic.getSel(data.h, list)
            .filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val v: Double = (1.0 / Logic.getSel(data.v, list)
            .filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val d: Double = (1.0 / Logic.getSel(data.d, list)
            .filter { item -> !item.isClicked }.size).roundOffDecimal3()
        var c = 0.0
        if (Logic.CORNERS.contains(clicked)) {
            c = (1.0 / Logic.getSel(Logic.CORNERS, list)
                .filter { item -> !item.isClicked }.size).roundOffDecimal3()
        }

        data.hidden = (h + v + d + c).roundOffDecimal3()

        if (data.code == "o5") {
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
                if (data.number == 20) {
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
//                if (data.number == 25) {
//                    Log.e(">>hid2", it.hidden.toString())
//                }
//            }
            data.avrage = 0.0 //(total / count).roundOffDecimal3()
        }

        if (data.number == 25) {
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
                    if (data.number == 21) {
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
        if (data.number == 21) {
            Log.e(">>>", data.subHiddenH.toString() + ">> " + subTotal + ":" + count)
        }
        if (count != 0)
            total += (data.subHiddenH * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
        else
            total = (total + 1).roundOffDecimal3()
        subTotal = 0.0
        count = 0
        if (data.number == 21) {
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

        if (data.number == 21) {
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
        if (data.number == 21) {
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
        if (data.number == 21) {
            Log.e(">>>", total.toString())
        }
        data.finalValue2 = total.roundOffDecimal3()
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
                if (data != null && data.finalValue2 != -1.0 && !data.isClicked) {
                    temp += data.finalValue2
                    tempArray.add(data.finalValue2)
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
        val h: Double = (1.0 / Logic.getSel(data.h, list)
            .filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val v: Double = (1.0 / Logic.getSel(data.v, list)
            .filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val d: Double = (1.0 / Logic.getSel(data.d, list)
            .filter { item -> !item.isClicked }.size).roundOffDecimal3()
        var c = 0.0
        if (Logic.CORNERS.contains(clicked)) {
            c = (1.0 / Logic.getSel(Logic.CORNERS, list)
                .filter { item -> !item.isClicked }.size).roundOffDecimal3()
        }

        data.hidden = (h + v + d + c).roundOffDecimal3()

        if (data.code == "o5") {
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
                if (data.number == 20) {
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
//                if (data.number == 25) {
//                    Log.e(">>hid2", it.hidden.toString())
//                }
//            }
            data.avrage = 0.0 //(total / count).roundOffDecimal3()
        }

        if (data.number == 20) {
            Log.e(">>avg ", data.avrage.toString() + " " + total + " / " + count)
        }
        return data
    }

}