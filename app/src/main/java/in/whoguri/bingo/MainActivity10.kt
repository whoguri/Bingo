package `in`.whoguri.bingo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class MainActivity10 : AppCompatActivity() {
    val adapter by lazy {
        GridAdapter(this, 10, AppData.dataList) { it, b ->
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
    var result = arrayListOf<Pair<String, Double>>()
    var view = "a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main10)
        findViewById<TextView>(R.id.title_).text = "Bingo! 10"

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en-us")
        AppCompatDelegate.setApplicationLocales(appLocale)
//actionBar.setDisplayHomeAsUpEnabled()
        averageAdapter.setNotifyOnChange(true)
        findViewById<GridView>(R.id.grid).adapter = adapter
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
            if (result.get(0).first === "B-O") {
                num1.setBackgroundColor(getColor(R.color.yellow))
                num2.setBackgroundColor(getColor(R.color.green))
            } else {
                num1.setBackgroundColor(getColor(R.color.green))
                num2.setBackgroundColor(getColor(R.color.yellow))
            }
        }
        view = "a"
    }

    private fun planB() {
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
            if (result.get(0).first === "B-O") {
                num1.setBackgroundColor(getColor(R.color.yellow))
                num2.setBackgroundColor(getColor(R.color.green))
            } else {
                num1.setBackgroundColor(getColor(R.color.green))
                num2.setBackgroundColor(getColor(R.color.yellow))

            }
        }
        view = "b"
    }

    private fun recal() {
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

    }

    fun restart() {
        AppData.reset()
        startActivity(Intent(this, MainActivity10::class.java))
        overridePendingTransition(0, 0)
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        recal()
    }
}