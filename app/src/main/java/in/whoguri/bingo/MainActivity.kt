package `in`.whoguri.bingo

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var list = getData()
    val adapter by lazy {
        Adapter(this, list, {
            val data = list[it]
            data.finalValue = data.selfValue
            list[it] = data
//            adapter.notifyDataSetChanged()
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<GridView>(R.id.grid).adapter = adapter
    }

    fun getData(): ArrayList<Data> {
        val list = arrayListOf<Data>()
        list.add(Data(1, "b1", 8,48))
        list.add(Data(2, "b2", 4,18))
        list.add(Data(3, "b3", 4,16))
        list.add(Data(4, "b4", 4,18))
        list.add(Data(5, "b5", 8,48))

        list.add(Data(6, "i1", 4,18))
        list.add(Data(7, "i2", 6,36))
        list.add(Data(8, "i3", 4,16))
        list.add(Data(9, "i4", 6,36))
        list.add(Data(10, "i5", 4,18))

        list.add(Data(11, "n1", 4,16))
        list.add(Data(12, "n2", 4,16))
        list.add(Data(13, "n3", 0,0,0))
        list.add(Data(14, "n4", 4,16))
        list.add(Data(15, "n5", 4,16))

        list.add(Data(16, "g1", 4,18))
        list.add(Data(17, "g2", 6,36))
        list.add(Data(18, "g3", 4,16))
        list.add(Data(19, "g4", 6,36))
        list.add(Data(20, "g5", 4,18))

        list.add(Data(21, "o1", 8,48))
        list.add(Data(22, "o2", 4,18))
        list.add(Data(23, "o3", 4,16))
        list.add(Data(24, "o4", 4,18))
        list.add(Data(25, "o5", 8,48))
        return list
    }
}