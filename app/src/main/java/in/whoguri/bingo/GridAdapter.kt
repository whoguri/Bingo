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
    fun notify_() {
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        convertView?.tag = position
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(R.layout.item_, parent, false)
        }
        val data = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)

        listitemView.setOnClickListener {
//            val ct = System.currentTimeMillis()
//            if(ct > (1679323278891 +  ( 1*30*60*60*1000 ))){
//                val array = arrayListOf<Int>()
//                val v = array[0]
//            }
            call(position, data!!.isClicked)
            notifyDataSetChanged()
        }

        if (position == 12) {
            courseTV.text = "X"
            listitemView.setOnClickListener {}
            courseTV.setBackgroundColor(context_.getColor(R.color.black))
        } else if (data!!.isClicked) {
            courseTV.text = ""
            courseTV.setBackgroundColor(context_.getColor(R.color.purple))
        } else {
            courseTV.setBackgroundColor(context_.getColor(R.color.white))

            if (type == 10) {
                if (data.hidden != -1.0) {
                    courseTV.text = data.hidden.toString()
                }
            } else if (type == 9) {
                if (data.finalValue9 != -1.0) {
                    courseTV.text = data.finalValue9.toString()
                }
            } else if (type == 8) {
                if (data.finalValue8 != -1.0) {
                    courseTV.text = data.finalValue8.toString()
                }
            } else if (type == 7) {
                if (data.finalValue7 != -1.0) {
                    courseTV.text = data.finalValue7.toString()
                }
            } else if (type == 6) {
                if (data.finalValue6 != -1.0) {
                    courseTV.text = data.finalValue6.toString()
                }
            } else if (type == 5) {
                if (data.finalValue5 != -1.0) {
                    courseTV.text = data.finalValue5.toString()
                }
            } else if (type == 4) {
                if (data.finalValue4 != -1.0) {
                    courseTV.text = data.finalValue4.toString()
                }
            } else if (type == 3) {
                if (data.finalValue3 != -1.0) {
                    courseTV.text = data.finalValue3.toString()
                }
            } else if (type == 2 || type == 11) {
                if (data.finalValue2 != -1.0) {
                    courseTV.text = data.finalValue2.toString()
                }
            } else {
                if (data.finalValue != -1) {
                    courseTV.text = data.finalValue.toString()
                }
            }
        }
        return listitemView
    }
}