package com.csaim.coroutinelabs


import android.util.Log

inline fun <T> logTime(label: String, block: () -> T): T {

    val start = System.currentTimeMillis()
    val result = block()
    val took = System.currentTimeMillis() - start


    Log.d("CoroutineLabs", "$label took $took ms")
    return result

}