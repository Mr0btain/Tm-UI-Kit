package org.mr0btain.uikit.files

import kotlin.test.Test
import kotlin.test.assertEquals

class DroppedPathsTest {
    @Test
    fun keepsLocalFilesOnly() {
        val uris = listOf(
            "file:/home/me/Videos/ep%201.mkv",
            "file:///home/me/Videos/Season%202/",
            "https://example.com/clip.mp4",
            "not a uri at all"
        )
        assertEquals(listOf("/home/me/Videos/ep 1.mkv", "/home/me/Videos/Season 2"), droppedPaths(uris))
    }
}
