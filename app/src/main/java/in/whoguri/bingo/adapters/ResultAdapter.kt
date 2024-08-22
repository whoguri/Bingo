package `in`.whoguri.bingo.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import `in`.whoguri.bingo.R
import java.util.Locale

class ResultAdapter(val context_: Context, val list: MutableList<String>, val isTras: Boolean = false) :
    ArrayAdapter<String>(context_, 0, list) {
    var clicked = ""
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(
                if (isTras) R.layout.item_trans else R.layout.item_, parent, false
            )
        }
        val courseModel = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)
        courseTV.text = courseModel?.uppercase(Locale.getDefault())
        if (isTras) {
            listitemView.setOnClickListener {
                if (courseModel == clicked)
                    clicked = ""
                else
                    clicked = courseModel!!
                notifyDataSetChanged()
            }
            courseTV.setBackgroundColor(context_.getColor(if (courseModel == clicked) R.color.white else R.color.purple))
        }
        return listitemView
    }
}