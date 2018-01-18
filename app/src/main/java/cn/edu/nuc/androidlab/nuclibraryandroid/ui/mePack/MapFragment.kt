package cn.edu.nuc.androidlab.nuclibraryandroid.ui.mePack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.baseModel.BaseFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.util.LocationManager
import cn.edu.nuc.androidlab.nuclibraryandroid.view.MyInfoWindow
import cn.edu.nuc.androidlab.nuclibraryandroid.view.WalkRouteOverlay
import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.TextureMapView
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.model.animation.Animation
import com.amap.api.maps.model.animation.ScaleAnimation
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.route.*

/**
 * MapFragment
 *
 * Created by MurphySL on 2018/1/17.
 */
class MapFragment : BaseFragment(){

    companion object {
        private val TAG: String = MapFragment::class.java.simpleName

        @JvmStatic
        val instance: MapFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MapFragment()
        }
    }

    override fun getResLayout(): Int = R.layout.fragment_map


    private lateinit var map: TextureMapView
    private lateinit var aMap: AMap
    private lateinit var uiSettings: UiSettings

    private var mWalkRouteResult : WalkRouteResult? = null

    private var locationManager: LocationManager? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        map.onCreate(savedInstanceState)
        aMap = map.map

        //初始化地图图层配置
        initMap()
        initLocation()
    }

    private fun initNavi(latitude : Double, longitude : Double) {
        val routeSearch = RouteSearch(context)
        routeSearch.setRouteSearchListener(object : RouteSearch.OnRouteSearchListener{
            override fun onDriveRouteSearched(p0: DriveRouteResult?, p1: Int) {

            }

            override fun onBusRouteSearched(p0: BusRouteResult?, p1: Int) {

            }

            override fun onRideRouteSearched(p0: RideRouteResult?, p1: Int) {

            }

            override fun onWalkRouteSearched(result: WalkRouteResult?, p1: Int) {
                Log.i(TAG, "onWalkRouteSearched")
                aMap.clear()// 清理地图上的所有覆盖物

                if (result?.paths != null) {
                    if (result.paths.size > 0) {
                        mWalkRouteResult = result
                        val walkPath = result.paths[0]
                        val walkRouteOverlay =  WalkRouteOverlay(
                                context, aMap, walkPath,
                                result.startPos,
                                result.targetPos)
                        walkRouteOverlay.removeFromMap()
                        walkRouteOverlay.addToMap()
                        walkRouteOverlay.zoomToSpan()
                        /*mBottomLayout.setVisibility(View.VISIBLE);
                        int dis = (int) walkPath.getDistance();
                        int dur = (int) walkPath.getDuration();
                        String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
                        mRotueTimeDes.setText(des);
                        mRouteDetailDes.setVisibility(View.GONE);
                        mBottomLayout.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext,
                                        WalkRouteDetailActivity.class);
                                intent.putExtra("walk_path", walkPath);
                                intent.putExtra("walk_result",
                                        mWalkRouteResult);
                                startActivity(intent)
                            }
                        })*/
                    } else if (result.paths == null) {
                        snackBar(map, "NO_RESULT")
                    }
                } else {
                    snackBar(map, "NO_RESULT")
                }
            }
        })

        val query = RouteSearch.WalkRouteQuery(RouteSearch.FromAndTo(LatLonPoint(latitude, longitude), LatLonPoint(38.015482, 112.448338)))
        routeSearch.calculateWalkRouteAsyn(query)
    }

    private fun initMark() {
        val latLng = LatLng(38.015482, 112.448338)
        val markerOption = MarkerOptions()
        markerOption.position(latLng)
        markerOption.title("目的地")
        markerOption.snippet("3号学生阅览室")
        markerOption.draggable(false)
        //markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.flag)))
        val marker = aMap.addMarker(markerOption)
        val anim: Animation = ScaleAnimation(0f, 1.0f, 0f, 1.0f)
        val duration = 1000L
        anim.setDuration(duration)
        anim.setInterpolator(BounceInterpolator())
        marker.setAnimation(anim)
        marker.startAnimation()
        if(context != null){
            val myInfo = MyInfoWindow(context, marker.title, "目的地")
            aMap.setInfoWindowAdapter(myInfo)
        }
        marker.showInfoWindow()
    }

    override fun onStart() {
        super.onStart()
        //设置mark点击事件
        clickerListener()
        initMark()
    }

    private fun clickerListener() {
        aMap.setOnMarkerClickListener { marker ->
            val myInfo = MyInfoWindow(context, marker.title, "Android 实验室")
            aMap.setInfoWindowAdapter(myInfo)
            marker.showInfoWindow()
            true
        }
        aMap.setOnInfoWindowClickListener {
            snackBar(map, "已点击")
        }
    }

    private fun initMap() {
        //定位蓝点
        val myLocationStyle = MyLocationStyle()
        aMap.moveCamera(CameraUpdateFactory.zoomTo(20.0f))
        aMap.moveCamera(CameraUpdateFactory.changeTilt(180.0f))
        myLocationStyle.interval(20000)
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE)
        aMap.myLocationStyle = myLocationStyle
        aMap.isMyLocationEnabled = true
        uiSettings = aMap.uiSettings
        uiSettings.isZoomControlsEnabled = false
    }

    private fun initLocation() {
        locationManager = LocationManager(context)
        if (locationManager != null) {
            locationManager?.setLocationListener(object : LocationManager.LocationListener {
                override fun onLocationChanged(aMapLocation: AMapLocation?) {
                    Log.i(TAG, "LOCATIONCHANGED")
                    if (aMapLocation != null && aMapLocation.errorCode == 0) {
                        Log.i(TAG, "${aMapLocation.latitude}")
                        initNavi(aMapLocation.latitude, aMapLocation.longitude)
                    } else {
                        Log.i(TAG, "定位失败，")
                    }
                }
            })
            locationManager?.openLocation()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_map, container, false)
        map = root!!.findViewById(R.id.map)
        return root
    }
    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (locationManager != null)
            locationManager?.stopLocation()
        locationManager = null
        map.onDestroy()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        map.onSaveInstanceState(outState)
    }

}