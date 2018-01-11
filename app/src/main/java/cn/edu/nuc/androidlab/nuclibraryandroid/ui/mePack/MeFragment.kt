package cn.edu.nuc.androidlab.nuclibraryandroid.ui.mePack

import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.base.BaseFragment

/**
 * MeFragment
 *
 * Created by MurphySL on 2018/1/11.
 */
class MeFragment : BaseFragment(){

    companion object {
        @JvmStatic
        val instance: MeFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MeFragment()
        }
    }

    override fun getResLayout(): Int = R.layout.fragment_me

}