package `in`.whoguri.bingo.oldscreens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import `in`.whoguri.bingo.AppData
import `in`.whoguri.bingo.Data
import `in`.whoguri.bingo.adapters.GridAdapter
import `in`.whoguri.bingo.helpers.Logic
import `in`.whoguri.bingo.R
import `in`.whoguri.bingo.adapters.ResultAdapter

class MainActivity6 : AppCompatActivity() {
    val adapter by lazy {
        GridAdapter(this, 6, AppData.dataList) { it, b ->
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
//    var variant = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.title_).text = "Bingo! 2"

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en-us")
        AppCompatDelegate.setApplicationLocales(appLocale)
//actionBar.setDisplayHomeAsUpEnabled()
        averageAdapter.setNotifyOnChange(true)
        findViewById<GridView>(R.id.grid).adapter = adapter
        findViewById<GridView>(R.id.sortGrid).adapter = resultAdapter
        findViewById<GridView>(R.id.avrageGrid).adapter = averageAdapter
        planA()
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

    }

    private fun planA() {
        findViewById<GridView>(R.id.sortGrid).visibility = View.VISIBLE
        findViewById<GridView>(R.id.avrageGrid).visibility = View.GONE
    }

    private fun planB() {
        findViewById<GridView>(R.id.sortGrid).visibility = View.GONE
        findViewById<GridView>(R.id.avrageGrid).visibility = View.VISIBLE
    }

    private fun recal() {
        AppData.dataList = Logic.calResult5(AppData.dataList, 2)
//        AppData.dataList = Logic.calResult5(AppData.dataList, 1)
//        AppData.dataList = Logic.calResult(AppData.dataList)
//        AppData.dataList = Logic.calResult7(AppData.dataList)
//        AppData.dataList = Logic.calResult8(AppData.dataList)
//        AppData.dataList = NewLogic.calResult9(AppData.dataList)
//
//        AppData.dataList = Logic.calResult3(AppData.dataList)
        val temp = ArrayList<Data>()
        AppData.dataList.forEach {
            if (it.finalValue6 > 0 && !it.isClicked) {
                temp.add(it)
            }
        }
        AppData.resultList.clear()
        AppData.averageList.clear()
        averageAdapter.clear()
        temp.sortedByDescending { it.finalValue6 }.forEach {
            if (AppData.resultList.size < 10 && it.number != 13) {
                AppData.resultList.add(it.code)
            }
        }
        AppData.averageList = Logic.calAverage5(AppData.dataList)
        resultAdapter.notifyDataSetChanged()
        averageAdapter.addAll(AppData.averageList)
//                adapter3.notifyDataSetChanged()
        adapter.notify_()

    }

    fun restart() {
        AppData.reset()
        startActivity(Intent(this, MainActivity6::class.java))
        overridePendingTransition(0, 0)
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        recal()
//        val temp = ArrayList<Data>()
//        AppData.dataList.forEach {
//            if (it.finalValue6 > 0 && !it.isClicked) {
//                temp.add(it)
//            }
//        }
//        AppData.resultList.clear()
//        AppData.averageList.clear()
//        averageAdapter.clear()
//        temp.sortedByDescending { it.finalValue6 }.forEach {
//            if (AppData.resultList.size < 10 && it.number != 13) {
//                AppData.resultList.add(it.code)
//            }
//        }
//        AppData.averageList = Logic.calAverage5(AppData.dataList)
//        averageAdapter.addAll(AppData.averageList)
    }

}