package `in`.whoguri.bingo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import `in`.whoguri.bingo.adapters.GridAdapter
import `in`.whoguri.bingo.adapters.HLResultAdapter
import `in`.whoguri.bingo.adapters.ResultAdapter
import `in`.whoguri.bingo.helpers.Data_13
import `in`.whoguri.bingo.helpers.Data_13_2
import `in`.whoguri.bingo.helpers.Logic
import `in`.whoguri.bingo.helpers.NewLogic
import `in`.whoguri.bingo.helpers.NewLogic13_2
import `in`.whoguri.bingo.helpers.NewLogic2

class MainActivity11 : AppCompatActivity() {
    val adapter by lazy {
        GridAdapter(this, 132, AppData.dataList) { it, b ->
            val data = AppData.dataList[it]
            data.isClicked = !b
            AppData.dataList[it] = data
            if (AppData.dataList.filter { it.isClicked }.isEmpty()) {
                AppData.reset()
                recal()
                return@GridAdapter
            } else {
                recal()
            }
        }
    }
    val resultAdapter by lazy {
        ResultAdapter(this, AppData.resultList)
    }
    val averageAdapter by lazy {
        ResultAdapter(this, AppData.averageList)
    }
    val tAdapter by lazy {
        ResultAdapter(this, arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), true)
    }

    fun changeTab(no: Int) {
        adapter.setType(no)
        val title = findViewById<TextView>(R.id.title_)
        when (no) {
            11 -> title.text = "Bingo! 11 P"
            10 -> title.text = "Bingo! G"
            1 -> title.text = "Bingo! 1"
            13 -> title.text = "Bingo! GT"
            132 -> title.text = "Bingo! 13"
            else -> title.text = "Bingo!"
        }
        recal()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.title_).text = ""
        AppData.dataList = Logic.getData()

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en-us")
        AppCompatDelegate.setApplicationLocales(appLocale)
