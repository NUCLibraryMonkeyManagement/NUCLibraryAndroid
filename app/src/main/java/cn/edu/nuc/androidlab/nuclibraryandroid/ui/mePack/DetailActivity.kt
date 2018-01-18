package cn.edu.nuc.androidlab.nuclibraryandroid.ui.mePack

import android.os.Bundle
import android.util.Log
import cn.edu.nuc.androidlab.map.LocationListener
import cn.edu.nuc.androidlab.map.LocationUtils
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.baseModel.BaseActivity

/**
 * DetailActivity
 *
 * Created by MurphySL on 2018/1/17.
 */
class DetailActivity : BaseActivity(){

    companion object {
        private val TAG = "DetailActivity"
    }

    override fun getResLayout(): Int = R.layout.activity_detail

    override fun logic(savedInstanceState: Bundle?) {
        val location = LocationUtils(this)

        addFragment(MapFragment.instance, R.id.map_content)

        location.setLocationListener(object : LocationListener{
            override fun onLocationChanged(address: String, latitude: Double, longitude: Double) {
                Log.i(TAG, address)
            }
        })
    }

}