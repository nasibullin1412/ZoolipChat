package com.homework.coursework.data.frameworks.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homework.coursework.data.frameworks.database.entities.CurrentProfileEntity
import io.reactivex.Single

@Dao
interface CurrentProfileDao {

    @Query("SELECT * FROM current_profile")
    fun getCurrentProfile(): Single<CurrentProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currentProfileEntity: CurrentProfileEntity)

    @Query("SELECT user_id FROM current_profile")
    fun getCurrentUserId(): Single<Int>
}
