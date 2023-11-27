package uz.gita.jaxongir.userformapp.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converter {
    private val converter = Gson()

    @TypeConverter
    fun booleanConverter(data: Boolean): String = converter.toJson(data)

}