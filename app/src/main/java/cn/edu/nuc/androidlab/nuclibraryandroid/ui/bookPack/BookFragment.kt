package cn.edu.nuc.androidlab.nuclibraryandroid.ui.bookPack

import cn.edu.nuc.androidlab.nuclibraryandroid.R
import cn.edu.nuc.androidlab.nuclibraryandroid.baseModel.BaseFragment
import cn.edu.nuc.androidlab.nuclibraryandroid.ui.mePack.MeFragment

/**
 * Created by MurphySL on 2018/1/17.
 */
class BookFragment : BaseFragment(){
    override fun getResLayout(): Int = R.layout.fragment_book

    companion object {
        private val TAG = "BookFragment"

        @JvmStatic
        val instance: BookFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BookFragment()
        }
    }

}