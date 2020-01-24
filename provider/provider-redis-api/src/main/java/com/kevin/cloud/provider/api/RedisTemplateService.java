package com.kevin.cloud.provider.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.sql.Time;
import java.util.Map;
import java.util.Set;

public interface RedisTemplateService {
    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     *
     * @param key
     */
    public  void del(String... key);

    /**
     * 批量删除<br>
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     *
     * @param pattern
     */
    public void batchDel(String... pattern);

    /**
     * 取得缓存（int型）
     *
     * @param key
     * @return
     */
    public Integer getInt(String key);

    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @return
     */
    public String getStr(String key);

    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @return
     */
    public String getStr(String key, boolean retain);

    /**
     * 获取缓存<br>
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @return
     */
    public Object getObj(String key);

    /**
     * 获取缓存<br>
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @param retain 是否保留
     * @return
     */
    public Object getObj(String key, boolean retain);

    /**
     * 获取缓存<br>
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) throws Exception;

    /**
     * 获取缓存json对象<br>
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */

    public <T> T getJson(String key, Class<T> clazz) throws IOException;

    /**
     * 将value对象写入缓存
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void set(String key, Object value, long time);

    /**
     * 将value对象以JSON格式写入缓存
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void setJson(String key, Object value, long time) throws JsonProcessingException;

    /**
     * 更新key对象field的值
     *
     * @param key   缓存key
     * @param field 缓存对象field
     * @param value 缓存对象field值
     */
    public void setJsonField(String key, String field, String value);


    /**
     * 递减操作
     *
     * @param key
     * @param by
     * @return
     */
    public double decr(String key, double by);

    /**
     * 递增操作
     *
     * @param key
     * @param by
     * @return
     */
    public double incr(String key, double by);

    /**
     * 递增操作
     * @param key
     * @return
     */
    public  long incr(String key);
    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void setDouble(String key, double value, long time);

    public  double getDouble(String key) ;
    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public void setInt(String key, int value, long time);

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     * @param time 失效时间(秒)
     */
    public <T> void setMap(String key, Map<String, T> map, long time);

    /**
     * 将map写入缓存
     *
     * @param key
     * @param obj
     * @param time 失效时间(秒)
     */
    @SuppressWarnings("unchecked")
    public <T> void setMap(String key, T obj, long time) throws Exception;


    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key
     * @param map
     */
    public <T> void addMap(String key, Map<String, T> map);

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    public void addMap(String key, String field, String value);

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param obj   对象
     */
    public <T> void addMap(String key, String field, T obj);

    /**
     * 获取map缓存
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> Map<String, T> mget(String key, Class<T> clazz);

    /**
     * 获取map缓存
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getMap(String key, Class<T> clazz);

    /**
     * 获取map缓存中的某个对象
     *
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field, Class<T> clazz);

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     * @author lh
     * @date 2016年8月10日
     */
    public void delMapField(String key, String... field);

    /**
     * 指定缓存的失效时间
     *
     * @param key  缓存KEY
     * @param time 失效时间(秒)
     * @author FangJun
     * @date 2016年8月14日
     */
    public void expire(String key, long time);

    /**
     * 添加set
     *
     * @param key
     * @param value
     */
    public void sadd(String key, String... value);

    /**
     * 删除set集合中的对象
     *
     * @param key
     * @param value
     */
    public void srem(String key, String... value);

    /**
     * set重命名
     *
     * @param oldkey
     * @param newkey
     */
    public void srename(String oldkey, String newkey);

    /**
     * 短信缓存
     *
     * @param key
     * @param value
     * @param time
     * @author fxl
     * @date 2016年9月11日
     */
    public void setIntForPhone(String key, Object value, int time) throws Exception;

    /**
     * 模糊查询keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern);

    /**
     * 判断集合是否存在
     *
     * @param key
     * @param obj
     * @return
     */
    public Boolean isMember(String key, Object obj);

}
