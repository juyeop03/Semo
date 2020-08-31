package kr.hs.dgsw.stac.semo.widget.`object`

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val USER_UID = "user_uid"

    fun getUserUid(context: Context): String? {
        return getDefaultSharedPreferences(context).getString(USER_UID, null)
    }

    fun setUserUid(context: Context, uid: String) {
        getDefaultSharedPreferences(context).edit().putString(USER_UID, uid).apply()
    }

    private fun getDefaultSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode())
    }
    private fun getDefaultSharedPreferencesName(context: Context): String {
        return context.packageName.toString() + "_preferences"
    }
    private fun getDefaultSharedPreferencesMode(): Int {
        return Context.MODE_PRIVATE
    }
}