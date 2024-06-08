package com.dicoding.picodiploma.mystory2

import com.dicoding.picodiploma.mystory2.data.api.respone.ListStoryItem

object DataDummy {
    fun generateDummyStoriesList(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..10) {
            val story = ListStoryItem(
                photoUrl = "https://example.com/photo$i.jpg",
                createdAt = "2023-06-08T10:00:00Z",
                name = "User $i",
                description = "Description $i",
                lon = -122.084,
                id = "story$i",
                lat = 37.421
            )
            items.add(story)
        }
        return items
    }
}