package cn.edu.nuc.androidlab.nuclibraryandroid.util

import android.content.Context
import cn.edu.nuc.androidlab.nuclibraryandroid.bean.UserResult

/**
 * SharedPreferencesHelper
 *
 * Created by MurphySL on 2018/1/15.
 */
class SharedPreferencesHelper(private val context : Context){

    companion object {
        private val SHARED_NAME : String = "NUC_LIBRARY"
        private val STU_NAME : String = "no"
        private val STU_PSW : String = "psw"
    }

    fun saveUserInfo(user : UserResult.User){
        val sp = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        sp.edit().putInt(STU_NAME, user.studentID).apply()
        sp.edit().putString(STU_PSW, user.password).apply()
    }

    fun getUserInfo() : UserResult.User?{
        val sp = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        val id = sp.getInt(STU_NAME, -1)
        val psw = sp.getString(STU_PSW, "")
        if(id == -1 || psw.isEmpty()){
            return null
        }
        return UserResult.User("", psw, id)
    }

    fun clear() {
        val sp = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        sp.edit().clear().apply()
    }

}