package cn.edu.nuc.androidlab.appointment.view

import android.os.Bundle
import android.view.View
import cn.edu.nuc.androidlab.appointment.R
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


/**
 * RoomFragment
 *
 * Created by MurphySL on 2018/1/15.
 */
/*
class RoomFragment : BaseFragment() {

    companion object {
        private val TAG = "RoomFragment"
    }

    private val data = ArrayList<Classroom>()
    private lateinit var adapter: CommonAdapter<Classroom>

    override fun getResLayout(): Int = R.layout.fragment_room

    override fun initView(root: View?, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.appointment)
    }

    override fun logic() {
        val layoutManager = LinearLayoutManager(context)
        room_recycler_view.layoutManager = layoutManager

        adapter = object : CommonAdapter<Classroom>(context, R.layout.item_room, data) {
            override fun convert(holder: ViewHolder?, t: Classroom?, position: Int) {

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

}*/
