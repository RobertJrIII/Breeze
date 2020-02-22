package com.the3rdwheel.breeze

import androidx.lifecycle.ViewModel
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.authentication.db.entity.Account

class ViewModel(val auth: Auth, val database: AccountDatabase) : ViewModel() {


    fun getGetCurrentUser() {

    }


}