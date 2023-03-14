package homework;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SongCacheImplTest {
    @Test
    public void cacheIsWorking() {
        SongCache cache = new SongCacheImpl();

        cache.recordSongPlays("ID-1", 3);
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 5);

        assertEquals(4, cache.getPlaysForSong("ID-1"));
        assertEquals(-1, cache.getPlaysForSong("ID-9"));
        assertTrue(cache.getTopNSongsPlayed(2).containsAll(List.of("ID-3", "ID-1")));
        assertTrue(cache.getTopNSongsPlayed(0).isEmpty());
    }
}
