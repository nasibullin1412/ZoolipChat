package com.homework.coursework.data.frameworks.database.dao

import androidx.room.*
import com.homework.coursework.data.frameworks.database.entities.AuthEntity
import io.reactivex.Completable

@Dao
interface AuthDao {
    @Query("SELECT * FROM auth")
    fun getApiKey(): AuthEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApiKey(authEntity: AuthEntity): Completable

    @Query("DELETE FROM auth")
    fun delete(): Completable
}
