package `in`.whoguri.bingo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import `in`.whoguri.bingo.helpers.roundOffDecimal2

class CallActivity : AppCompatActivity() {
    lateinit var tv_n_1: TextView
    lateinit var tv_n_2: TextView
    lateinit var tv_n_3: TextView
    lateinit var tv_n_4: TextView
    lateinit var tv_n_5: TextView

    lateinit var tv_d_1: TextView
    lateinit var tv_d_2: TextView
    lateinit var tv_d_3: TextView
    lateinit var tv_d_4: TextView
    lateinit var tv_d_5: TextView

    lateinit var tv_r_1: TextView
    lateinit var tv_r_2: TextView
    lateinit var tv_r_3: TextView
    lateinit var tv_r_4: TextView
    lateinit var tv_r_5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cal)
        tv_n_1 = findViewById(R.id.cal_n_1)
        tv_n_2 = findViewById(R.id.cal_n_2)
        tv_n_3 = findViewById(R.id.cal_n_3)
        tv_n_4 = findViewById(R.id.cal_n_4)
        tv_n_5 = findViewById(R.id.cal_n_5)

        tv_d_1 = findViewById(R.id.cal_d_1)
        tv_d_2 = findViewById(R.id.cal_d_2)
        tv_d_3 = findViewById(R.id.cal_d_3)
        tv_d_4 = findViewById(R.id.cal_d_4)
        tv_d_5 = findViewById(R.id.cal_d_5)

        tv_r_1 = findViewById(R.id.cal_r_1)
        tv_r_2 = findViewById(R.id.cal_r_2)
        tv_r_3 = findViewById(R.id.cal_r_3)
        tv_r_4 = findViewById(R.id.cal_r_4)
        tv_r_5 = findViewById(R.id.cal_r_5)


        tv_n_1.setOnClickListener {
            var cv = tv_n_1.text.toString().toInt()
            if (cv == 1)
                cv = 15
            else
                cv = cv - 1
            tv_n_1.text = cv.toString()
            tv_r_1.text = (tv_n_1.text.toString().toDouble() / tv_d_1.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_n_2.setOnClickListener {
            var cv = tv_n_2.text.toString().toInt()
            if (cv == 1)
                cv = 15
            else
                cv = cv - 1
            tv_n_2.text = cv.toString()
            tv_r_2.text = (tv_n_2.text.toString().toDouble() / tv_d_2.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_n_3.setOnClickListener {
            var cv = tv_n_3.text.toString().toInt()
            if (cv == 1)
                cv = 15
            else
                cv = cv - 1
            tv_n_3.text = cv.toString()
            tv_r_3.text = (tv_n_3.text.toString().toDouble() / tv_d_3.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_n_4.setOnClickListener {
            var cv = tv_n_4.text.toString().toInt()
            if (cv == 1)
                cv = 15
            else
                cv = cv - 1
            tv_n_4.text = cv.toString()
            tv_r_4.text = (tv_n_4.text.toString().toDouble() / tv_d_4.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_n_5.setOnClickListener {
            var cv = tv_n_5.text.toString().toInt()
            if (cv == 1)
                cv = 15
            else
                cv = cv - 1
            tv_n_5.text = cv.toString()
            tv_r_5.text = (tv_n_5.text.toString().toDouble() / tv_d_5.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_d_1.setOnClickListener {
            var cv = tv_d_1.text.toString().toInt()
            if (cv == 1)
                cv = 5
            else
                cv = cv - 1
            tv_d_1.text = cv.toString()
            tv_r_1.text = (tv_n_1.text.toString().toDouble() / tv_d_1.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_d_2.setOnClickListener {
            var cv = tv_d_2.text.toString().toInt()
            if (cv == 1)
                cv = 5
            else
                cv = cv - 1
            tv_d_2.text = cv.toString()
            tv_r_2.text = (tv_n_2.text.toString().toDouble() / tv_d_2.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_d_3.setOnClickListener {
            var cv = tv_d_3.text.toString().toInt()
            if (cv == 1)
                cv = 5
            else
                cv = cv - 1
            tv_d_3.text = cv.toString()
            tv_r_3.text = (tv_n_3.text.toString().toDouble() / tv_d_3.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_d_4.setOnClickListener {
            var cv = tv_d_4.text.toString().toInt()
            if (cv == 1)
                cv = 5
            else
                cv = cv - 1
            tv_d_4.text = cv.toString()
            tv_r_4.text = (tv_n_4.text.toString().toDouble() / tv_d_4.text.toString().toDouble()).roundOffDecimal2().toString()
        }
        tv_d_5.setOnClickListener {
            var cv = tv_d_5.text.toString().toInt()
            if (cv == 1)
                cv = 5
            else
                cv = cv - 1
            tv_d_5.text = cv.toString()
            tv_r_5.text = (tv_n_5.text.toString().toDouble() / tv_d_5.text.toString().toDouble()).roundOffDecimal2().toString()
        }
    }
}