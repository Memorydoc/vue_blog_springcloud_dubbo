package com.kevin.cloud.test;

import com.kevin.cloud.provider.api.DistributedLocker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: vue-blog-backend
 * @description: Lock接口实现类
 * @author: kevin
 * @create: 2020-01-23 21:25
 **/
@Slf4j
@Component
public class RedissonDistributedLocker implements DistributedLocker {

    //RedissonClient已经由配置类生成，这里自动装配即可
    @Autowired
    private RedissonClient redissonClient;


    /**
     * 拿不到lock就不罢休，不然线程就一直block
     * @param lockKey
     * @return
     */
    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }


    /**
     * timeout为加锁时间，单位为秒
     * @param lockKey
     * @param timeout
     * @return
     */
    @Override
    public RLock lock(String lockKey, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout,TimeUnit.SECONDS);
        return lock;
    }


    /**
     * timeout为加锁时间，时间单位由unit确定
     * @param lockKey
     * @param unit
     * @param timeout
     * @return
     */
    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout,unit);
        return lock;
    }


    /**
     * tryLock()，马上返回，拿到lock就返回true，不然返回false。
     * 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);

        try {
            return lock.tryLock(waitTime,leaseTime,unit);
        } catch (InterruptedException e) {
            log.warn("==========<<<< tryLock is exception:",e);
            return false;
        }
    }

    /**
     * 释放锁
     * @param lockKey
     */
    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();

    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

}
