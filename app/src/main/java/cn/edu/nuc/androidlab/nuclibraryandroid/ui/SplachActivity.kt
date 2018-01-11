package cn.edu.nuc.androidlab.nuclibraryandroid.ui

import android.content.Intent
import android.os.Bundle
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.base.BaseActivity
import cn.edu.nuc.androidlab.nuclibraryandroid.util.RxSchedulerHelper
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Splash
 *
 * Created by MurphySL on 2018/1/11.
 */
class SplachActivity : BaseActivity(){

    override fun getResLayout(): Int = R.layout.activity_splash

    override fun logic(savedInstanceState: Bundle?) {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(RxSchedulerHelper.io_main())
                .subscribe(
                        {},{},
                        {
                            startActivity(Intent(SplachActivity@this, MainActivity::class.java))
                        }
                )
    }


}