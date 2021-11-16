package com.homework.coursework.data.frameworks.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.homework.coursework.data.frameworks.database.crossref.MessageToUserCrossRef

@Dao
interface MessageToUserCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(crossRef: MessageToUserCrossRef)
}
