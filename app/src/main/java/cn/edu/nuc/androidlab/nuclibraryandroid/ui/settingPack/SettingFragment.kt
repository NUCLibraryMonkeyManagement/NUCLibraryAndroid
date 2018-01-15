package cn.edu.nuc.androidlab.nuclibraryandroid.ui.settingPack

import android.content.Intent
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.base.BaseFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.ReviseActivity
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * Setting
 *
 * Created by MurphySL on 2018/1/11.
 */
class SettingFragment : BaseFragment(){

    companion object {
        @JvmStatic
        val instance: SettingFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SettingFragment()
        }
    }

    override fun getResLayout(): Int = R.layout.fragment_setting

    override fun logic() {
        setting_revise.setOnClickListener{
            startActivity(Intent(activity, ReviseActivity::class.java))
        }
    }


}