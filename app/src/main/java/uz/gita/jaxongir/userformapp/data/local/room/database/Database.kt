package uz.gita.jaxongir.userformapp.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.jaxongir.userformapp.data.local.room.dao.Dao
import uz.gita.jaxongir.userformapp.data.local.room.entity.ComponentEntity
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormData

@Database([ComponentEntity::class, FormData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class Database : RoomDatabase() {
    abstract fun getDao(): Dao
}