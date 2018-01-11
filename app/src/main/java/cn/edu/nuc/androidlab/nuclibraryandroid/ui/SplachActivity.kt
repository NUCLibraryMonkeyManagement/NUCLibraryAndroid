package cn.edu.nuc.androidlab.nuclibraryandroid.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Splash
 *
 * Created by MurphySL on 2018/1/11.
 */
class SplachActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(MainActivity@this, SplachActivity::class.java))
    }
}