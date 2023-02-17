package com.pionnet.eland.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Push::class], version = 1)
abstract class PushDatabase : RoomDatabase() {
    abstract fun pushDao(): PushDao
}