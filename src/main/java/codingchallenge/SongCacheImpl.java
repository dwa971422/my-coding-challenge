package codingchallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class SongCacheImpl implements SongCache {
    private ConcurrentHashMap<String, Integer> songPlaysMapping;

    public SongCacheImpl() {
        songPlaysMapping = new ConcurrentHashMap<>();
    }

    @Override
    public void recordSongPlays(String songId, int numPlays) {
        songPlaysMapping.put(songId, songPlaysMapping.getOrDefault(songId, 0) + numPlays);
    }

    @Override
    public int getPlaysForSong(String songId) {
        return songPlaysMapping.getOrDefault(songId, -1);
    }

    @Override
    public List<String> getTopNSongsPlayed(int n) {
        List<String> result = new ArrayList<>();

        if (n == 0) {
            return result;
        }

        Queue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(
                (entryA, entryB) -> Integer.compare(entryA.getValue(), entryB.getValue()));

        songPlaysMapping.forEachEntry(Integer.MAX_VALUE, (entry) -> {
            minHeap.add(entry);
            if (minHeap.size() > n) {
                minHeap.poll();
            }
        });

        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().getKey());
        }

        return result;
    }
}
