package study.bhyunnie.synchronize.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import study.bhyunnie.synchronize.service.StockService

@RestController
class StockController(
	private val stockService: StockService
) {
	@GetMapping("/test")
	fun stockTest() {
		stockService.pessimisticLockDecrease(1L,1L)
	}
}