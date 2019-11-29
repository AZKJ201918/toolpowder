package com.azkj.toolpowder.common.cache;


import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class CommonCacheUtil {


	private static final String TOKEN_PREFIX = "token.";

	private static final String USER_PREFIX = "user.";

	private Logger logger= LoggerFactory.getLogger(CollectionUtils.class);
	@Autowired
	private JedisPoolWarpper jedisPoolWrapper;


	public void cache(String key, String value) {
		try {
			JedisPool pool = jedisPoolWrapper.getJedisPool();
			if (pool != null) {
				try (Jedis Jedis = pool.getResource()) {
					Jedis.select(0);
					Jedis.set(key, value);
				}
			}
		} catch (Exception e) {
			logger.error("Fail to cache value", e);
		}
	}


	public String getCacheValue(String key) {
		String value = null;
		try {
			JedisPool pool = jedisPoolWrapper.getJedisPool();
			if (pool != null) {
				try (Jedis Jedis = pool.getResource()) {
					Jedis.select(0);
					value = Jedis.get(key);
				}
			}
		} catch (Exception e) {
			logger.error("Fail to get cached value", e);
		}
		return value;
	}


	public long cacheNxExpire(String key, String value, int expiry) {
		long result = 0;
		try {
			JedisPool pool = jedisPoolWrapper.getJedisPool();
			if (pool != null) {
				try (Jedis jedis = pool.getResource()) {
					jedis.select(0);
					result = jedis.setnx(key, value);
					jedis.expire(key, expiry);
				}
			}
		} catch (Exception e) {
			logger.error("Fail to cacheNx value", e);
		}
		
		return result;
	}


	public void delKey(String key) {
		JedisPool pool = jedisPoolWrapper.getJedisPool();
		if (pool != null) {

			try (Jedis jedis = pool.getResource()) {
				jedis.select(0);
				try {
					jedis.del(key);
				} catch (Exception e) {
					logger.error("Fail to remove key from redis", e);
				}
			}
		}
	}


//
//	public void putTokenWhenLogin(UserElement ue) {
//		JedisPool pool = jedisPoolWrapper.getJedisPool();
//		if (pool != null) {
//
//			try (Jedis jedis = pool.getResource()) {
//				jedis.select(0);
//				Transaction trans = jedis.multi();
//				try {
//					trans.del(TOKEN_PREFIX + ue.getToken());
//					trans.hmset(TOKEN_PREFIX + ue.getToken(), ue.toMap());
//					trans.expire(TOKEN_PREFIX + ue.getToken(), 2592000);
//					trans.sadd(USER_PREFIX + ue.getUserId(), ue.getToken());
//					trans.exec();
//				} catch (Exception e) {
//					trans.discard();
//					logger.error("Fail to cache token to redis", e);
//				}
//			}
//		}
//	}
//
//
//
//
//
//	/**
//	 *  根据token取缓存的用户信息
//	 * @param token
//	 * @return
//	 * @throws
//	 */
//	public UserElement getUserByToken(String token) throws SuperMarketException {
//		UserElement ue = null;
//
//		JedisPool pool = jedisPoolWrapper.getJedisPool();
//		if (pool != null) {
//			try (Jedis jedis = pool.getResource()) {
//				jedis.select(0);
//				try {
//					Map<String, String> map = jedis.hgetAll(TOKEN_PREFIX + token);
//					if (map != null && !map.isEmpty()) {
//						ue = UserElement.fromMap(map);
//					} else {
//						logger.warn("Fail to find cached element for token {}", token);
//					}
//				} catch (Exception e) {
//					logger.error("Fail to get token from redis", e);
//					throw new SuperMarketException("Fail to get token content");
//				}
//			}
//		}
//
//		return ue;
//	}
}
