package com.homework.coursework.data.frameworks.database.dao

import androidx.room.*
import com.homework.coursework.data.frameworks.database.entities.CurrentProfileEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CurrentProfileDao {

    @Query("SELECT * FROM current_profile")
    fun getCurrentProfile(): Single<CurrentProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currentProfileEntity: CurrentProfileEntity)

    @Query("SELECT user_id FROM current_profile")
    fun getCurrentUserId(): Single<Int>

    @Query("DELETE FROM current_profile")
    fun delete(): Completable
}
