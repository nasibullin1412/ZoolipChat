package com.homework.coursework.data.frameworks.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homework.coursework.data.frameworks.database.entities.UserEntity
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table WHERE user_id = :id")
    fun getUser(id: Long): Single<UserEntity>

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): Single<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userEntities: List<UserEntity>)
}
