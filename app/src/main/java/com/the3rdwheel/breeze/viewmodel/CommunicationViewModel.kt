package com.the3rdwheel.breeze.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommunicationViewModel : ViewModel() {

    private val postFragmentReselected = MutableLiveData<Boolean>()


    fun setPostFragmentReselected(selected: Boolean) {
        postFragmentReselected.postValue(selected)
    }

    fun getPostFragmentReselected(): LiveData<Boolean> {
        return postFragmentReselected
    }

}