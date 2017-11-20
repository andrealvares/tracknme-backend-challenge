package br.com.tracknme.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Cleberson on 18/11/2017.
 */
@Repository
public class BasicRepository<T> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public T retrieve(String key, long id) {
        validateKey(key);
        return (T) redisTemplate.opsForValue().get(key + id);
    }

    public List<T> retrieveAll(String key) {
        validateKey(key);
        Set ids = getSortedSetRange(key, 0, -1);
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        List<T> results = new ArrayList<>(ids.size());
        redisTemplate.executePipelined(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection) {
                ids.forEach(id -> results.add((T) redisTemplate.opsForValue().get(key + id)));
                return null;
            }
        });
        return results;
    }

    public void create(String key, T value, long id) {
        validateArguments(key, value, id);
        //Similar to transaction context
        redisTemplate.executePipelined(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) {
                operations.multi();
                redisTemplate.opsForValue().set(key + id, value);
                redisTemplate.opsForZSet().add(key, id, id);
                return operations.exec();
            }
        });
    }

    public T update(String key, T value, long id) {
        validateArguments(key, value, id);
        redisTemplate.opsForValue().set(key + id, value);
        return retrieve(key, id);
    }

    public void delete(String key, long id) {
        validateArguments(key, id);
        redisTemplate.executePipelined(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) {
                operations.multi();
                redisTemplate.delete(key + id);
                redisTemplate.opsForZSet().remove(key, id);
                return operations.exec();
            }
        });
    }

    public Set getSortedSetRange(String key, int rangeStart, int rangeEnd) {
        validateKey(key);
        return redisTemplate.opsForZSet().range(key, rangeStart, rangeEnd);
    }

    public void validateArguments(String key, long id) {
        if (key == null || id <= 0) throw new IllegalArgumentException("Key Value or Id invalid");
        validateKey(key);
    }

    public void validateArguments(String key, T value, long id) {
        if (key == null || value == null || id <= 0) throw new IllegalArgumentException("Key Value or Id invalid");
        validateKey(key);
    }

    public void validateKey(String key) {
        if (key == null) throw new IllegalArgumentException("Key cant be null");
        if (!key.contains(":")) throw new IllegalArgumentException("Illegal format of key, should contain : ");
    }

    /**
     * Used for specific purpose
     *
     * @return RedisTemplate
     */
    public RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

}
