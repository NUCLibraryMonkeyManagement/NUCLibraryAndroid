package cn.edu.nuc.androidlab.nuclibraryandroid.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.base.BaseActivity
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.appointmentPack.AppointFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.mePack.MeFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.settingPack.SettingFragment

import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {

    override fun getResLayout(): Int = R.layout.activity_main

/*    override fun initView() {
        setSupportActionBar(toolbar)
    }*/

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
