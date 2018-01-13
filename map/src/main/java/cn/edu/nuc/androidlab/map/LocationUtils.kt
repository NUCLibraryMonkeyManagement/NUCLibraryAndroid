package cn.edu.nuc.androidlab.map

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

/**
 * Location
 *
 * Created by MurphySL on 2018/1/13.
 */
class LocationUtils(context : Context){

    private val client : AMapLocationClient = AMapLocationClient(context.applicationContext)
    private lateinit var listener : AMapLocationListener

    private val option : AMapLocationClientOption = AMapLocationClientOption()

    init {
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn // 签到模式
        client.stopLocation()
        client.setLocationOption(option)
        client.startLocation()
    }

    fun setLocationListener(listener: LocationListener){
        this.listener = AMapLocationListener {
            location ->
            location?.let {
                if(location.errorCode == 0){
                    listener.onLocationChanged(location.aoiName, location.latitude, location.longitude)
                }
            }
        }
        client.setLocationListener(this.listener)
    }

    fun onDestory(){
        client.stopLocation()
        client.onDestroy()
    }
}

interface LocationListener{
    fun onLocationChanged(address : String, latitude : Double, longitude : Double)
}