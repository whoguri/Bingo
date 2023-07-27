package `in`.whoguri.bingo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(val context_: Context, val list: MutableList<Data>, val call :(Int)->Unit) : ArrayAdapter<Data>(context_, 0, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(R.layout.item_, parent, false)
        }
        val courseModel = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)
        if (position == 12) {
            courseTV.text = "X"
        } else {
            if (courseModel!!.finalValue != -1) {
                courseTV.text = courseModel.finalValue.toString()
            }
        }
        listitemView.setOnClickListener {
            if (courseModel!!.finalValue == -1) {
                call(position)
                notifyDataSetChanged()
            }
//            Toast.makeText(context, courseModel?.number.toString(), Toast.LENGTH_SHORT).show()
        }
        courseTV.text = (position+1).toString()

        return listitemView
    }
}