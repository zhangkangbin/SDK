package com.api.sdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.api.sdk.base.BaseActivity
import com.api.sdk.base.InterfaceViewBind
import com.api.sdk.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun initView(viewBinding: ActivityMainBinding,savedInstanceState: Bundle?) {
        viewBinding.editName.setText("editName")

    }

    override fun getViewBind(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

}

