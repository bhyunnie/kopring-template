package study.bhyunnie.synchronize.facade

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import study.bhyunnie.synchronize.service.StockService
import java.util.concurrent.TimeUnit

@Component
class RedissonLockStockFacade(
	private val redissonClient: RedissonClient, private val stockService: StockService
) {
	fun decrease(id:Long, quantity:Long) {
		val lock = redissonClient.getLock(id.toString())
		try {
			val available:Boolean = lock.tryLock(10, 1, TimeUnit.SECONDS)
			if (!available) {
				println("lock 획득 실패")
				return
			}
			stockService.decrease(id=id,quantity=quantity)
		} catch (e: InterruptedException) {
			throw RuntimeException(e)
		} finally {
			lock.unlock()
		}
	}
}