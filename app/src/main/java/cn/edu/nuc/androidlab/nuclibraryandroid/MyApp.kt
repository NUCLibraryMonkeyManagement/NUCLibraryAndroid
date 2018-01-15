package cn.edu.nuc.androidlab.nuclibraryandroid

import android.app.Application
import cn.edu.nuc.androidlab.nuclibraryandroid.bean.UserResult

/**
 * MyAPP
 *
 * Created by MurphySL on 2018/1/15.
 */
class MyApp : Application(){

    companion object {
        @JvmStatic
        val instance: MyApp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MyApp()
        }
    }

    var user : UserResult.User? = null
}