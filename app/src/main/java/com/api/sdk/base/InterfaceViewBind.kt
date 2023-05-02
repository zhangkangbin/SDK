package com.api.sdk.base

import androidx.viewbinding.ViewBinding

interface InterfaceViewBind <T:ViewBinding>{

    fun  getViewBind():  T
}