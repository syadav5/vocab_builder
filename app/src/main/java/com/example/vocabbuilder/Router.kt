package com.example.vocabbuilder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

object Router {
    var params: MutableMap<String, Any> = HashMap<String, Any>()

    fun addQueryParam(key: String, value: Any) {
        params[key] = value
    }

    fun <T> navigateTo(context: Context, clas: Class<T>) {
        val bundle = Bundle()
        val intent = Intent(context, clas)

        if (!params.isEmpty()) {
            params.onEach { entry ->
                if (entry.value is Parcelable) {
                    bundle.putParcelable(entry.key, entry.value as Parcelable)
                } else if (entry.value is String) {
                    bundle.putString(entry.key, entry.value as String)
                }
            }
            intent.putExtras(bundle)
        }
        context.startActivity(intent)
        params.clear()
    }
}