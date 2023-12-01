package uz.gita.jaxongir.userformapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDatas(entity: FormEntity)


    @Query("select * from formentity where isDraft = :isDraft AND userId=:userId order by id asc ")
     fun getAllDrafts(isDraft: Boolean = true, userId: String): Flow<List<FormEntity>>


    @Query("select * from formentity where isSubmitted = :isSubmitted AND userId=:userId order by id asc ")
     fun getAllSubmitteds(isSubmitted: Boolean = true, userId: String):Flow<List<FormEntity>>


}