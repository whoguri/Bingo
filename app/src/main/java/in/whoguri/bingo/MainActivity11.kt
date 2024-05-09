package `in`.whoguri.bingo

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        GridAdapter(this, 11, AppData.dataList) { it, b ->
            val data = AppData.dataList[it]
            data.isClicked = !b
            AppData.dataList[it] = data
            if (AppData.dataList.filter { it.isClicked }.isEmpty()) {
                restart()
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

    fun changeTab(no: Int) {
        adapter.setType(no)
//        findViewById<TextView>(R.id.cal_1)
//        findViewById<TextView>(R.id.cal_2)
//        findViewById<TextView>(R.id.cal_3)
//        findViewById<TextView>(R.id.cal_4)
        val title = findViewById<TextView>(R.id.title_)
        when (no) {
            11 -> title.text = "Bingo! 11"
            10 -> title.text = "Bingo! G"
            1 -> title.text = "Bingo! 1"
            9 -> title.text = "Bingo! 9"
            else -> title.text = "Bingo!"
        }
        findViewById<LinearLayout>(R.id.result1).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.result2).visibility = View.GONE
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
        planA()
        changeTab(11)
        findViewById<LinearLayout>(R.id.resetD).setOnClickListener {
            Logic.clickDs()
            recal()
        }
        findViewById<LinearLayout>(R.id.restart).setOnClickListener {
            restart()
        }
        findViewById<Button>(R.id.btn1).setOnClickListener {
            planA()
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            planB()
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
            changeTab(9)
        }
    }

    private fun planA() {
        if (adapter.calType == 10) {
            findViewById<LinearLayout>(R.id.result1).visibility = View.GONE
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
                Log.e(">>>>>>> "+result.get(0).first, result.get(0).second.toString())
                Log.e(">>>>>>> "+result.get(1).first, result.get(1).second.toString())
                if (result.get(0).first == "B-O") {
                    num1.setBackgroundColor(getColor(R.color.yellow))
                    num2.setBackgroundColor(getColor(R.color.green))
                } else {
                    num1.setBackgroundColor(getColor(R.color.green))
                    num2.setBackgroundColor(getColor(R.color.yellow))
                }
                adapter.setHigh(result.get(0).first)
            }
            view = "a"
        } else {
            findViewById<LinearLayout>(R.id.result1).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.result2).visibility = View.GONE

            findViewById<GridView>(R.id.sortGrid).visibility = View.VISIBLE
            findViewById<GridView>(R.id.avrageGrid).visibility = View.GONE
        }
    }

    private fun planB() {
        if (adapter.calType == 10) {
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
                Log.e(">>>>>>> "+result.get(0).first, result.get(0).second.toString())
                Log.e(">>>>>>> "+result.get(1).first, result.get(1).second.toString())
                if (result.get(0).first == "B-O") {
                    num1.setBackgroundColor(getColor(R.color.yellow))
                    num2.setBackgroundColor(getColor(R.color.green))
                } else {
                    num1.setBackgroundColor(getColor(R.color.green))
                    num2.setBackgroundColor(getColor(R.color.yellow))
                }
                adapter.setHigh(result.get(0).first)
            }
            view = "b"
        }
        else{   findViewById<GridView>(R.id.sortGrid).visibility = View.GONE
            findViewById<GridView>(R.id.avrageGrid).visibility = View.VISIBLE
        }
    }
    var result = arrayListOf<Pair<String, Double>>()
    var view = "a"

    private fun recal() {
        if (adapter.calType == 10) {
            val result_ = NewLogic.calResult10_Group(AppData.dataList)
            AppData.dataList = result_.second
            AppData.resultList.clear()
            AppData.averageList.clear()
            averageAdapter.clear()
            result.clear()
            result_.first.sortedByDescending { it.second }.forEach {
                result.add(it)
            }
            if (view == "a")
                planA()
            else
                planB()
            adapter.notify_()

        } else {
            if (adapter.calType == 11)
                AppData.dataList = NewLogic.calResult11(AppData.dataList)
            else if (adapter.calType == 1)
                AppData.dataList = Logic.calResult5(AppData.dataList)
            else if (adapter.calType == 9)
                AppData.dataList = NewLogic.calResult9(AppData.dataList)

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

        }
    }

    fun restart() {
        AppData.reset()
        startActivity(Intent(this, MainActivity11::class.java))
        overridePendingTransition(0, 0)
        finishAffinity()
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