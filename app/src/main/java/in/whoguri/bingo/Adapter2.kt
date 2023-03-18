package `in`.whoguri.bingo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter2(val context_: Context, val list: MutableList<String>) :
    ArrayAdapter<String>(context_, 0, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(R.layout.item, parent, false)
        }
        val courseModel = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)
        courseTV.text = courseModel?.toUpperCase()
        return listitemView
    }
}