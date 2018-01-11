package cn.edu.nuc.androidlab.nuclibraryandroid.ui.localePack

import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.base.BaseFragment

/**
 * Locale
 *
 * Created by MurphySL on 2018/1/11.
 */
class LocaleFragment : BaseFragment(){

    companion object {
        @JvmStatic
        val instance: LocaleFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LocaleFragment()
        }
    }

    override fun getResLayout(): Int = R.layout.fragment_locale


}