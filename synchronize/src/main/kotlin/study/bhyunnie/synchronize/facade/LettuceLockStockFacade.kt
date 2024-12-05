package study.bhyunnie.synchronize.facade

import org.springframework.stereotype.Component
import study.bhyunnie.synchronize.repository.RedisLockRepository
import study.bhyunnie.synchronize.service.StockService

@Component
class LettuceLockStockFacade(
	private val redisLockRepository: RedisLockRepository,
	private val stockService: StockService
) {
	fun decrease(id:Long, quantity: Long) {
		while(!redisLockRepository.lock(id)) {
			Thread.sleep(100)
		}

		try {
			stockService.decrease(id = id, quantity = quantity)
		} finally {
			redisLockRepository.unlock(id)
		}
	}
}