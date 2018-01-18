package cn.edu.nuc.androidlab.nuclibraryandroid.ui.basePack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import cn.edu.nuc.androidlab.nuclibraryandroid.baseModel.BaseActivity
import cn.edu.nuc.androidlab.nuclibraryandroid.MyApp
import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.bean.UserResult
import cn.edu.nuc.androidlab.nuclibraryandroid.data.Network
import cn.edu.nuc.androidlab.nuclibraryandroid.util.SharedPreferencesHelper
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

/**
 * LoginActivity
 *
 * Created by MurphySL on 2018/1/11.
 */
class LoginActivity : BaseActivity(){
    companion object {
        private val TAG : String = "LoginActivity"
    }

    override fun getResLayout(): Int = R.layout.activity_login

    override fun initView() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.login_hint)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    override fun logic(savedInstanceState: Bundle?) {
        login.setOnClickListener {
            val no = login_no.text.toString()
            val psw = login_password.text.toString()

            //login(no, psw)
            startActivity(Intent(LoginActivity@this, MainActivity::class.java))
            finish()
        }
    }

    private fun login(id : String, psw : String){
        if(!id.isEmpty() && !psw.isEmpty()){
            login_psw_wrapper.isErrorEnabled = false
            login_no_wrapper.isErrorEnabled = false
            validate(id.toInt(), psw)
        }else{
            if(id.isEmpty())
                login_no_wrapper.error = getString(R.string.error_null)
            if(psw.isEmpty())
                login_psw_wrapper.error = getString(R.string.error_null)
        }
    }

    private fun validate(id: Int, psw: String) {
        Network.instance.getCommonApi().getUser(Integer.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        {
                            next ->
                            if(next.code != -1 && next.data != null){
                                val idReal = next.data?.studentID
                                val pswReal = next.data?.password

                                if(id != idReal ||  psw != pswReal){
                                    login_psw_wrapper.error = getString(R.string.login_error_psw_no_match)
                                }else{
                                    // 信息正确
                                    val help = SharedPreferencesHelper(LoginActivity@this)
                                    val user = UserResult.User("", psw, id)
                                    help.saveUserInfo(user)
                                    MyApp.instance.user = user
                                    startActivity(Intent(LoginActivity@this, MainActivity::class.java))
                                    finish()
                                }
                            }
                        },
                        {
                            error ->
                            Log.i(TAG, "GET USER FAIL : $error")
                            snackBar(login_no_wrapper, error.toString())
                        },
                        {
                            Log.i(TAG, "GET USER SUCCESS")
                        }
                )
    }

}