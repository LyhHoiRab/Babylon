package com.rab.babylon.commons.utils;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.ShardedJedis;

import java.lang.reflect.Type;
import java.util.*;

public class RedisUtils{

    private final static String NULL_STRING = "nil";

    private RedisUtils(){

    }

    private static void checkKey(Object key){
        if(key == null){
            throw new IllegalArgumentException("缓存名称不能为空");
        }

        if(key instanceof  String){
            if(StringUtils.isBlank(key.toString())){
                throw new IllegalArgumentException("缓存名称不能为空");
            }
        }

        if(key instanceof Collection){
            Collection collection = (Collection) key;

            if(collection.isEmpty()){
                throw new IllegalArgumentException("缓存名称集合不能为空");
            }
        }
    }

    private static void checkValue(Object value){
        if(value == null){
            throw new IllegalArgumentException("缓存对象不能为空");
        }

        if(value instanceof String){
            if(StringUtils.isBlank(value.toString())){
                throw new IllegalArgumentException("缓存对象不能为空");
            }
        }

        if(value instanceof Collection){
            Collection collection = (Collection) value;

            if(collection.isEmpty()){
                throw new IllegalArgumentException("缓存对象集合不能为空");
            }
        }
    }

    private static void checkField(Object field){
        if(field == null){
            throw new IllegalArgumentException("缓存集合元素名称不能为空");
        }

        if(field instanceof  String){
            if(StringUtils.isBlank(field.toString())){
                throw new IllegalArgumentException("缓存集合元素名称不能为空");
            }
        }

        if(field instanceof Collection){
            Collection collection = (Collection) field;

            if(collection.isEmpty()){
                throw new IllegalArgumentException("缓存集合元素名称集合不能为空");
            }
        }
    }

    private static void checkJedis(ShardedJedis jedis){
        if(jedis == null){
            throw new IllegalArgumentException("Redis链接不能为空");
        }
    }

    private static void checkClass(Class clazz){
        if(clazz == null){
            throw new IllegalArgumentException("缓存对象Class类型不能为空");
        }
    }

    private static void checkType(Type type){
        if(type == null){
            throw new IllegalArgumentException("缓存对象Type类型不能为空");
        }
    }

    private static boolean checkJson(Object json){
        if(json instanceof String){
            return !StringUtils.isBlank(json.toString()) && !NULL_STRING.equalsIgnoreCase(json.toString());
        }

        if(json instanceof Collection){
            Collection collection = (Collection) json;

            return !collection.isEmpty();
        }

        return json != null;
    }

    private static void close(ShardedJedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

    public static <T> T get(ShardedJedis jedis, String key, Class<T> clazz){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkClass(clazz);

            String json = jedis.get(key);

            return checkJson(json) ? ObjectUtils.deserialize(json, clazz) : null;
        }finally{
            close(jedis);
        }
    }

