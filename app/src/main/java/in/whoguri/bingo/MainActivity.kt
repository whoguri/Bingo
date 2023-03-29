package `in`.whoguri.bingo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class MainActivity : AppCompatActivity() {
    var list = Logic.getData()
    var list2 = ArrayList<String>()
    var list3 = ArrayList<String>()

    var start = false
    val adapter by lazy {
        GridAdapter(this, list) { it, b ->
            val data = list[it]
            data.isClicked = !b
            list[it] = data
            if (list.filter { it.isClicked }.isEmpty()) {
                restart()
                return@GridAdapter
            } else {
                list = Logic.calResult(list)
                start = true
                val temp = ArrayList<Data>()
                list.forEach {
                    if (it.finalValue > 0 && !it.isClicked) {
                        temp.add(it)
                    }
                }
                list2.clear()
                list3.clear()
                averageAdapter.clear()
                temp.sortedByDescending { it.finalValue }.forEach {
                    if (list2.size < 10) {
                        list2.add(it.code)
                    }
                }
                list3 = Logic.calAverage(list)
                resultAdapter.notifyDataSetChanged()
                averageAdapter.addAll(list3)
//                adapter3.notifyDataSetChanged()
            }
        }
    }
    val resultAdapter by lazy {
        ResultAdapter(this, list2)
    }
    val averageAdapter by lazy {
        ResultAdapter(this, list3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en-us")
        AppCompatDelegate.setApplicationLocales(appLocale)

        averageAdapter.setNotifyOnChange(true)
        findViewById<GridView>(R.id.grid).adapter = adapter
        findViewById<GridView>(R.id.sortGrid).adapter = resultAdapter
        findViewById<GridView>(R.id.avrageGrid).adapter = averageAdapter
        planA()

        findViewById<Button>(R.id.restart).setOnClickListener {
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
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(0, 0)
        finishAffinity()
    }
}