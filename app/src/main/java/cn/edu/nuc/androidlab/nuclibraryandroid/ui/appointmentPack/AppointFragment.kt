package cn.edu.nuc.androidlab.nuclibraryandroid.ui.appointmentPack

import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.base.BaseFragment

/**
 * Appoint
 *
 * Created by MurphySL on 2018/1/11.
 */
class AppointFragment : BaseFragment(){

    companion object {
        @JvmStatic
        val instance: AppointFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppointFragment()
        }
    }

    override fun getResLayout(): Int = R.layout.fragment_appoint

}