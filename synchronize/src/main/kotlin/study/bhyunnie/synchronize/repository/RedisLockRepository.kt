package study.bhyunnie.synchronize.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisLockRepository(
	private val redisTemplate: RedisTemplate<String, String>
) {
	fun lock(key:Long):Boolean {
		return redisTemplate.opsForValue().setIfAbsent(generateKey(key = key), "lock", Duration.ofMillis(3_000)) ?: false
	}

	fun unlock(key:Long): Boolean {
		return redisTemplate.delete(generateKey(key = key))
	}

	fun generateKey(key: Long):String {
		return key.toString()
	}
}