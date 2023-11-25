package uz.gita.jaxongir.userformapp.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class MyPref @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val user_id: String = "USER_ID"
    private val isLogin:String = "LOGIN"
    fun saveId(id: String) = sharedPreferences.edit().putString(user_id, id).apply()

    fun getId(): String = sharedPreferences.getString(user_id, "") ?: ""

    fun setLogin(bool:Boolean) = sharedPreferences.edit().putBoolean(isLogin, bool).apply()

    fun isLogin():Boolean = sharedPreferences.getBoolean(isLogin, false) ?: false

    fun saveUserName(name:String) = sharedPreferences.edit().putString("NAME", name).apply()

    fun getUserName():String = sharedPreferences.getString("NAME", "User") ?: ""

    fun clearData(){
        sharedPreferences.edit().clear().apply()
    }
}