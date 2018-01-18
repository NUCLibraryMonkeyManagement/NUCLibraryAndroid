package cn.edu.nuc.androidlab.nuclibraryandroid.ui.appointmentPack

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.baseModel.BaseFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.bean.Classroom
import com.bigkoo.pickerview.OptionsPickerView
import com.bigkoo.pickerview.TimePickerView
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_appoint.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Appoint
 *
 * Created by MurphySL on 2018/1/11.
 */
class AppointFragment : BaseFragment(){

    companion object {
        private val TAG = "RoomFragment"

        @JvmStatic
        val instance: AppointFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppointFragment()
        }
    }

    override fun getResLayout(): Int = R.layout.fragment_appoint

    private val data = ArrayList<Classroom>()
    private lateinit var adapter: CommonAdapter<Classroom>

    init {
        (0..10).forEach {
            val room = Classroom("主楼 30$it", it)
            data += room
        }
    }

    override fun initView(root: View?, savedInstanceState: Bundle?) {
        /*(activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        toolbar.title = getString(R.string.appointment)*/

        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        startDate.time = Date(System.currentTimeMillis())
        endDate.time = Date(System.currentTimeMillis())
        endDate.add(Calendar.DATE, 1)

        val pvDate = TimePickerView.Builder(context,
                TimePickerView.OnTimeSelectListener {
                    date, v ->

                }).setType(booleanArrayOf(false, true, true, false, false, false))
                .setRangDate(startDate, endDate).build()

        val duration = ArrayList<String>()
        duration.add("8:30-10:30")
        duration.add("10:30-11:30")
        duration.add("14:30-16:30")
        duration.add("16:30-18:30")
        duration.add("18:30-20:30")
        val pvTime = OptionsPickerView.Builder(context, OptionsPickerView.OnOptionsSelectListener {
            options1, _, _, v ->

        }).build()
        pvTime.setPicker(duration)

        iv_select_time.setOnClickListener {
            pvTime.show()
        }

        iv_select_date.setOnClickListener {
            pvDate.show()
        }
    }

    override fun logic() {
        val layoutManager = LinearLayoutManager(context)
        room_recycler_view.layoutManager = layoutManager

        adapter = object : CommonAdapter<Classroom>(context, R.layout.item_room, data) {
            override fun convert(holder: ViewHolder?, t: Classroom?, position: Int) {
                holder?.let {
                    it.setText(R.id.classroom, t?.name)
                    it.setText(R.id.seat_num, t?.num.toString())
                    it.setOnClickListener(R.id.classroom_item, {
                        startActivity(Intent(activity, SeatActivity::class.java))
                    })
                }
            }
        }

        room_recycler_view.adapter = adapter
    }

    fun fetchData(data: Observable<Classroom>) =
            data.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                            {
                                next ->
                                this.data.add(next)
                                adapter.notifyDataSetChanged()
                            },
                            {
                                error ->
                                Log.i(TAG, "GET USER FAIL : $error")
                                snackBar(room_recycler_view, error.toString())
                            },
                            {
                                Log.i(TAG, "GET USER SUCCESS")
                            }
                    )

}