package study.bhyunnie.synchronize.facade

import org.springframework.stereotype.Component
import study.bhyunnie.synchronize.service.StockService

@Component
class OptimisticLockStockFacade(
	private val stockService: StockService
) {
	fun decrease(id:Long, quantity:Long) {
		while(true) {
			try {
				stockService.optimisticLockDecrease(id = id, quantity = quantity)
				break
			} catch (e: Exception) {
				Thread.sleep(50)
			}
		}
	}
}