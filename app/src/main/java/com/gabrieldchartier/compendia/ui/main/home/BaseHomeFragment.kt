package com.gabrieldchartier.compendia.ui.main.home

import android.content.Context
import android.util.Log
import com.gabrieldchartier.compendia.ui.DataStateChangeListener
import dagger.android.support.DaggerFragment

abstract class BaseHomeFragment : DaggerFragment(){

    lateinit var stateChangeListener: DataStateChangeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            stateChangeListener = context as DataStateChangeListener
        }catch(e: ClassCastException){
            Log.e("BaseHomeFragment", "onAttach (line 17)")
        }
    }
}