package cn.edu.nuc.androidlab.nuclibraryandroid.ui.mePack

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.baseModel.BaseFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.bean.Me
import cn.iwgang.countdownview.CountdownView
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_me.*
import java.text.SimpleDateFormat

/**
 * MeFragment
 *
 * Created by MurphySL on 2018/1/11.
 */
class MeFragment : BaseFragment(){

    private lateinit var adapter : MultiItemTypeAdapter<Me>

    private val data = ArrayList<Me>()

    companion object {
        private val TAG = "MeFragment"

        @JvmStatic
        val instance: MeFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MeFragment()
        }
    }

    init {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val suffix = format.format(System.currentTimeMillis())
        Log.i(TAG, suffix)

        val t1 = suffix +" 8:30"
        val t2 = suffix + " 10:30"
        val t3 = suffix + " 11:30"
        val t4 = suffix + " 14:30"
        val t5 = suffix +" 16:30"
        val t6 = suffix +" 18:30"
        val t7 = suffix + " 20:30"

        val format2 = SimpleDateFormat("yyyy-MM-dd HH:mm")

        data += Me(5, "1号学生阅览室", format2.parse(t1).time, format2.parse(t2).time)
        data += Me(5, "3号学生阅览室", format2.parse(t2).time, format2.parse(t3).time)
        data += Me(6, "5号学生阅览室", format2.parse(t4).time, format2.parse(t5).time)
        data += Me(7, "8号学生阅览室", format2.parse(t5).time, format2.parse(t6).time)
        data += Me(7, "10号学生阅览室", format2.parse(t6).time, format2.parse(t7).time)
    }

    override fun getResLayout(): Int = R.layout.fragment_me

    override fun initView(root: View?, savedInstanceState: Bundle?) {
        fab.setOnClickListener {
            val dialog = AlertDialog.Builder(context)

            dialog.setTitle("确认")
            dialog.setMessage("是否释放座位")
            dialog.setPositiveButton("确定") {
                p0, p1 ->
                snackBar(fab, "已释放座位")
            }
            dialog.setNegativeButton("取消"){
                p0, p1 ->

            }
            dialog.show()
        }
    }

    override fun logic(){
        val layoutManager = LinearLayoutManager(context)

        me_recycler_view.layoutManager = layoutManager

        adapter = MultiItemTypeAdapter(context, data)
        adapter.addItemViewDelegate(OutDurationItemDelagate(context))
        adapter.addItemViewDelegate(InDurationItemDelagate(context))

        me_recycler_view.adapter = adapter
    }

    fun fetchData(stream : Observable<Me>){
        stream.subscribeOn(Schedulers.io())
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
                            snackBar(me_recycler_view, error.toString())
                        },
                        {
                            Log.i(TAG, "GET USER SUCCESS")
                        }
                )
    }

}

class OutDurationItemDelagate(private val context : Context) : ItemViewDelegate<Me>{

    override fun getItemViewLayoutId(): Int = R.layout.item_appoint_1

    override fun convert(holder: ViewHolder?, t: Me?, position: Int) {
        holder?.let {
            val format = SimpleDateFormat("HH:mm")
            val startTime = format.format(t?.start)
            val endTime = format.format(t?.end)
            it.setText(R.id.start_time, startTime)
            it.setText(R.id.end_time, endTime)
            it.setText(R.id.floor, t?.floor.toString())
            it.setText(R.id.address, t?.name)
            it.setOnClickListener(R.id.card_view){
                context.startActivity(Intent(context, DetailActivity::class.java))
            }
        }?:Log.i("MeFragment", "OUT DURATION LOAD FAIL")
    }

    override fun isForViewType(item: Me?, position: Int): Boolean {
        val now = System.currentTimeMillis()
        item?.let {
            return it.start > now || it.end < now
        }?:return true
    }
}

class InDurationItemDelagate(private val context : Context) : ItemViewDelegate<Me>{
    override fun getItemViewLayoutId(): Int = R.layout.item_appoint_2

    override fun convert(holder: ViewHolder?, t: Me?, position: Int) {
        holder?.let {
            it.getView<CountdownView>(R.id.count_down).start((t?.end!! - System.currentTimeMillis()))
            it.setText(R.id.floor, t.floor.toString())
            it.setText(R.id.address, t.name)
            it.setOnClickListener(R.id.card_view){
                context.startActivity(Intent(context, DetailActivity::class.java))
            }
        }?:Log.i("MeFragment", "IN DURATION LOAD FAIL")
    }

    override fun isForViewType(item: Me?, position: Int): Boolean {
        val now = System.currentTimeMillis()
        item?.let {
            return it.start < now && it.end > now
        }?:return false
    }
}
