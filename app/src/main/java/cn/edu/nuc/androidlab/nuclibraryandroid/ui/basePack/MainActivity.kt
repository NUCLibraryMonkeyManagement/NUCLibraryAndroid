package cn.edu.nuc.androidlab.nuclibraryandroid.ui.basePack

import android.os.Bundle
import android.view.KeyEvent
import cn.edu.nuc.androidlab.nuclibraryandroid.baseModel.BaseActivity
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.appointmentPack.AppointFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.bookPack.BookFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.mePack.MeFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.settingPack.SettingFragment
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {

    override fun getResLayout(): Int = R.layout.activity_main

    override fun logic(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            addFragment(MeFragment.instance, R.id.main_content)
        }

        navi.setOnNavigationItemSelectedListener {
            item ->
            when(item.itemId){
                R.id.my ->{
                    replaceFragment(MeFragment.instance, R.id.main_content)
                }
                R.id.appointment ->{
                    replaceFragment(AppointFragment.instance, R.id.main_content)
                }
                R.id.book ->{
                    replaceFragment(BookFragment.instance, R.id.main_content)
                }
                R.id.setting ->{
                    replaceFragment(SettingFragment.instance, R.id.main_content)
                }
            }
            true
        }
    }

    private var exitTime = 0L
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val now = System.currentTimeMillis()

            if (now - exitTime <= 2000) {
                finish()
            } else {
                snackBar(navi, "再次点击退出")
                exitTime = now
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }

}
