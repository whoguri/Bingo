package `in`.whoguri.bingo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class MainActivity5_dep : AppCompatActivity() {
    val adapter by lazy {
        GridAdapter(this, 5, AppData.dataList) { it, b ->
            val data = AppData.dataList[it]
            data.isClicked = !b
            AppData.dataList[it] = data
            if (AppData.dataList.filter { it.isClicked }.isEmpty()) {
                restart()
                return@GridAdapter
            } else {
                AppData.dataList = Logic.calResult5(AppData.dataList)
                AppData.dataList = Logic.calResult(AppData.dataList)
                AppData.dataList = Logic.calResult3(AppData.dataList)
                val temp = ArrayList<Data>()
                AppData.dataList.forEach {
                    if (it.finalValue5 > 0 && !it.isClicked) {
                        temp.add(it)
                    }
                }
                AppData.resultList.clear()
                AppData.averageList.clear()
                averageAdapter.clear()
                temp.sortedByDescending { it.finalValue5 }.forEach {
                    if (AppData.resultList.size < 10 && it.number != 13) {
                        AppData.resultList.add(it.code)
                    }
                }
                AppData.averageList = Logic.calAverage5(AppData.dataList)
                resultAdapter.notifyDataSetChanged()
                averageAdapter.addAll(AppData.averageList)
//                adapter3.notifyDataSetChanged()
            }
        }
    }
    val resultAdapter by lazy {
        ResultAdapter(this, AppData.resultList)
    }
    val averageAdapter by lazy {
        ResultAdapter(this, AppData.averageList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.title_).text = "Bingo! 5"

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en-us")
        AppCompatDelegate.setApplicationLocales(appLocale)
//actionBar.setDisplayHomeAsUpEnabled()
        averageAdapter.setNotifyOnChange(true)
        findViewById<GridView>(R.id.grid).adapter = adapter
        findViewById<GridView>(R.id.sortGrid).adapter = resultAdapter
        findViewById<GridView>(R.id.avrageGrid).adapter = averageAdapter
        planA()

        findViewById<TextView>(R.id.restart).setOnClickListener {
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

    fun restart() {
        AppData.reset()
        startActivity(Intent(this, MainActivity5_dep::class.java))
        overridePendingTransition(0, 0)
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()

        val temp = ArrayList<Data>()
        AppData.dataList.forEach {
            if (it.finalValue5 > 0 && !it.isClicked) {
                temp.add(it)
            }
        }
        AppData.resultList.clear()
        AppData.averageList.clear()
        averageAdapter.clear()
        temp.sortedByDescending { it.finalValue5 }.forEach {
            if (AppData.resultList.size < 10 && it.number != 13) {
                AppData.resultList.add(it.code)
            }
        }
        AppData.averageList = Logic.calAverage5(AppData.dataList)
        averageAdapter.addAll(AppData.averageList)
    }

}