package cn.edu.nuc.androidlab.nuclibraryandroid.ui.appointmentPack

import android.os.Bundle
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.baseModel.BaseActivity
import kotlinx.android.synthetic.main.activity_seat.*

/**
 * Created by MurphySL on 2018/1/17.
 */
class SeatActivity : BaseActivity(){

    override fun getResLayout(): Int = R.layout.activity_seat

    override fun logic(savedInstanceState: Bundle?) {
        seat.setScreenName("主楼301")
        seat.setMaxSelected(1)
        seat.setSeatChecker(object : cn.edu.nuc.androidlab.nuclibraryandroid.view.SeatTable.SeatChecker {

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