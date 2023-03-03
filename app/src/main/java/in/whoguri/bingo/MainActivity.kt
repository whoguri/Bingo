package `in`.whoguri.bingo

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var list = Logic.getData()
    var list2 = ArrayList<Int>()
    var start = false
    val adapter by lazy {
        Adapter(this, list) {
            val data = list[it]
            data.isClicked = true
            list[it] = data
            list[it] = Logic.calculate(list, data, it + 1, start)
            start = true
            val temp = ArrayList<Int>()
            list.forEach {
                if (it.finalValue > 0) {
                    temp.add(it.finalValue)
                }
            }
            list2.clear()
            temp.sortedDescending().forEach {
                if (list2.size < 10) {
                    list2.add(it)
                }
            }
            adapter2.notifyDataSetChanged()
        }
    }
    val adapter2 by lazy {
        Adapter2(this, list2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<GridView>(R.id.grid).adapter = adapter
        findViewById<GridView>(R.id.grid2).adapter = adapter2
    }
}