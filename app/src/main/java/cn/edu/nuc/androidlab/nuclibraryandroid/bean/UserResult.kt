package cn.edu.nuc.androidlab.nuclibraryandroid.bean

/**
 * User Result
 * Created by MurphySL on 2018/1/15.
 */
data class UserResult(val code : Int, val data : User?, val msg : String){
    data class User(val creditScore : String, val password : String, val studentID : Int)
}