    public static <T> T get(ShardedJedis jedis, String key, Type type){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkType(type);

            String json = jedis.get(key);

            return (T) (checkJson(json) ? ObjectUtils.deserialize(json, type) : null);
        }finally{
            close(jedis);
        }
    }

    public static void save(ShardedJedis jedis, String key, Object value){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(value);

            String json = ObjectUtils.serialize(value);
            jedis.set(key, json);
        }finally{
            close(jedis);
        }
    }

    public static void save(ShardedJedis jedis, String key, Object value, int expire){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(value);

            String json = ObjectUtils.serialize(value);
            jedis.set(key, json);
            if(expire > -1){
                jedis.expire(key, expire);
            }
        }finally{
            close(jedis);
        }
    }

    public static void save(ShardedJedis jedis, String key, Object value, Date expire){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(value);

            String json = ObjectUtils.serialize(value);
            jedis.set(key, json);

            if(expire != null){
                jedis.expireAt(key, expire.getTime());
            }
        }finally{
            close(jedis);
        }
    }

    public static void delete(ShardedJedis jedis, String key){
        try{
            checkJedis(jedis);
            checkKey(key);

            jedis.del(key);
        }finally{
            close(jedis);
        }
    }

    public static void expire(ShardedJedis jedis, String key, int expire){
        try{
            checkJedis(jedis);
            checkKey(key);

            if(expire > -1){
                jedis.expire(key, expire);
            }
        }finally{
            close(jedis);
        }
    }

    public static void expireAt(ShardedJedis jedis, String key, Date expire){
        try{
            checkJedis(jedis);
            checkKey(key);

            if(expire != null){
                jedis.expireAt(key, expire.getTime());
            }
        }finally{
            close(jedis);
        }
    }

    public static boolean exists(ShardedJedis jedis, String key){
        try{
            checkJedis(jedis);
            checkKey(key);

            return jedis.exists(key);
        }finally{
            close(jedis);
        }
    }

    public static <T> T hget(ShardedJedis jedis, String key, String field, Class<T> clazz){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkField(field);
            checkClass(clazz);

            String json = jedis.hget(key, field);

            return checkJson(json) ? ObjectUtils.deserialize(json, clazz) : null;
        }finally{
            close(jedis);
        }
    }

    public static <T> T hget(ShardedJedis jedis, String key, String field, Type type){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkField(field);
            checkType(type);

            String json = jedis.hget(key, field);

            return (T) (checkJson(json) ? ObjectUtils.deserialize(json, type) : null);
        }finally{
            close(jedis);
        }
    }

    public static <T> Map<String, T> hgetAll(ShardedJedis jedis, String key, Class<T> clazz){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkClass(clazz);

            Map<String, String> jsons = jedis.hgetAll(key);

            if(checkJson(jsons)){
                Map<String, T> target = new HashMap<String, T>();

                Iterator<Map.Entry<String, String>> iterator = jsons.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                    String value = entry.getValue();
                    target.put(entry.getKey(), checkJson(value) ? ObjectUtils.deserialize(value, clazz) : null);
                }

                return target;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> Map<String, T> hgetAll(ShardedJedis jedis, String key, Type type){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkType(type);

            Map<String, String> jsons = jedis.hgetAll(key);

            if(checkJson(jsons)){
                Map<String, T> target = new HashMap<String, T>();

                Iterator<Map.Entry<String, String>> iterator = jsons.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                    String value = entry.getValue();
                    target.put(entry.getKey(), (T) (checkJson(value) ? ObjectUtils.deserialize(value, type) : null));
                }

                return target;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static Set<String> hkeys(ShardedJedis jedis, String key){
        try{
            checkJedis(jedis);
            checkKey(key);

            return jedis.hkeys(key);
        }finally{
            close(jedis);
        }
    }

    public static <T> List<T> hvalues(ShardedJedis jedis, String key, Class<T> clazz){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkClass(clazz);

            List<String> jsons = jedis.hvals(key);

            if(checkJson(jsons)){
                List<T> target = new ArrayList<T>();

                Iterator<String> iterator = jsons.iterator();
                while(iterator.hasNext()){
                    String json = iterator.next();
                    target.add(checkJson(json) ? ObjectUtils.deserialize(json, clazz) : null);
                }

                return target;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> List<T> hvalues(ShardedJedis jedis, String key, Type type){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkType(type);

            List<String> jsons = jedis.hvals(key);

            if(checkJson(jsons)){
                List<T> target = new ArrayList<T>();

                Iterator<String> iterator = jsons.iterator();
                while(iterator.hasNext()){
                    String json = iterator.next();
                    target.add((T) (checkJson(json) ? ObjectUtils.deserialize(json, type) : null));
                }

                return target;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static long hlen(ShardedJedis jedis, String key){
        try{
            checkJedis(jedis);
            checkKey(key);

            return jedis.hlen(key);
        }finally{
            close(jedis);
        }
    }

    public static <T> void hmset(ShardedJedis jedis, String key, Map<String, T> values){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(values);

            Map<String, String> target = new HashMap<String, String>();

            Iterator<Map.Entry<String, T>> iterator = values.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, T> entry = iterator.next();
                Object value = entry.getValue();
                target.put(entry.getKey(), checkJson(value) ? ObjectUtils.serialize(value) : null);
            }

            jedis.hmset(key, target);
        }finally{
            close(jedis);
        }
    }

    public static void hset(ShardedJedis jedis, String key, String field, Object value){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkField(field);
            checkValue(value);

            jedis.hset(key, field, ObjectUtils.serialize(value));
        }finally{
            close(jedis);
        }
    }

    public static void hsetnx(ShardedJedis jedis, String key, String field, Object value){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkField(field);
            checkValue(value);

            jedis.hsetnx(key, field, ObjectUtils.serialize(value));
        }finally{
            close(jedis);
        }
    }

    public static void hdel(ShardedJedis jedis, String key, List<String> fields){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkField(fields);

            jedis.hdel(key, (String[]) fields.toArray(new String[fields.size()]));
        }finally{
            jedis.close();
        }
    }

    public static void hdel(ShardedJedis jedis, String key, String field){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkField(field);

            jedis.hdel(key, field);
        }finally{
            jedis.close();
        }
    }

    public static boolean hexists(ShardedJedis jedis, String key, String field){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkField(field);

            return jedis.hexists(key, field);
        }finally{
            close(jedis);
        }
    }

    public static <T> T lindex(ShardedJedis jedis, String key, long index, Class<T> clazz){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkClass(clazz);

            if(index > -1){
                String json = jedis.lindex(key, index);

                return checkJson(json) ? ObjectUtils.deserialize(json, clazz) : null;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> T lindex(ShardedJedis jedis, String key, long index, Type type){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkType(type);

            if(index > -1){
                String json = jedis.lindex(key, index);

                return (T) (checkJson(json) ? ObjectUtils.deserialize(json, type) : null);
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> List<T> lrange(ShardedJedis jedis, String key, long start, long end, Class<T> clazz){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkClass(clazz);

            if(start > -1 && end > -1){
                List<String> jsons = jedis.lrange(key, start, end);

                if(checkJson(jsons)){
                    List<T> target = new ArrayList<T>();

                    Iterator<String> iterator = jsons.iterator();
                    while(iterator.hasNext()){
                        String json = iterator.next();
                        target.add(checkJson(json) ? ObjectUtils.deserialize(json, clazz) : null);
                    }

                    return target;
                }
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> List<T> lrange(ShardedJedis jedis, String key, long start, long end, Type type){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkType(type);

            if(start > -1 && end > -1){
                List<String> jsons = jedis.lrange(key, start, end);

                if(checkJson(jsons)){
                    List<T> target = new ArrayList<T>();

                    Iterator<String> iterator = jsons.iterator();
                    while(iterator.hasNext()){
                        String json = iterator.next();
                        target.add((T) (checkJson(json) ? ObjectUtils.deserialize(json, type) : null));
                    }

                    return target;
                }
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> List<T> lall(ShardedJedis jedis, String key, Class<T> clazz){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkClass(clazz);

            List<String> jsons = jedis.lrange(key, 0, -1);

            if(checkJson(jsons)){
                List<T> target = new ArrayList<T>();

                Iterator<String> iterator = jsons.iterator();
                while(iterator.hasNext()){
                    String json = iterator.next();
                    target.add(checkJson(json) ? ObjectUtils.deserialize(json, clazz) : null);
                }

                return target;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> List<T> lall(ShardedJedis jedis, String key, Type type){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkType(type);

            List<String> jsons = jedis.lrange(key, 0, -1);

            if(checkJson(jsons)){
                List<T> target = new ArrayList<T>();

                Iterator<String> iterator = jsons.iterator();
                while(iterator.hasNext()){
                    String json = iterator.next();
                    target.add((T) (checkJson(json) ? ObjectUtils.deserialize(json, type) : null));
                }

                return target;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static void lset(ShardedJedis jedis, String key, long index, Object value){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(value);

            jedis.lset(key, index, ObjectUtils.serialize(value));
        }finally{
            close(jedis);
        }
    }

    public static void linsert(ShardedJedis jedis, String key, BinaryClient.LIST_POSITION position, Object exist, Object insert){
        try{
            checkKey(jedis);
            checkKey(key);
            checkValue(exist);
            checkValue(insert);

            jedis.linsert(key, position, ObjectUtils.serialize(exist), ObjectUtils.serialize(insert));
        }finally{
            close(jedis);
        }
    }

    public static long llen(ShardedJedis jedis, String key){
        try{
            checkJedis(jedis);
            checkKey(key);

            return jedis.llen(key);
        }finally{
            close(jedis);
        }
    }

    public static <T> void lpush(ShardedJedis jedis, String key, List<T> values){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(values);

            List<String> jsons = new ArrayList<String>();

            Iterator<T> iterator = values.iterator();
            while(iterator.hasNext()){
                jsons.add(ObjectUtils.serialize(iterator.next()));
            }

            jedis.lpush(key, jsons.toArray(new String[jsons.size()]));
        }finally{
            close(jedis);
        }
    }

    public static <T> void lpushx(ShardedJedis jedis, String key, List<T> values){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(values);

            List<String> jsons = new ArrayList<String>();

            Iterator<T> iterator = values.iterator();
            while(iterator.hasNext()){
                jsons.add(ObjectUtils.serialize(iterator.next()));
            }

            jedis.lpushx(key, jsons.toArray(new String[jsons.size()]));
        }finally{
            close(jedis);
        }
    }

    public static <T> void rpush(ShardedJedis jedis, String key, List<T> values){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(values);

            List<String> jsons = new ArrayList<String>();

            Iterator<T> iterator = values.iterator();
            while(iterator.hasNext()){
                jsons.add(ObjectUtils.serialize(iterator.next()));
            }

            jedis.rpush(key, jsons.toArray(new String[jsons.size()]));
        }finally{
            close(jedis);
        }
    }

    public static <T> void rpushx(ShardedJedis jedis, String key, List<T> values){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(values);

            List<String> jsons = new ArrayList<String>();

            Iterator<T> iterator = values.iterator();
            while(iterator.hasNext()){
                jsons.add(ObjectUtils.serialize(iterator.next()));
            }

            jedis.rpushx(key, jsons.toArray(new String[jsons.size()]));
        }finally{
            close(jedis);
        }
    }

    public static void ltrim(ShardedJedis jedis, String key, long start, long end){
        try{
            checkJedis(jedis);
            checkKey(key);

            if(start > -1 && end > -1){
                jedis.ltrim(key, start, end);
            }
        }finally{
            close(jedis);
        }
    }

    public static void lrem(ShardedJedis jedis, String key, long count, Object value){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(value);

            jedis.lrem(key, count, ObjectUtils.serialize(value));
        }finally{
            close(jedis);
        }
    }

    public static <T> Set<T> smembers(ShardedJedis jedis, String key, Class<T> clazz){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkClass(clazz);

            Set<String> jsons = jedis.smembers(key);

            if(checkJson(jsons)){
                Set<T> target = new HashSet<T>();

                Iterator<String> iterator = jsons.iterator();
                while(iterator.hasNext()){
                    String json = iterator.next();
                    target.add(checkJson(json) ? ObjectUtils.deserialize(json, clazz) : null);
                }

                return target;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> Set<T> smembers(ShardedJedis jedis, String key, Type type){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkType(type);

            Set<String> jsons = jedis.smembers(key);

            if(checkJson(jsons)){
                Set<T> target = new HashSet<T>();

                Iterator<String> iterator = jsons.iterator();
                while(iterator.hasNext()){
                    String json = iterator.next();
                    target.add((T) (checkJson(json) ? ObjectUtils.deserialize(json, type) : null));
                }

                return target;
            }

            return null;
        }finally{
            close(jedis);
        }
    }

    public static <T> void sadd(ShardedJedis jedis, String key, List<T> values){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(values);

            List<String> jsons = new ArrayList<String>();

            Iterator<T> iterator = values.iterator();
            while(iterator.hasNext()){
                jsons.add(ObjectUtils.serialize(iterator.next()));
            }

            jedis.sadd(key, jsons.toArray(new String[jsons.size()]));
        }finally{
            close(jedis);
        }
    }

    public static <T> void srem(ShardedJedis jedis, String key, List<T> values){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(values);

            List<String> jsons = new ArrayList<String>();

            Iterator<T> iterator = values.iterator();
            while(iterator.hasNext()){
                jsons.add(ObjectUtils.serialize(iterator.next()));
            }

            jedis.srem(key, jsons.toArray(new String[jsons.size()]));
        }finally{
            close(jedis);
        }
    }

    public static void srem(ShardedJedis jedis, String key, Object value){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(value);

            jedis.srem(key, ObjectUtils.serialize(value));
        }finally{
            close(jedis);
        }
    }

    public static long scard(ShardedJedis jedis, String key){
        try{
            checkJedis(jedis);
            checkKey(key);

            return jedis.scard(key);
        }finally{
            close(jedis);
        }
    }

    public static boolean sismember(ShardedJedis jedis, String key, Object value){
        try{
            checkJedis(jedis);
            checkKey(key);
            checkValue(value);

            return jedis.sismember(key, ObjectUtils.serialize(value));
        }finally{
            close(jedis);
        }
    }
}
