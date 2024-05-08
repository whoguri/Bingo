package `in`.whoguri.bingo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView

class GridAdapter(
    val context_: Context,
    private val type: Int,
    private val list: MutableList<Data>,
    val call: (Int, Boolean) -> Unit
) :
    ArrayAdapter<Data>(context_, 0, list) {
    var calType: Int = 0
    var higher: String = ""

    init {
        calType = type
    }

    fun notify_() {
        notifyDataSetChanged()
    }

    fun setType(type_: Int) {
        calType = type_
        higher = ""
        notify_()
    }

    fun setHigh(shine_: String) {
        higher = shine_
        notify_()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        convertView?.tag = position
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(R.layout.item_, parent, false)
        }
        val data = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)
        val courseLL = listitemView.findViewById<LinearLayout>(R.id.numLL)

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
            courseLL.setBackgroundColor(context_.getColor(R.color.black))
        } else if (data!!.isClicked) {
            courseTV.text = ""
            courseLL.setBackgroundColor(context_.getColor(R.color.purple))
        } else {
            courseLL.setBackgroundColor(context_.getColor(R.color.white))

            if (calType == 10) {
                if (data.hidden != -1.0) {
                    courseTV.text = data.hidden.roundOffDecimal3().toString()
                }
//            } else if (calType == 9) {
//                if (data.finalValue9 != -1.0) {
//                    courseTV.text = data.finalValue9.roundOffDecimal3().toString()
//                }
            } else if (calType == 8) {
                if (data.finalValue8 != -1.0) {
                    courseTV.text = data.finalValue8.roundOffDecimal3().toString()
                }
            } else if (calType == 7) {
                if (data.finalValue7 != -1.0) {
                    courseTV.text = data.finalValue7.roundOffDecimal3().toString()
                }
            } else if (calType == 6) {
                if (data.finalValue6 != -1.0) {
                    courseTV.text = data.finalValue6.roundOffDecimal3().toString()
                }
//            } else if (calType == 5) {
//                if (data.finalValue5 != -1.0) {
//                    courseTV.text = data.finalValue5.roundOffDecimal3().toString()
//                }
            } else if (calType == 4) {
                if (data.finalValue4 != -1.0) {
                    courseTV.text = data.finalValue4.roundOffDecimal3().toString()
                }
            } else if (calType == 3) {
                if (data.finalValue3 != -1.0) {
                    courseTV.text = data.finalValue3.roundOffDecimal3().toString()
                }
            } else if (calType == 2 || calType == 11 || calType ==
                1 || calType == 9
            ) {
                if (data.finalValue2 != -1.0) {
                    courseTV.text = data.finalValue2.roundOffDecimal3().toString()
                }
            } else {
                if (data.finalValue != -1) {
                    courseTV.text = data.finalValue.toString()
                }
            }
        }

        val courseLL2 = listitemView.findViewById<LinearLayout>(R.id.numLL2)
        if (calType == 10) {
            if (higher == "B-O") {
                if (arrayOf(6, 8, 10, 11, 15, 16, 18, 20).contains(position + 1))
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.border))
                else
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))
            } else if (higher == "1-5") {
                if (arrayOf(2, 3, 4, 12, 14, 22, 23, 24).contains(position + 1))
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.border))
                else
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))

            }
        } else {
            courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))
        }

        return listitemView
    }
}