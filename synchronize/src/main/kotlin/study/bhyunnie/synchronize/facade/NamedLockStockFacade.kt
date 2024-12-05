package study.bhyunnie.synchronize.facade

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import study.bhyunnie.synchronize.repository.LockRepository
import study.bhyunnie.synchronize.service.StockService

@Component
class NamedLockStockFacade(
    private val stockService: StockService,
    private val lockRepository: LockRepository
) {
    @Transactional
    fun decrease(id:Long, quantity: Long) {
        val key = "stock-${id}"
        try {
            lockRepository.getLock(key = key)
            stockService.namedLockDecrease(id = id, quantity = quantity)
        } finally {
            lockRepository.releaseLock(key = key)
        }
    }
}