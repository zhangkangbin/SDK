package com.api.sdk

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.api.sdk.databinding.ActivityMainBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class MainActivity: AppCompatActivity() {
    // no reflection API is used under the hood
   // private val viewBinding by viewBinding(ActivityMainBinding::bind)\

    // reflection API and ViewBinding.bind are used under the hood
   // private val viewBinding: ProfileBinding by viewBinding()

    private val viewBinding by bingView(ActivityMainBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        viewBinding.editName.setText("0077700")

    }

}

fun <A: ComponentActivity,T : ViewBinding>AppCompatActivity.bingView(vbFactory: (View) -> T): ViewBindingProperty<A,T> {


//    val rootView=layoutInflater.inflate(layoutId,null,false)


  return  ActivityBindingProperty{ activity -> vbFactory(findRootView(activity)) }
}



fun findRootView(activity: Activity): View {
    val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
    checkNotNull(contentView) { "Activity has no content view" }
    return when (contentView.childCount) {
        1 -> contentView.getChildAt(0)
        0 -> error("Content view has no children. Provide a root view explicitly")
        else -> error("More than one child view found in the Activity content view")
    }
}

class ActivityBindingProperty<in R : ComponentActivity, out T : ViewBinding>(
    viewBinder: (R) -> T)
    :LazyViewBindingProperty<R,T>(viewBinder){


}
public open class LazyViewBindingProperty<in R : Any, out T : ViewBinding>(
    protected val viewBinder: (R) -> T
) : ViewBindingProperty<R, T> {

   // constructor(viewBinder: (R) -> T) : this( viewBinder)

    private var viewBinding: Any? = null

    @Suppress("UNCHECKED_CAST")
    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        return viewBinding as? T ?: viewBinder(thisRef).also { viewBinding ->
            this.viewBinding = viewBinding
        }
    }

    @Suppress("UNCHECKED_CAST")
    @MainThread
    @CallSuper
    public override fun clear() {
        val viewBinding = this.viewBinding as T?
        if (viewBinding != null) {
            //onViewDestroyed(viewBinding)
        }
        this.viewBinding = null
    }
}

interface ViewBindingProperty<in R : Any, out T : ViewBinding> : ReadOnlyProperty<R, T> {

    @MainThread
    fun clear()
}
