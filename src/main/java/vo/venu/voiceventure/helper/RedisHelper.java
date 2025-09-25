package vo.venu.voiceventure.helper;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vo.venu.voiceventure.util.JsonUtils;

import java.time.Duration;
import java.lang.Iterable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RedisHelper {

    private final RedissonClient redissonClient;

    private final Codec codec = new JsonJacksonCodec();

    public <T> void saveData(String key, T value) {
        RBucket<T> bucket = redissonClient.getBucket(key, codec);
        bucket.set(value);
    }

    public <T> void saveData(String key, T value, Long ttl) {
        RBucket<T> bucket = redissonClient.getBucket(key, codec);
        bucket.set(value, Duration.ofSeconds(ttl));
    }

    public <T> boolean saveDataIfAbsent(String key, T value, Long ttl) {
        RBucket<T> bucket = redissonClient.getBucket(key, codec);
        return bucket.setIfAbsent(value, Duration.ofSeconds(ttl));
    }

    public <T> T getData(String key, Class<T> type) {
        RBucket<T> bucket = redissonClient.getBucket(key, codec);
        return bucket.get();
    }

    public String getData(String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    public <T> void deleteData(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key, codec);
        bucket.delete();
    }

    public RMapCache<String, String> getMapCache(String key) {
        return redissonClient.getMapCache(key);
    }

    public <T> List<T> getAllDataPattern(String pattern, Class<T> type) {
        Iterable<String> keys = redissonClient.getKeys().getKeysByPattern(pattern);
        return StreamSupport.stream(keys.spliterator(), false)
                .map(key -> {
                    String jsonData = getData(key);
                    return JsonUtils.fromJson(jsonData, type);
                })
                .filter(value -> !ObjectUtils.isEmpty(value))
                .collect(Collectors.toList());
    }

}