//actionBar.setDisplayHomeAsUpEnabled()
        averageAdapter.setNotifyOnChange(true)
        findViewById<GridView>(R.id.grid).adapter = adapter
        findViewById<GridView>(R.id.sortGrid).adapter = resultAdapter
        findViewById<GridView>(R.id.avrageGrid).adapter = averageAdapter
        findViewById<GridView>(R.id.hlGrid).adapter = result13Adapter
        findViewById<GridView>(R.id.t_Grid).adapter = tAdapter

        planA()
        changeTab(132)
        findViewById<LinearLayout>(R.id.resetD).setOnClickListener {
            if (Logic.getSel(arrayListOf(1, 7, 19, 25, 5, 9, 17, 21), AppData.dataList).filter { it.isClicked }.size == 8)
                Logic.clickDs(false)
            else
                Logic.clickDs(true)
            recal()
        }
        findViewById<LinearLayout>(R.id.restart).setOnClickListener {
            AppData.reset()
            Logic.clickDs()
            recal()
        }
        findViewById<Button>(R.id.btn1).setOnClickListener {
            view = "a"
            planA()
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            view = "b"
            planB()
        }
        findViewById<Button>(R.id.btn3).setOnClickListener {
            view = "t"
            planT()
        }
        findViewById<TextView>(R.id.cal_1).setOnClickListener {
            changeTab(11)
        }
        findViewById<TextView>(R.id.cal_2).setOnClickListener {
            changeTab(10) // hiden
        }
        findViewById<TextView>(R.id.cal_3).setOnClickListener {
            changeTab(1)
        }
        findViewById<TextView>(R.id.cal_4).setOnClickListener {
            changeTab(132)
        }
        findViewById<TextView>(R.id.cal_5).setOnClickListener {
            changeTab(13)
        }
    }

    private fun planA() {
        findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result3).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result_t).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result13_2).visibility = View.GONE

        result13Adapter.clear()
        if (adapter.calType == 10) {
            findViewById<LinearLayout>(R.id.result2).visibility = View.VISIBLE

            var num1 = findViewById<TextView>(R.id.num1)
            var num2 = findViewById<TextView>(R.id.num2)
            if (result.size == 0) {
                num1.text = ""
                num2.text = ""
                num1.setBackgroundColor(getColor(R.color.white))
                num2.setBackgroundColor(getColor(R.color.white))
            } else {
                num1.text = result.get(0).first
                num2.text = result.get(1).first
                if (result.get(0).first == "B-O") {
                    num1.setBackgroundColor(getColor(R.color.yellow))
                    num2.setBackgroundColor(getColor(R.color.green))
                } else {
                    num1.setBackgroundColor(getColor(R.color.green))
                    num2.setBackgroundColor(getColor(R.color.yellow))
                }
                adapter.setHigh(result.get(0).first)
            }
        } else if (adapter.calType == 13) {
            findViewById<LinearLayout>(R.id.result3).visibility = View.VISIBLE

            result13Adapter.setType(1)
            result13Adapter.notify_(result13)
        }  else if (adapter.calType == 132) {
            findViewById<LinearLayout>(R.id.result13_2).visibility = View.VISIBLE
            findViewById<TextView>(R.id._13_x_1).text =  result13_2.find { it.name=="X" }?.result?.get(0)?.first ?:""
            findViewById<TextView>(R.id._13_bo_1).text =  result13_2.find { it.name=="BO" }?.result?.get(0)?.first ?:""
            findViewById<TextView>(R.id._13_ig_1).text =  result13_2.find { it.name=="IG" }?.result?.get(0)?.first ?:""
            findViewById<TextView>(R.id._13_n3_1).text =  result13_2.find { it.name=="N3" }?.result?.get(0)?.first ?:""

            findViewById<TextView>(R.id._13_x_2).text =  result13_2.find { it.name=="X" }?.result?.get(1)?.first ?:""
            findViewById<TextView>(R.id._13_bo_2).text =  result13_2.find { it.name=="BO" }?.result?.get(1)?.first ?:""
            findViewById<TextView>(R.id._13_ig_2).text =  result13_2.find { it.name=="IG" }?.result?.get(1)?.first ?:""
            findViewById<TextView>(R.id._13_n3_2).text =  result13_2.find { it.name=="N3" }?.result?.get(1)?.first ?:""
        }else {
            findViewById<LinearLayout>(R.id.result1).visibility = View.VISIBLE

            findViewById<GridView>(R.id.sortGrid).visibility = View.VISIBLE
            findViewById<GridView>(R.id.avrageGrid).visibility = View.GONE
        }
    }

    private fun planB() {
        findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result3).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result_t).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result13_2).visibility = View.GONE

        if (adapter.calType == 10) {
            findViewById<LinearLayout>(R.id.result2).visibility = View.VISIBLE

            var num1 = findViewById<TextView>(R.id.num1)
            var num2 = findViewById<TextView>(R.id.num2)
            if (result.size == 0) {
                num1.text = ""
                num2.text = ""
                num1.setBackgroundColor(getColor(R.color.white))
                num2.setBackgroundColor(getColor(R.color.white))
            } else {
                num1.text = result.get(0).second.toString()
                num2.text = result.get(1).second.toString()
                if (result.get(0).first == "B-O") {
                    num1.setBackgroundColor(getColor(R.color.yellow))
                    num2.setBackgroundColor(getColor(R.color.green))
                } else {
                    num1.setBackgroundColor(getColor(R.color.green))
                    num2.setBackgroundColor(getColor(R.color.yellow))
                }
                adapter.setHigh(result.get(0).first)
            }
        } else if (adapter.calType == 13) {
            findViewById<LinearLayout>(R.id.result3).visibility = View.VISIBLE

            result13Adapter.setType(0)
            result13Adapter.notify_(result13)
        } else if (adapter.calType == 132) {
            findViewById<LinearLayout>(R.id.result13_2).visibility = View.VISIBLE
            findViewById<TextView>(R.id._13_x_1).text =  result13_2.find { it.name=="X" }?.result?.get(0)?.second?.toString() ?:""
            findViewById<TextView>(R.id._13_bo_1).text =  result13_2.find { it.name=="BO" }?.result?.get(0)?.second?.toString() ?:""
            findViewById<TextView>(R.id._13_ig_1).text =  result13_2.find { it.name=="IG" }?.result?.get(0)?.second?.toString() ?:""
            findViewById<TextView>(R.id._13_n3_1).text =  result13_2.find { it.name=="N3" }?.result?.get(0)?.second?.toString() ?:""

            findViewById<TextView>(R.id._13_x_2).text =  result13_2.find { it.name=="X" }?.result?.get(1)?.second?.toString() ?:""
            findViewById<TextView>(R.id._13_bo_2).text =  result13_2.find { it.name=="BO" }?.result?.get(1)?.second?.toString() ?:""
            findViewById<TextView>(R.id._13_ig_2).text =  result13_2.find { it.name=="IG" }?.result?.get(1)?.second?.toString() ?:""
            findViewById<TextView>(R.id._13_n3_2).text =  result13_2.find { it.name=="N3" }?.result?.get(1)?.second?.toString() ?:""
        }else {
            findViewById<LinearLayout>(R.id.result1).visibility = View.VISIBLE

            findViewById<GridView>(R.id.sortGrid).visibility = View.GONE
            findViewById<GridView>(R.id.avrageGrid).visibility = View.VISIBLE
        }
    }

    private fun planT() {
        findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result3).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result13_2).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result_t).visibility = View.VISIBLE
    }

    var result = arrayListOf<Pair<String, Double>>()
    var view = "a"
    var result13 = arrayListOf<Data_13>()
    var result13_2 = arrayListOf<Data_13_2>()
    val result13Adapter by lazy { HLResultAdapter(this, arrayListOf(), 1) }

    private fun recal() {
        if (AppData.dataList.size != 25) {
            AppData.dataList = Logic.getData()
            adapter.notify_()

        } else if (adapter.calType == 10) {
            val result_ = NewLogic.calResult10_GroupNew(AppData.dataList)
            AppData.dataList = result_.second
            AppData.resultList.clear()
            AppData.averageList.clear()
            averageAdapter.clear()
            result.clear()
            //descending for old
            result_.first.sortedBy { it.second }.forEach {
                result.add(it)
            }
            adapter.notify_()

        } else if (adapter.calType == 13) {
            val result_ = NewLogic2.calResult13(AppData.dataList)
            AppData.resultList.clear()
            AppData.averageList.clear()
            averageAdapter.clear()
            result.clear()
            result13.clear()

            //descending for old
            result_.forEach {
                result13.add(it)
            }
            adapter.setHigh(result13.get(0).name)
            adapter.notify_()
        } else if (adapter.calType == 132) {
            result13_2 = NewLogic13_2.calResult(AppData.dataList)
            AppData.resultList.clear()
            AppData.averageList.clear()
            averageAdapter.clear()
            result.clear()
            result13.clear()

            adapter.notify_()
        } else {
            if (adapter.calType == 11)
                AppData.dataList = NewLogic.calResult11P(AppData.dataList)
            else if (adapter.calType == 1)
                AppData.dataList = Logic.calResult5(AppData.dataList)
            else if (adapter.calType == 12)
                AppData.dataList = NewLogic.calResult12(AppData.dataList)

            val temp = ArrayList<Data>()
            AppData.dataList.forEach {
                if (it.finalValue2 > 0 && !it.isClicked) {
                    temp.add(it)
                }
            }
            AppData.resultList.clear()
            resultAdapter.clear()
            temp.sortedByDescending { it.finalValue2 }.forEach {
                if (AppData.resultList.size < 10 && it.number != 13) {
                    AppData.resultList.add(it.code)
                }
            }
            resultAdapter.addAll(AppData.resultList)
            
            AppData.averageList.clear()
            averageAdapter.clear()
            AppData.averageList = Logic.calAverage2(AppData.dataList)
            averageAdapter.addAll(AppData.averageList)
            adapter.notify_()
        }
        if (view == "a")
            planA()
        else if (view == "t")
            planT()
        else
            planB()
        xo()
    }

    fun xo() {
        var x_size = Logic.getSel(arrayListOf(1, 7, 19, 25, 5, 9, 17, 21), AppData.dataList).filter { it.isClicked }.size
        var o_size = AppData.dataList.filter { !arrayListOf(1, 7, 19, 25, 5, 9, 17, 21).contains(it.number) }.filter { it.isClicked }.size
        val xo_btn = findViewById<TextView>(R.id.btn_XO)
        if (x_size == 0 && o_size == 0)
            xo_btn.text = "  "
        else if (x_size * 2 == o_size)
            xo_btn.text = "  "
        else if (x_size * 2 > o_size)
            xo_btn.text = "O"
        else
            xo_btn.text = "X"
    }

    override fun onResume() {
        super.onResume()
        recal()
    }
}