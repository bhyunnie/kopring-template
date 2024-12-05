package study.bhyunnie.synchronize.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import study.bhyunnie.synchronize.domain.Stock
import study.bhyunnie.synchronize.repository.StockRepository

@Service

class StockService(
	private val stockRepository: StockRepository
) {
	fun decrease(id:Long, quantity: Long) {
		val stock = stockRepository.findById(id).orElseThrow {
			RuntimeException("재고 정보를 찾을 수 없습니다")
		}
		stock.decrease(quantity = quantity)
		stockRepository.save(stock)
	}

	@Synchronized
	fun synchronizedDecrease(id:Long, quantity:Long) {
		val stock = stockRepository.findById(id).orElseThrow{
			RuntimeException("재고 정보를 찾을 수 없습니다")
		}
		stock.decrease(quantity=quantity)
		stockRepository.save(stock)
	}

	 @Transactional
	fun pessimisticLockDecrease(id:Long, quantity:Long) {
		val stock = stockRepository.findByIdWithPessimisticLock(id).orElseThrow {
			RuntimeException("재고를 찾을 수 없습니다")
		}
		 stock.decrease(quantity=quantity)
		 stockRepository.save(stock)
	}


	@Transactional
	fun optimisticLockDecrease(id:Long, quantity:Long) {
		val stock = stockRepository.findByIdWithOptimisticLock(id).orElseThrow{
			RuntimeException("재고를 찾을 수 없습니다")
		}
		stock.decrease(quantity = quantity)
		stockRepository.save(stock)
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	fun namedLockDecrease(id:Long, quantity:Long) {
		val stock = stockRepository.findById(id).orElseThrow {
			RuntimeException("재고를 찾을 수 없습니다")
		}
		stock.decrease(quantity = quantity)
		stockRepository.save(stock)
	}
}