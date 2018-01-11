package cn.edu.nuc.androidlab.nuclibraryandroid.base


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * BaseFragment
 *
 * Created by MurphySL on 2018/1/11.
 */
abstract class BaseFragment : Fragment() {

    abstract protected fun getResLayout() : Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(getResLayout(), container, false)
        init(root, savedInstanceState)
        logic()
        return root
    }

    protected fun snackBar(view : View, content : String) = Snackbar.make(view, content, Snackbar.LENGTH_SHORT).show()

    protected fun logic() {}

    protected fun init(root: View?, savedInstanceState: Bundle?) {}

}