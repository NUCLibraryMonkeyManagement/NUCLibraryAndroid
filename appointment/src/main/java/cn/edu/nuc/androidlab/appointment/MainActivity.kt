package cn.edu.nuc.androidlab.appointment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import cn.edu.nuc.androidlab.appointment.view.SeatTable



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seat.setScreenName("主楼301")
        seat.setMaxSelected(1)
        seat.setSeatChecker(object : SeatTable.SeatChecker {

            override fun isValidSeat(row: Int, column: Int): Boolean {
                return if (column == 2) {
                    false
                } else true
            }

            override fun isSold(row: Int, column: Int): Boolean {
                return if (row == 6 && column == 6) {
                    true
                } else false
            }

            override fun checked(row: Int, column: Int) {

            }

            override fun unCheck(row: Int, column: Int) {

            }

            override fun checkedSeatTxt(row: Int, column: Int): Array<String>? {
                return null
            }

        })
        seat.setData(10, 15)
    }
}
