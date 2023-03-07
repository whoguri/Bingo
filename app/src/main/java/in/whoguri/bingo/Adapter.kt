package `in`.whoguri.bingo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(val context_: Context, val list: MutableList<Data>, val call: (Int) -> Unit) :
    ArrayAdapter<Data>(context_, 0, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        convertView?.tag = position
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(R.layout.item, parent, false)
        }
        val courseModel = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)
        if (position == 12) {
            courseTV.text = "X"
            listitemView.setOnClickListener {}
            courseTV.setBackgroundColor(context_.getColor(R.color.black))
        } else if (courseModel!!.isClicked) {
            courseTV.text = ""
            listitemView.setOnClickListener {}
            courseTV.setBackgroundColor(context_.getColor(R.color.purple))
        } else {
            courseTV.setBackgroundColor(context_.getColor(R.color.white))
            if (courseModel.finalValue != -1) {
                courseTV.text = courseModel.finalValue.toString()
            }
            listitemView.setOnClickListener {
                call(position)
                notifyDataSetChanged()
            }
        }

        return listitemView
    }
}