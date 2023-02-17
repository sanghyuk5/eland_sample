package com.pionnet.eland.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Push(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "link") val link: String?,
    @ColumnInfo(name = "isRead") val isRead: Boolean = false
)