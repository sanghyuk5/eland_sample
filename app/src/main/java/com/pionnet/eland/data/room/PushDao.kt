package com.pionnet.eland.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PushDao {
    @Query("SELECT * FROM push")
    fun getAll(): Flow<List<Push>>

//    @Query("SELECT * FROM push WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<Push>
//
//    @Query("SELECT * FROM push WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): Push


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Push)

//    @Delete
//    fun delete(data: Push): Flow<Push>
}