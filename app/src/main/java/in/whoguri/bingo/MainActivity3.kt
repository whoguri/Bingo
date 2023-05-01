package `in`.whoguri.bingo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class MainActivity3 : AppCompatActivity() {
    val adapter by lazy {
        GridAdapter(this, 3, AppData.list) { it, b ->
            val data = AppData.list[it]
            data.isClicked = !b
            AppData.list[it] = data
            if (AppData.list.filter { it.isClicked }.isEmpty()) {
                restart()
                return@GridAdapter
            } else {
                AppData.list = Logic.calResult(AppData.list)
                AppData.list = Logic.calResult3(AppData.list)
                val temp = ArrayList<Data>()
                AppData.list.forEach {
                    if (it.finalValue3 > 0 && !it.isClicked) {
                        temp.add(it)
                    }
                }
                AppData.list2.clear()
                AppData.list3.clear()
                averageAdapter.clear()
                temp.sortedByDescending { it.finalValue3 }.forEach {
                    if (AppData.list2.size < 10) {
                        AppData.list2.add(it.code)
                    }
                }
                AppData.list3 = Logic.calAverage3(AppData.list)
                resultAdapter.notifyDataSetChanged()
                averageAdapter.addAll(AppData.list3)
//                adapter3.notifyDataSetChanged()
            }
        }
    }
    val resultAdapter by lazy {
        ResultAdapter(this, AppData.list2)
    }
    val averageAdapter by lazy {
        ResultAdapter(this, AppData.list3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.title_).text = "Bingo! 3"

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en-us")
        AppCompatDelegate.setApplicationLocales(appLocale)
//actionBar.setDisplayHomeAsUpEnabled()
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
        AppData.reset()
        startActivity(Intent(this, MainActivity3::class.java))
        overridePendingTransition(0, 0)
        finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item1: MenuItem = menu.findItem(R.id.button_item1)
        item1.setOnMenuItemClickListener { it ->
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
            return@setOnMenuItemClickListener true
        }
        val item2: MenuItem = menu.findItem(R.id.button_item2)
        item2.setOnMenuItemClickListener { it ->
            startActivity(Intent(this, MainActivity2::class.java))
            overridePendingTransition(0, 0)
            return@setOnMenuItemClickListener true
        }
        return true
    }

    override fun onResume() {
        super.onResume()

        val temp = ArrayList<Data>()
        AppData.list.forEach {
            if (it.finalValue3 > 0 && !it.isClicked) {
                temp.add(it)
            }
        }
        AppData.list2.clear()
        AppData.list3.clear()
        averageAdapter.clear()
        temp.sortedByDescending { it.finalValue3 }.forEach {
            if (AppData.list2.size < 10) {
                AppData.list2.add(it.code)
            }
        }
        AppData.list3 = Logic.calAverage3(AppData.list)
        averageAdapter.addAll(AppData.list3)
    }
}