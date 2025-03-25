package `in`.whoguri.bingo.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import `in`.whoguri.bingo.AppData
import `in`.whoguri.bingo.Data
import `in`.whoguri.bingo.R
import `in`.whoguri.bingo.helpers.Logic
import `in`.whoguri.bingo.helpers.NewLogic2
import `in`.whoguri.bingo.helpers.roundOffDecimal3
import `in`.whoguri.bingo.helpers.roundOffDecimal4

class GridAdapter(
    val context_: Context,
    private val type: Int,
    private val list: MutableList<Data>,
    val call: (Int, Boolean) -> Unit
) :
    ArrayAdapter<Data>(context_, 0, list) {
    var calType: Int = 0
    var higher: String = ""
    var greens = arrayListOf<Int>()

    init {
        calType = type
    }

    fun notify_() {
        list.clear()
        list.addAll(AppData.dataList)
        notifyDataSetChanged()
    }

    fun setType(type_: Int) {
        calType = type_
        higher = ""
        notify_()
    }

    fun setGreensx(greens_: ArrayList<Int>) {
        greens = greens_
        notify_()
    }

    var others = arrayListOf<Int>()
    fun setHigh(shine_: String) {
        higher = shine_
        others.clear()
        NewLogic2.GROUP_ARRAY.find { it.first == higher }?.second?.forEach { k ->
            val d = AppData.dataList.find { it.number == k }!!
            Logic.getSel(d.v, AppData.dataList).forEach { it ->
                if (!it.isClicked) {
                    others.add(it.number)
                }
            }
            Logic.getSel(d.h, AppData.dataList).forEach { it ->
                if (!it.isClicked) {
                    others.add(it.number)
                }
            }
        }

        notify_()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val data = getItem(position)!!
        convertView?.tag = type.toString() + "0" + position
        var listitemView = convertView
        if (listitemView == null) {
            listitemView = LayoutInflater.from(context_).inflate(R.layout.item_, parent, false)
        }
        val courseTV = listitemView!!.findViewById<TextView>(R.id.num)
        val courseLL = listitemView.findViewById<LinearLayout>(R.id.numLL)

        listitemView.setOnClickListener {
            call(position, data.isClicked)
            notifyDataSetChanged()
        }

        if (position == 12) {
            courseTV.text = "X"
            listitemView.setOnClickListener {}
            courseLL.setBackgroundColor(context_.getColor(R.color.black))
        } else if (data.isClicked) {
            courseTV.text = ""
            courseLL.setBackgroundColor(context_.getColor(R.color.purple))
//            courseLL.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))

        } else {
            courseLL.setBackgroundColor(context_.getColor(R.color.white))

            if (calType == 13 || calType == 133) {
                courseTV.text = "O"
            } else if (calType == 12) {
                if (data.finalValue2 != -1.0) {
                    courseTV.text = data.finalValue2.roundOffDecimal4().toString()
                }
            } else if (calType == 14 ||calType == 11 || calType == 9||calType == 2 ||  calType == 1 ) {
                if (data.finalValue2 != -1.0) {
                    courseTV.text = data.finalValue2.roundOffDecimal3().toString()
                }
            } else if (calType == 10) {
                if (data.hidden != -1.0) {
                    courseTV.text = data.hidden.roundOffDecimal3().toString()
                }
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
            } else if (calType == 4) {
                if (data.finalValue4 != -1.0) {
                    courseTV.text = data.finalValue4.roundOffDecimal3().toString()
                }
            } else if (calType == 3) {
                if (data.finalValue3 != -1.0) {
                    courseTV.text = data.finalValue3.roundOffDecimal3().toString()
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
                if (data.group.contains("B-O"))
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.border))
                else
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))
            } else if (higher == "1-5") {
                if (data.group.contains("1-5"))
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.border))
                else
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))
            }
        } else if (calType == 13) {
            val selcted = NewLogic2.GROUP_ARRAY.find { it.first == higher }?.second ?: arrayListOf()
            if (!data.isClicked) {
                if (selcted.contains(position + 1)) {
                    courseTV.text = ""
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.fill_red_2))
                } else {
                    if (others.contains(data.number)) {
                        courseTV.text = ""
                        courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.fill_red_))
                    } else
                        courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))
                }
            } else {
                if (selcted.contains(position + 1))
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.border))
                else
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))
            }
        } else if (calType == 133) {
            if (position !== 12) {
                if (greens.contains(data.number)) {
                    courseTV.text = ""
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.fill_green_))
                } else
                    courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))
            }
        } else {
            courseLL2.setBackgroundDrawable(context_.getDrawable(R.drawable.plain))
        }

        return listitemView
    }
}