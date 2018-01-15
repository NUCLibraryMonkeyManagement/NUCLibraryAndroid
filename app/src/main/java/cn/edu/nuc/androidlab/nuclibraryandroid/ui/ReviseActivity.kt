package cn.edu.nuc.androidlab.nuclibraryandroid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import cn.edu.nuc.androidlab.nuclibraryandroid.MyApp
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.base.BaseActivity
import cn.edu.nuc.androidlab.nuclibraryandroid.data.Network
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_revise.*

/**
 * ReviseActivity
 *
 * Created by MurphySL on 2018/1/11.
 */
class ReviseActivity : BaseActivity(){

    companion object {
        private val TAG = "ReviseActivity"
    }

    override fun getResLayout(): Int = R.layout.activity_revise

    override fun initView() {
        setSupportActionBar(revise_toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.revise_hint)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    override fun logic(savedInstanceState: Bundle?) {
        val psw = revise_psw_wrapper.editText?.text.toString()
        val psw2 = revise_psw2_wrapper.editText?.text.toString()

        revise_btn.setOnClickListener {
            revise(psw, psw2)
        }
    }

    private fun revise(psw : String, psw2 : String){
        if(psw == psw2 && !psw.isEmpty() && !psw2.isEmpty()){
            revise_psw_wrapper.isErrorEnabled = false
            revise_psw2_wrapper.isErrorEnabled = false

            validate(psw)
        }else {
            if(psw.isEmpty())
                revise_psw_wrapper.error = getString(R.string.error_null)
            if(psw2.isEmpty())
                revise_psw2_wrapper.error = getString(R.string.error_null)
            if(psw2 != psw)
                revise_psw2_wrapper.error = getString(R.string.revise_error_psw2_eq)
        }
    }

    private fun validate(psw : String){
        MyApp.instance.user?.let {
            Network.instance.getCommonApi().updateUser(it.studentID, psw)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                            {
                                next ->
                                if(next.code != -1){
                                    startActivity(Intent(ReviseActivity@this, LoginActivity::class.java))
                                    finish()
                                }
                            },
                            {
                                error ->
                                Log.i(TAG, "UPDATE USER FAIL : $error")
                                snackBar(revise_psw2, error.toString())
                            },
                            {
                                Log.i(TAG, "UPDATE USER SUCCESS")
                            }
                    )
        }?:snackBar(revise_psw, "UNKNOWN ERROR")
    }


}