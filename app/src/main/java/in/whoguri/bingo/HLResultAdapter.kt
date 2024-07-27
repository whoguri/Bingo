package `in`.whoguri.bingo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HLResultAdapter(val context_: Context, val list: ArrayList<Data_13>, val type: Int) : ArrayAdapter<Data_13>(context_, 0, list) {
    var calType: Int = 0
    var higher: ArrayList<Int> = arrayListOf()

    init {
        calType = type
    }

    fun notify_(list_: ArrayList<Data_13>) {
        list.clear()
        list.addAll(list_)
        notifyDataSetChanged()
    }

    fun setType(type_: Int) {
        calType = type_
//        higher = ""
        notifyDataSetChanged()
    }

//    fun setHigh(shine_: String) {
//        higher = shine_
//        notify_(list)
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(R.layout.item_, parent, false)
        }
        val courseModel = getItem(position)!!
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)
        if (calType == 1)
            courseTV.text = courseModel.name
        else
            courseTV.text = courseModel.result.toString()
        return listitemView
    }
}