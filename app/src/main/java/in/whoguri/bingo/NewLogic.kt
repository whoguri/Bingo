package `in`.whoguri.bingo

import android.util.Log

object NewLogic {
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
        Log.e(">>genAvrage "+data.number,  data.genAvrage.toString())

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
                        subTotal += ((d.avrage +d.hidden) /2).roundOffDecimal3()
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