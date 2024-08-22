package `in`.whoguri.bingo

import `in`.whoguri.bingo.helpers.Logic

object AppData {
    var dataList = Logic.getData()
    var resultList = ArrayList<String>()
    var averageList = ArrayList<String>()

    fun reset() {
        dataList = Logic.getData()
        resultList = ArrayList<String>()
        averageList = ArrayList<String>()

    }
}