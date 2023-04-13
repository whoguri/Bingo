package `in`.whoguri.bingo

object AppData {
    var list = Logic.getData()
    var list2 = ArrayList<String>()
    var list3 = ArrayList<String>()

    fun reset() {
        list = Logic.getData()
        list2 = ArrayList<String>()
        list3 = ArrayList<String>()

    }
}