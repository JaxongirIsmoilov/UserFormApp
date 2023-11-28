package uz.gita.jaxongir.userformapp.data.local.room.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.data.model.Conditions

class Converter {
    private val converter = Gson()

    @TypeConverter
    fun stringConverter(data: List<String>): String = converter.toJson(data)

    @TypeConverter
    fun conditionsConverter(data: List<Conditions>): String = converter.toJson(data)

    @TypeConverter
    fun booleanConverter(data: List<Boolean>): String = converter.toJson(data)

    @TypeConverter
    fun typeConverter(data: ComponentEnum): String = converter.toJson(data)

    @TypeConverter
    fun componentConverter(data: List<ComponentData>): String = converter.toJson(data)

    @TypeConverter
    fun stringConverter(data: String): List<String> =
        converter.fromJson(data, Array<String>::class.java).asList()

    @TypeConverter
    fun conditionsConverter(data: String): List<Conditions> =
        converter.fromJson(data, Array<Conditions>::class.java).asList()

    @TypeConverter
    fun booleanConverter(data: String): List<Boolean> =
        converter.fromJson(data, Array<Boolean>::class.java).asList()

    @TypeConverter
    fun typeConverter(data: String): ComponentEnum =
        converter.fromJson(data, ComponentEnum::class.java)

    @TypeConverter
    fun componentConverter(data: String): List<ComponentData> =
        converter.fromJson(data, Array<ComponentData>::class.java).asList()
}