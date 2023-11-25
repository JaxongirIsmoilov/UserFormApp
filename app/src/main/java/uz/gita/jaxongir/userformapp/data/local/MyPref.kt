package uz.gita.jaxongir.userformapp.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class MyPref @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val user_id: String = "USER_ID"
    fun saveId(id: String) = sharedPreferences.edit().putString(user_id, id).apply()

    fun getId(): String = sharedPreferences.getString(user_id, "") ?: ""
}