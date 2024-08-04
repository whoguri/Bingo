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

class MainActivity11 : AppCompatActivity() {
    val adapter by lazy {
        GridAdapter(this, 13, AppData.dataList) { it, b ->
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
//        findViewById<TextView>(R.id.cal_1)
//        findViewById<TextView>(R.id.cal_2)
//        findViewById<TextView>(R.id.cal_3)
//        findViewById<TextView>(R.id.cal_4)
        val title = findViewById<TextView>(R.id.title_)
        when (no) {
            11 -> title.text = "Bingo! 11 P"
            10 -> title.text = "Bingo! G"
            1 -> title.text = "Bingo! 1"
            12 -> title.text = "Bingo! 12"
            13 -> title.text = "Bingo! GT"
            else -> title.text = "Bingo!"
        }
        recal()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.title_).text = "Bingo! 11"
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
        changeTab(13)
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
            planA()
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            planB()
        }
        findViewById<Button>(R.id.btn3).setOnClickListener {
            planT()
        }
        findViewById<TextView>(R.id.cal_1).setOnClickListener {
            changeTab(11)
        }
        findViewById<TextView>(R.id.cal_2).setOnClickListener {
            changeTab(10)
        }
        findViewById<TextView>(R.id.cal_3).setOnClickListener {
            changeTab(1)
        }
        findViewById<TextView>(R.id.cal_4).setOnClickListener {
            changeTab(12)
        }
        findViewById<TextView>(R.id.cal_5).setOnClickListener {
            changeTab(13)
        }
    }

    private fun planA() {
        findViewById<LinearLayout>(R.id.result_t).visibility = View.GONE
        view = "a"
        result13Adapter.clear()
        if (adapter.calType == 10) {
            findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
            findViewById<LinearLayout>(R.id.result2).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.result3).visibility = View.GONE

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
            findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
            findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
            findViewById<LinearLayout>(R.id.result3).visibility = View.VISIBLE

            result13Adapter.setType(1)
            result13Adapter.notify_(result13)
        } else {
            findViewById<LinearLayout>(R.id.result1).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
            findViewById<LinearLayout>(R.id.result3).visibility = View.GONE

            findViewById<GridView>(R.id.sortGrid).visibility = View.VISIBLE
            findViewById<GridView>(R.id.avrageGrid).visibility = View.GONE
        }
    }

    private fun planB() {
        findViewById<LinearLayout>(R.id.result_t).visibility = View.GONE
        view = "b"

        if (adapter.calType == 10) {
            findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
            findViewById<LinearLayout>(R.id.result2).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.result3).visibility = View.GONE

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
            findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
            findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
            findViewById<LinearLayout>(R.id.result3).visibility = View.VISIBLE

            result13Adapter.setType(0)
            result13Adapter.notify_(result13)
        } else {
            findViewById<LinearLayout>(R.id.result1).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
            findViewById<LinearLayout>(R.id.result3).visibility = View.GONE

            findViewById<GridView>(R.id.sortGrid).visibility = View.GONE
            findViewById<GridView>(R.id.avrageGrid).visibility = View.VISIBLE
        }
    }

    private fun planT() {
        findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result3).visibility = View.GONE
        findViewById<LinearLayout>(R.id.result_t).visibility = View.VISIBLE
        view = "t"
    }

    var result = arrayListOf<Pair<String, Double>>()
    var view = "a"
    var result13 = arrayListOf<Data_13>()
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
            if (view == "a")
                planA()
            else if (view == "t")
                planT()
            else
                planB()
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
            if (view == "a")
                planA()
            else if (view == "t")
                planT()
            else
                planB()

            adapter.setHigh(result13.get(0).name)

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
            AppData.averageList.clear()
            averageAdapter.clear()
            temp.sortedByDescending { it.finalValue2 }.forEach {
                if (AppData.resultList.size < 10 && it.number != 13) {
                    AppData.resultList.add(it.code)
                }
            }
            AppData.averageList = Logic.calAverage2(AppData.dataList)
            resultAdapter.notifyDataSetChanged()
            averageAdapter.addAll(AppData.averageList)
            adapter.notify_()
            if (view == "a")
                planA()
            else if (view == "t")
                planT()
            else
                planB()
        }
        xo()
    }

    fun xo() {
        //    We have 8 diagonal tile and other 16 tiles , when they are all
        //    white so it will be empty (kind of tie- tie here is when the ratio
        //    is 1 to 2)
        //    Also in any other situation like this I mean ratio wise, if there
        //    are 6 diagonal tile white and 12 of the others so it will still be
        //    empty.
        //    When the ratio will be more towards the diagonal tile for
        //        example 6 diagonal tile are white and 11 of the others white so
        //    X, and if the ratio is towards the others , for example 6 diagonal
        //    tiles and 13 of the other so “O”

        var x_size = Logic.getSel(arrayListOf(1, 7, 19, 25, 5, 9, 17, 21), AppData.dataList).filter { it.isClicked }.size
        var o_size = AppData.dataList.filter { !arrayListOf(1, 7, 19, 25, 5, 9, 17, 21).contains(it.number) }.filter { it.isClicked }.size
        val xo_btn = findViewById<TextView>(R.id.btn_XO)
        if (x_size == 0 && o_size == 0)
            xo_btn.text = "  "
        else if (x_size * 2 == o_size)
            xo_btn.text = "  "
        else if (x_size * 2 > o_size)
            xo_btn.text = "X"
        else
            xo_btn.text = "O"

    }

    override fun onResume() {
        super.onResume()
        recal()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)

//        val item2: MenuItem = menu.findItem(R.id.button_item2)
//        item2.setOnMenuItemClickListener { it ->
//            startActivity(Intent(this, MainActivity6::class.java))
//            overridePendingTransition(0, 0)
//            return@setOnMenuItemClickListener true
//        }
//        val item3: MenuItem = menu.findItem(R.id.button_item3)
//        item3.setOnMenuItemClickListener { it ->
//            startActivity(Intent(this, MainActivity::class.java))
//            overridePendingTransition(0, 0)
//            return@setOnMenuItemClickListener true
//        }
//
//        val item7: MenuItem = menu.findItem(R.id.button_item7)
//        item7.setOnMenuItemClickListener { it ->
//            startActivity(Intent(this, MainActivity7::class.java))
//            overridePendingTransition(0, 0)
//            return@setOnMenuItemClickListener true
//        }
//        val item8: MenuItem = menu.findItem(R.id.button_item8)
//        item8.setOnMenuItemClickListener { it ->
//            startActivity(Intent(this, MainActivity8::class.java))
//            overridePendingTransition(0, 0)
//            return@setOnMenuItemClickListener true
//        }
//        val item1: MenuItem = menu.findItem(R.id.button_item1)
//        item1.setOnMenuItemClickListener { it ->
//            startActivity(Intent(this, MainActivity5::class.java))
//            overridePendingTransition(0, 0)
//            return@setOnMenuItemClickListener true
//        }
//        val item9: MenuItem = menu.findItem(R.id.button_item9)
//        item9.setOnMenuItemClickListener { it ->
//            startActivity(Intent(this, MainActivity9::class.java))
//            overridePendingTransition(0, 0)
//            return@setOnMenuItemClickListener true
//        }
//        val item10: MenuItem = menu.findItem(R.id.button_item10)
//        item10.setOnMenuItemClickListener { it ->
//            startActivity(Intent(this, MainActivity10::class.java))
//            overridePendingTransition(0, 0)
//            return@setOnMenuItemClickListener true
//        }
//        return true
//    }

}