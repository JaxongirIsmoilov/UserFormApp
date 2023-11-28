package uz.gita.jaxongir.userformapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.jaxongir.userformapp.data.local.room.entity.ComponentEntity
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDatas(entity: FormEntity)


    @Query("select * from formentity where isDraft = :isDraft order by id asc ")
    suspend fun getAllDrafts(isDraft: Boolean = true): List<FormEntity>


    @Query("select * from formentity where isSubmitted = :isSubmitted order by id asc ")
   suspend fun getAllSubmitteds(isSubmitted: Boolean = true): List<FormEntity>


}