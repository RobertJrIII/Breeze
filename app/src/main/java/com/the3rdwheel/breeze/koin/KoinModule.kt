package com.the3rdwheel.breeze.koin

import com.the3rdwheel.breeze.ViewModel
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.authentication.network.ConnectivityInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val modules = module {

    single { ConnectivityInterceptor(androidContext()) }

    single { Auth.invoke(get()) }
    single { AccountDatabase.invoke(androidContext()) }


    viewModel { ViewModel(get(), get()) }
}

