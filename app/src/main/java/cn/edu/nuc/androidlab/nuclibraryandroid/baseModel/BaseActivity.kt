package cn.edu.nuc.androidlab.nuclibraryandroid.baseModel


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View

/**
 * BaseActivity
 *
 * Created by MurphySL on 2018/1/11.
 */
abstract class BaseActivity : AppCompatActivity(){

    private lateinit var fragmentManager : FragmentManager

    abstract fun getResLayout() :Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getResLayout())

        fragmentManager = supportFragmentManager
        initView()
        logic(savedInstanceState)
    }

    open protected fun logic(savedInstanceState: Bundle?) {}

    open protected fun initView() {}

    protected fun snackBar(v : View, content : String) = Snackbar.make(v, content, Snackbar.LENGTH_SHORT).show()

    protected fun getFragmentNum() = fragmentManager.backStackEntryCount

    protected fun addFragment(fragment: Fragment, frameId: Int){
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
        //transaction.addToBackStack(fragment.javaClass.simpleName)
        //transaction.commitAllowingStateLoss()
    }

    protected fun replaceFragment(fragment : Fragment, frameId : Int){
        Log.i("TEST", "replace")
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.commitAllowingStateLoss()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(getFragmentNum() != 0){
                fragmentManager.popBackStack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }


}