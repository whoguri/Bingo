package `in`.whoguri.bingo

object Logic {

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

    fun calResult(list: ArrayList<Data>): ArrayList<Data> {
        val mList = list
        for (i in 1..25) {
            mList[i - 1] = calculate(list, list[i - 1], i)
        }
        return mList
    }

    fun calculate(list: ArrayList<Data>, data: Data, clicked: Int): Data {
        var total = 0

        data.h.forEach {
            var hn = 1
            val d = list.filter { item -> item.number == it }.first()
            val all = getAll(d, list)
            val size = all.filter { item -> item.isClicked }.size
            hn += size
//            if (size > 0) {
//                hn++
//            }
            if (d.isClicked || d.number == clicked) {
                total += d.selfValue
            } else {
                total += (d.hideValue * hn)
            }
//            if (clicked == 2) {
//                Log.e(">>>", total.toString())
//            }
        }

        data.v.forEach {
            var vn = 1
            val hd = data.h.filter { item -> item == it }.firstOrNull()
            if (hd == null) {
                val d = list.filter { item -> item.number == it }.first()
                val all = getAll(d, list)
                val size = all.filter { item -> item.isClicked }.size
                vn += size

//                if (size > 0) {
//                    vn++
//                }
                if (d.isClicked || d.number == clicked) {
                    total += d.selfValue
                } else {
                    total = total + (d.hideValue * vn)
                }
//                if (clicked == 2) {
//                    Log.e(">>>", total.toString())
//                }
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
//                if (size > 0) {
//                    dn++
//                }
                if (d.isClicked || d.number == clicked) {
                    total += d.selfValue
                } else {
                    total = total + (d.hideValue * dn)
                }
//                if (clicked == 2) {
//                    Log.e(">>>", total.toString())
//                }
            }
        }
        data.finalValue = total
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

        list.add(Pair("X", arrayListOf(1, 5, 21, 25)))
        return list
    }

}