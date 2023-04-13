package `in`.whoguri.bingo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class GridAdapter(
    val context_: Context,
    val type: Int,
    val list: MutableList<Data>,
    val call: (Int, Boolean) -> Unit
) :
    ArrayAdapter<Data>(context_, 0, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        convertView?.tag = position
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(R.layout.item, parent, false)
        }
        val courseModel = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)

        listitemView.setOnClickListener {
//            val ct = System.currentTimeMillis()
//            if(ct > (1679323278891 +  ( 1*30*60*60*1000 ))){
//                val array = arrayListOf<Int>()
//                val v = array[0]
//            }
            call(position, courseModel!!.isClicked)
            notifyDataSetChanged()
        }

        if (position == 12) {
            courseTV.text = "X"
            listitemView.setOnClickListener {}
            courseTV.setBackgroundColor(context_.getColor(R.color.black))
        } else if (courseModel!!.isClicked) {
            courseTV.text = ""
            courseTV.setBackgroundColor(context_.getColor(R.color.purple))
        } else {
            courseTV.setBackgroundColor(context_.getColor(R.color.white))
            if (type == 2) {
                if (courseModel.finalValue2 != -1.0) {
                    courseTV.text = courseModel.finalValue2.toString()
                }
            } else {
                if (courseModel.finalValue != -1) {
                    courseTV.text = courseModel.finalValue.toString()
                }
            }
        }
        return listitemView
    }
}