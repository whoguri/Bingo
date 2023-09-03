package `in`.whoguri.bingo

import android.util.Log

object Logic {

    val CORNERS = arrayListOf(1, 5, 21, 25)
    fun getAll(data: Data, list: ArrayList<Data>): ArrayList<Data> {
        val all = arrayListOf<Int>()
        val allData = arrayListOf<Data>()
        data.h.forEach {
            all.add(it)
        }
        data.v.forEach {
            if (all.filter { item -> item == it }.isEmpty()) {
                all.add(it)
            }
        }
        data.d.forEach {
            if (all.filter { item -> item == it }.isEmpty()) {
                all.add(it)
            }
        }
        list.forEach {
            val has = all.filter { i -> i == it.number }.isNotEmpty()
            if (has) {
                allData.add(it)
            }
        }
        return allData
    }

    fun getSel(all: ArrayList<Int>, list: ArrayList<Data>): ArrayList<Data> {
        val allData = arrayListOf<Data>()

        list.forEach {
            val has = all.filter { i -> i == it.number }.isNotEmpty()
            if (has) {
                allData.add(it)
            }
        }
        return allData
    }

    fun calAverage(list: ArrayList<Data>): ArrayList<String> {
        val lines = createLines()
        val result = arrayListOf<Pair<String, Int>>()
        lines.forEach {
            var temp = 0
            val tempArray = arrayListOf<Int>()
            it.second.forEach {
                val data = list.find { e -> e.number == it }
                if (data != null && data.finalValue != -1 && !data.isClicked) {
                    temp += data.finalValue
                    tempArray.add(data.finalValue)
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

    fun calAverage2(list: ArrayList<Data>): ArrayList<String> {
        val lines = createLines()
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

    fun calAverage3(list: ArrayList<Data>): ArrayList<String> {
        val lines = createLines()
        val result = arrayListOf<Pair<String, Double>>()
        lines.forEach {
            var temp = 0.0
            val tempArray = arrayListOf<Double>()
            it.second.forEach {
                val data = list.find { e -> e.number == it }
                if (data != null && data.finalValue3 != -1.0 && !data.isClicked) {
                    temp += data.finalValue3
                    tempArray.add(data.finalValue3)
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

    fun calAverage4(list: ArrayList<Data>): ArrayList<String> {
        val lines = createLines()
        val result = arrayListOf<Pair<String, Double>>()
        lines.forEach {
            var temp = 0.0
            val tempArray = arrayListOf<Double>()
            it.second.forEach {
                val data = list.find { e -> e.number == it }
                if (data != null && data.finalValue4 != -1.0 && !data.isClicked) {
                    temp += data.finalValue4
                    tempArray.add(data.finalValue4)
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

    fun calAverage5(list: ArrayList<Data>): ArrayList<String> {
        val lines = createLines()
        val result = arrayListOf<Pair<String, Double>>()
        lines.forEach {
            var temp = 0.0
            val tempArray = arrayListOf<Double>()
            it.second.forEach {
                val data = list.find { e -> e.number == it }
                if (data != null && data.finalValue5 != -1.0 && !data.isClicked) {
                    temp += data.finalValue5
                    tempArray.add(data.finalValue5)
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

    fun calResult(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculate(list, list[i - 1], i)
        }
        return mList
    }

    fun calculate(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0
        var total2 = 0
        var count = 0

        var total4 = 0

        data.h.forEach {
            var hn = 1
            val d = list.filter { item -> item.number == it }.first()
            val all = getAll(d, list)
            val size = all.filter { item -> item.isClicked }.size
            hn += size
            if (d.isClicked || d.number == clicked) {
                total += d.selfValue
            } else {
                total += (d.bingos * hn)
            }
            if (!d.isClicked) {
                if (it == clicked) {
                    total2 += d.selfValue
                } else {
                    total2 += (d.bingos * hn)
                }
                if (clicked == 4) {
//                    Log.e("####", d.bingos.toString() +" : "+ hn+" = "+ total4)
                }
                total4 += (d.bingos * hn)
                count++
            }
        }

        data.v.forEach {
            var vn = 1
            val hd = data.h.filter { item -> item == it }.firstOrNull()
            if (hd == null) {
                val d = list.filter { item -> item.number == it }.first()
                val all = getAll(d, list)
                val size = all.filter { item -> item.isClicked }.size
                vn += size

                if (d.isClicked || d.number == clicked) {
                    total += d.selfValue
                } else {
                    total += (d.bingos * vn)
                }

                if (!d.isClicked) {
                    if (d.number == clicked) {
                        total2 += d.selfValue
                    } else {
                        total2 += (d.bingos * vn)
                    }
                    if (clicked == 4) {
//                        Log.e("####", d.bingos.toString() +" : "+ vn+" = "+ total4)
                    }
                    total4 += (d.bingos * vn)
                    count++

                }
            }
        }

        data.d.forEach {
            val hd = data.h.filter { item -> item == it }.firstOrNull()
            val vd = data.v.filter { item -> item == it }.firstOrNull()
            if (hd == null && vd == null) {
                val d = list.filter { item -> item.number == it }.first()
                var dn = 1
                val all = getAll(d, list)
                val size = all.filter { item -> item.isClicked }.size
                dn += size
                if (d.isClicked || d.number == clicked) {
                    total += d.selfValue
                } else {
                    total = total + (d.bingos * dn)
                }
                if (!d.isClicked) {
                    if (d.number == clicked) {
                        total2 += d.selfValue
                    } else {
                        total2 += (d.bingos * dn)
                    }
                    if (clicked == 4) {
//                        Log.e("####", d.bingos.toString() +" : "+ dn+" = "+ total4)
                    }
                    total4 += (d.bingos * dn)
                    count++
                }

            }
        }

        data.finalValue = total
        if (count > 0) {
            data.finalValue2 = ((total2).toDouble() / count).roundOffDecimal3()
//            Log.e(">>>", ""+total4+" : "+count)
            data.finalValue4 =
                (((total4).toDouble() / count).roundOffDecimal3() * data.bingos).roundOffDecimal2()
        }

        return data
    }

    fun calResult3(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculate3(list, list[i - 1], i)
        }
        return mList
    }

    fun calculate3(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0.0
        val sel = list.filter { item -> item.number == clicked }.first()
        val allSel = getAll(sel, list)

        allSel.filter { item -> !item.isClicked }.forEach {
            val allSel = getAll(it, list)
            val sizeSel: Double = allSel.filter { item -> !item.isClicked }.size.toDouble()
            total += (sizeSel / it.bingos).roundOffDecimal2()
        }
        data.finalValue3 = (total / data.bingos).roundOffDecimal2()

        return data
    }

    fun calResult5(list: ArrayList<Data>, variant: Int = 1): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculateHidden5(list, list[i - 1], i)
        }
        var isD = false
        if (variant == 2) {
            arrayListOf<Int>(1, 7, 19, 25, 5, 9, 17, 21).forEach {
                val item = list[it - 1]
                if (!item.isClicked) {
                    isD = true
                }
            }
        }
        for (i in 1..25) {
            val data = list[i - 1]
            if (!data.isClicked) {
                if (variant == 1) {
//                    isD = data.d.size > 0
                }
                mList[i - 1] = calculate5(list, data, i, isD, variant)
            }
        }
        return mList
    }

    fun calculateHidden5(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val h: Double = (1.0 / getSel(data.h, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val v: Double = (1.0 / getSel(data.v, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val d: Double = (1.0 / getSel(data.d, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        var c = 0.0
        if (CORNERS.contains(clicked)) {
            c = (1.0 / getSel(CORNERS, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        }
        data.hidden = h + v + d + c
        data.subHiddenH = h
        data.subHiddenV = v
        data.subHiddenD = d
        data.subHiddenC = c
        return data
    }

    fun calculate5(list: ArrayList<Data>, data: Data, clicked: Int, isD: Boolean, variant: Int): Data {
        var total = 0.0
        var subTotal = 0.0
        var count = 0
        var q = 1

        if (!isD) {
            q = 0
        }

        data.h.forEach {
            val d = list.filter { item -> item.number == it }.first()
            if (!d.isClicked) {
                if (!isD) {
                    if (d.number != clicked) {
                        subTotal += d.hidden
                        count++
                    }
                } else {
                    subTotal += d.hidden
                    count++
                }
            }
        }

        if (count != q)
            total += (data.subHiddenH * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
        else
            total = (total + 1).roundOffDecimal3()
        subTotal = 0.0
        count = 0

        data.v.forEach {
            val d = list.filter { item -> item.number == it }.first()
            if (!d.isClicked) {
                if (!isD) {
                    if (d.number != clicked) {
                        subTotal += d.hidden
                        count++
                    }
                } else {
                    subTotal += d.hidden
                    count++
                }
            }
        }
        if (count != q)
            total += (data.subHiddenV * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
        else
            total = (total + 1).roundOffDecimal3()

        subTotal = 0.0
        count = 0

        if (data.d.size > 0) {
            data.d.forEach {
                val d = list.filter { item -> item.number == it }.first()
                if (!d.isClicked) {
                    if (!isD) {
                        if (d.number != clicked) {
                            subTotal += d.hidden
                            count++
                        }
                    } else {
                        subTotal += d.hidden
                        count++
                    }
                }
            }
            if (count != q)
                total += (data.subHiddenD * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
            else
                total = (total + 1).roundOffDecimal3()

            subTotal = 0.0
            count = 0
        }

        if (CORNERS.contains(clicked)) {
            CORNERS.forEach {
                val d = list.filter { item -> item.number == it }.first()
                if (!d.isClicked) {
                    if (!isD) {
                        if (d.number != clicked) {
                            subTotal += d.hidden
                            count++
                        }
                    } else {
                        subTotal += d.hidden
                        count++
                    }
                }
            }

            if (count != q) {
                if (variant == 1)
                    total += (data.subHiddenC * (subTotal / count).roundOffDecimal3()).roundOffDecimal3()
                else
                    total += (subTotal / count).roundOffDecimal3()
            } else
                total = (total + 1).roundOffDecimal3()

            subTotal = 0.0
            count = 0
        }
        if (variant == 1)
            data.finalValue5 = total.roundOffDecimal3()
        else
            data.finalValue6 = total.roundOffDecimal3()
        return data
    }

    fun calResult7(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculateAvrg7(list, list[i - 1], i)
        }
        for (i in 1..25) {
            mList[i - 1] = calculate7(list, list[i - 1], i)
        }
        return mList
    }

    fun calculateAvrg7(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val h: Int = getSel(data.h, list).filter { item -> !item.isClicked }.size
        val v: Int = getSel(data.v, list).filter { item -> !item.isClicked }.size - 1
        var d: Int = 0

        if (data.d.size > 0)
            d = getSel(data.d, list).filter { item -> !item.isClicked }.size - 1
        var c = 0
        if (CORNERS.contains(clicked)) {
            c = getSel(CORNERS, list).filter { item -> !item.isClicked }.size - 1
        }
        data.avrage = (data.finalValue5 / (h + v + d + c)).roundOffDecimal3()
        return data
    }

    fun calculate7(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0.0
        var count = 0

        count = getSel(data.h, list).filter { !it.isClicked }.size

        if (count == 1)
            total += 1
        else if (count != 0) {
            data.h.forEach {
                val d = list.firstOrNull { item -> item.number == it && !item.isClicked && item.number != clicked }
                if (d != null)
                    total = total + ((1.0 / count) * d.avrage).roundOffDecimal3()
            }
        }
        if (clicked == 20)
            Log.e("Total", total.toString())
        count = getSel(data.v, list).filter { !it.isClicked }.size

        if (count == 1)
            total += 1
        else if (count != 0) {
            data.v.forEach {
                val d = list.firstOrNull { item -> item.number == it && !item.isClicked && item.number != clicked }
                if (d != null)
                    total = total + ((1.0 / count) * d.avrage).roundOffDecimal3()
            }
        }

        if (clicked == 20)
            Log.e("Total", total.toString())

        if (data.d.size > 0) {
            count = getSel(data.d, list).filter { !it.isClicked }.size

            if (count == 1)
                total += 1
            else if (count != 0) {
                data.d.forEach {
                    val d = list.firstOrNull { item -> item.number == it && !item.isClicked && item.number != clicked }
                    if (d != null)
                        total = total + ((1.0 / count) * d.avrage).roundOffDecimal3()
                }
            }
        }
        if (clicked == 20)
            Log.e("Total", total.toString())

        if (CORNERS.contains(clicked)) {
            count = getSel(CORNERS, list).filter { !it.isClicked }.size

            if (count == 1)
                total += 1
            else if (count != 0) {
                CORNERS.forEach {
                    val d = list.firstOrNull { item -> item.number == it && !item.isClicked && item.number != clicked }
                    if (d != null)
                        total = total + ((1.0 / count) * d.avrage).roundOffDecimal3()
                }
            }
        }
        if (clicked == 20)
            Log.e("Total", total.toString())

        data.finalValue7 = total.roundOffDecimal3()
        return data
    }

    fun calResult5_dep(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculateHidden5_dep(list, list[i - 1], i)
        }
        for (i in 1..25) {
            mList[i - 1] = calculate5_dep(list, list[i - 1], i)
        }
        return mList
    }

    fun calculateHidden5_dep(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        val h: Double = (1.0 / getSel(data.h, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val v: Double = (1.0 / getSel(data.v, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        val d: Double = (1.0 / getSel(data.d, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        var c = 0.0
        if (CORNERS.contains(clicked)) {
            c = (1.0 / getSel(CORNERS, list).filter { item -> !item.isClicked }.size).roundOffDecimal3()
        }
        data.hidden = h + v + d + c
        data.subHiddenH = h
        data.subHiddenV = v
        data.subHiddenD = d
        data.subHiddenC = c
        return data
    }

    fun calculate5_dep(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0.0
        var subTotal = 0.0
        var count = 0

        data.h.forEach {
            val d = list.filter { item -> item.number == it }.first()
            if (!d.isClicked && d.number != clicked) {
                subTotal += d.hidden
                count++
            }
        }
        if (count != 0)
            total += data.subHiddenH * (subTotal / count).roundOffDecimal3()
        else
            total += 1

        subTotal = 0.0
        count = 0

        data.v.forEach {
            val d = list.filter { item -> item.number == it }.first()
            if (!d.isClicked && d.number != clicked) {
                subTotal += d.hidden
                count++
            }
        }
        if (count != 0)
            total += (data.subHiddenV * (subTotal / count)).roundOffDecimal3()
        else
            total += 1

        subTotal = 0.0
        count = 0

        if (data.d.size > 0) {
            data.d.forEach {
                val d = list.filter { item -> item.number == it }.first()
                if (!d.isClicked && d.number != clicked) {
                    subTotal += d.hidden
                    count++
                }
            }
            if (count != 0)
                total += data.subHiddenD * (subTotal / count).roundOffDecimal3()
            else
                total += 1

            subTotal = 0.0
            count = 0
        }

        if (CORNERS.contains(clicked)) {
            CORNERS.forEach {
                val d = list.filter { item -> item.number == it }.first()
                if (!d.isClicked && d.number != clicked) {
                    subTotal += d.hidden
                    count++
                }
            }
            if (count != 0)
                total += data.subHiddenC * (subTotal / count).roundOffDecimal3()
            else
                total += 1

            subTotal = 0.0
            count = 0
        }

        data.finalValue5 = total.roundOffDecimal3()

        return data
    }

    fun calResult3dep(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculate3dep(list, list[i - 1], i)
        }
        return mList
    }

    fun calculate3dep(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0.0
        var init = 0.0

        val sel = list.filter { item -> item.number == clicked }.first()
        val allSel = getAll(sel, list)

        var n = 0
        val sizeSel = allSel.filter { item -> !item.isClicked }.size
        n += sizeSel
        total += sel.selfValue.toDouble() / n
        init = total

        data.h.forEach {
            val d = list.filter { item -> item.number == it }.first()
            val all = getAll(d, list)

            if (d.number == clicked) {
            } else if (d.isClicked) {
                total += init
            } else {
                var hn = 1.0
                val s = all.size
                val size = all.filter { item -> item.isClicked }.size
                hn += size
                total += (init * (hn / s)).roundOffDecimal2()
            }
        }

        data.v.forEach {
            val hd = data.h.filter { item -> item == it }.firstOrNull()
            if (hd == null) {
                val d = list.filter { item -> item.number == it }.first()
                val all = getAll(d, list)

                if (d.number == clicked) {
                } else if (d.isClicked) {
                    total += init
                } else {
                    var vn = 1.0
                    val s = all.size
                    val size = all.filter { item -> item.isClicked }.size
                    vn += size
                    total += (init * (vn / s)).roundOffDecimal2()
//                    if (clicked == 4)
//                        Log.e(" 2 >>>>> " + d.number, " " + (init * (vn / s)) + " , " + vn + " " + s+" >> "+(vn/s))

                }
            }
        }

        data.d.forEach {
            val hd = data.h.filter { item -> item == it }.firstOrNull()
            val vd = data.v.filter { item -> item == it }.firstOrNull()
            if (hd == null && vd == null) {
                val d = list.filter { item -> item.number == it }.first()
                val all = getAll(d, list)
                if (d.number == clicked) {
                } else if (d.isClicked) {
                    total += init
                } else {
                    var dn = 1.0
                    val s = all.size
                    val size = all.filter { item -> item.isClicked }.size
                    dn += size
                    total += (init * (dn / s)).roundOffDecimal2()
//                    if (clicked == 4)
//                        Log.e(" 2 >>>>> " + d.number, " " + (init * (dn / s)) + " , " + dn + " " + s+" >> "+(dn/s))

                }
            }
        }
        data.finalValue3 = total.roundOffDecimal2()
        return data
    }

    fun getData(): ArrayList<Data> {
        val list = arrayListOf<Data>()
        list.add(
            Data(
                1,
                "b1",
                4,
                48,
                arrayListOf(1, 2, 3, 4, 5),
                arrayListOf(1, 6, 11, 16, 21),
                arrayListOf(1, 7, 19, 25) //check 1
            )
        )
        list.add(
            Data(
                2,
                "i1",
                2,
                18,
                arrayListOf(1, 2, 3, 4, 5),
                arrayListOf(2, 7, 12, 17, 22),
                arrayListOf()
            )
        )
        list.add(
            Data(
                3,
                "n1",
                2,
                16,
                arrayListOf(1, 2, 3, 4, 5),
                arrayListOf(3, 8, 18, 23),
                arrayListOf()
            )
        )
        list.add(
            Data(
                4,
                "g1",
                2,
                18,
                arrayListOf(1, 2, 3, 4, 5),
                arrayListOf(4, 9, 14, 19, 24),
                arrayListOf()
            )
        )
        list.add(
            Data(
                5,
                "o1",
                4,
                48,
                arrayListOf(1, 2, 3, 4, 5),
                arrayListOf(5, 10, 15, 20, 25),
                arrayListOf(5, 9, 17, 21)
            )
        )

        list.add(
            Data(
                6,
                "b2",
                2,
                18,
                arrayListOf(6, 7, 8, 9, 10),
                arrayListOf(1, 6, 11, 16, 21),
                arrayListOf()
            )
        )
        list.add(
            Data(
                7,
                "i2",
                3,
                36,
                arrayListOf(6, 7, 8, 9, 10),
                arrayListOf(2, 7, 12, 17, 22),
                arrayListOf(1, 7, 19, 25)
            )
        )
        list.add(
            Data(
                8,
                "n2",
                2,
                16,
                arrayListOf(6, 7, 8, 9, 10),
                arrayListOf(3, 8, 18, 23),
                arrayListOf()
            )
        )
        list.add(
            Data(
                9,
                "g2",
                3,
                36,
                arrayListOf(6, 7, 8, 9, 10),
                arrayListOf(4, 9, 14, 19, 24),
                arrayListOf(5, 9, 17, 21)
            )
        )
        list.add(
            Data(
                10,
                "o2",
                2,
                18,
                arrayListOf(6, 7, 8, 9, 10),
                arrayListOf(5, 10, 15, 20, 25),
                arrayListOf()
            )
        )

        list.add(
            Data(
                11,
                "b3",
                2,
                16,
                arrayListOf(11, 12, 14, 15),
                arrayListOf(1, 6, 11, 16, 21),
                arrayListOf()
            )
        )
        list.add(
            Data(
                12,
                "i3",
                2,
                16,
                arrayListOf(11, 12, 14, 15),
                arrayListOf(2, 7, 12, 17, 22),
                arrayListOf()
            )
        )
        list.add(Data(13, "n3", 0, 0, arrayListOf(), arrayListOf(), arrayListOf(), 0))
        list.add(
            Data(
                14,
                "g3",
                2,
                16,
                arrayListOf(11, 12, 14, 15),
                arrayListOf(4, 9, 14, 19, 24),
                arrayListOf()
            )
        )
        list.add(
            Data(
                15,
                "o3",
                2,
                16,
                arrayListOf(11, 12, 14, 15),
                arrayListOf(5, 10, 15, 20, 25),
                arrayListOf()
            )
        )

        list.add(
            Data(
                16,
                "b4",
                2,
                18,
                arrayListOf(16, 17, 18, 19, 20),
                arrayListOf(1, 6, 11, 16, 21),
                arrayListOf()
            )
        )
        list.add(
            Data(
                17,
                "i4",
                3,
                36,
                arrayListOf(16, 17, 18, 19, 20),
                arrayListOf(2, 7, 12, 17, 22),
                arrayListOf(5, 9, 17, 21)
            )
        )
        list.add(
            Data(
                18,
                "n4",
                2,
                16,
                arrayListOf(16, 17, 18, 19, 20),
                arrayListOf(3, 8, 18, 23),
                arrayListOf()
            )
        )
        list.add(
            Data(
                19,
                "g4",
                3,
                36,
                arrayListOf(16, 17, 18, 19, 20),
                arrayListOf(4, 9, 14, 19, 24),
                arrayListOf(1, 7, 19, 25)
            )
        )
        list.add(
            Data(
                20,
                "o4",
                2,
                18,
                arrayListOf(16, 17, 18, 19, 20),
                arrayListOf(5, 10, 15, 20, 25),
                arrayListOf()
            )
        )

        list.add(
            Data(
                21,
                "b5",
                4,
                48,
                arrayListOf(21, 22, 23, 24, 25),
                arrayListOf(1, 6, 11, 16, 21),
                arrayListOf(5, 9, 17, 21)
            )
        )
        list.add(
            Data(
                22,
                "i5",
                2,
                18,
                arrayListOf(21, 22, 23, 24, 25),
                arrayListOf(2, 7, 12, 17, 22),
                arrayListOf()
            )
        )
        list.add(
            Data(
                23,
                "n5",
                2,
                16,
                arrayListOf(21, 22, 23, 24, 25),
                arrayListOf(3, 8, 18, 23),
                arrayListOf()
            )
        )
        list.add(
            Data(
                24,
                "g5",
                2,
                18,
                arrayListOf(21, 22, 23, 24, 25),
                arrayListOf(4, 9, 14, 19, 24),
                arrayListOf()
            )
        )
        list.add(
            Data(
                25,
                "o5",
                4,
                48,
                arrayListOf(21, 22, 23, 24, 25),
                arrayListOf(5, 10, 15, 20, 25),
                arrayListOf(1, 7, 19, 25)
            )
        )
        return list
    }

    fun clickDs() {
        val list = AppData.dataList
        val newList = getData()
        for (i in 1..25) {
            if (arrayListOf(1, 7, 19, 25, 5, 9, 17, 21).contains(i)) {
                val item = newList[i - 1]
                item.isClicked = true
                list[i - 1] = item
            }
        }
        AppData.dataList = list
    }

    fun createLines(): ArrayList<Pair<String, ArrayList<Int>>> {
        val list = arrayListOf<Pair<String, ArrayList<Int>>>()
        list.add(Pair("1", arrayListOf(1, 2, 3, 4, 5)))
        list.add(Pair("2", arrayListOf(6, 7, 8, 9, 10)))
        list.add(Pair("3", arrayListOf(11, 12, 14, 15)))
        list.add(Pair("4", arrayListOf(16, 17, 18, 19, 20)))
        list.add(Pair("5", arrayListOf(21, 22, 23, 24, 25)))

        list.add(Pair("B", arrayListOf(1, 6, 11, 16, 21)))
        list.add(Pair("I", arrayListOf(2, 7, 12, 17, 22)))
        list.add(Pair("N", arrayListOf(3, 8, 18, 23)))
        list.add(Pair("G", arrayListOf(4, 9, 14, 19, 24)))
        list.add(Pair("O", arrayListOf(5, 10, 15, 20, 25)))

        list.add(Pair("B1", arrayListOf(1, 7, 19, 25)))
        list.add(Pair("O1", arrayListOf(5, 9, 17, 21)))

        list.add(Pair("X", CORNERS))
        return list
    }

}