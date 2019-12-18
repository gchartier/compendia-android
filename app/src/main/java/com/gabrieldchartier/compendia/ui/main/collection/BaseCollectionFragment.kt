package com.gabrieldchartier.compendia.ui.main.collection

import android.content.Context
import android.util.Log
import com.gabrieldchartier.compendia.ui.DataStateChangeListener
import dagger.android.support.DaggerFragment

abstract class BaseCollectionFragment : DaggerFragment(){

    val TAG: String = "AppDebug"

    lateinit var stateChangeListener: DataStateChangeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            stateChangeListener = context as DataStateChangeListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement DataStateChangeListener" )
        }
    }
}