package com.api.sdk.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity< T :ViewBinding> : AppCompatActivity() {
    private lateinit var binding:ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBind()

        val view = binding.root;
        initView(binding as T,savedInstanceState)
        setContentView(view)

    }
   abstract fun  getViewBind():  T
   abstract fun initView(viewBinding: T,savedInstanceState: Bundle?)

}