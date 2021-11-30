package com.homework.coursework.data.frameworks.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homework.coursework.data.frameworks.database.entities.AuthEntity
import io.reactivex.Completable

@Dao
interface ApiKeyDao {
    @Query("SELECT * FROM api_key")
    fun getApiKey(): AuthEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApiKey(authEntity: AuthEntity): Completable
}
