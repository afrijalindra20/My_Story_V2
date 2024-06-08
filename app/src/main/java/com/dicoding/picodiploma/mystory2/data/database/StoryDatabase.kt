package com.dicoding.picodiploma.mystory2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.picodiploma.mystory2.data.api.respone.ListStoryItem

@Database(
    entities = [ListStoryItem::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}