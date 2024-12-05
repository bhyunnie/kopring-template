package study.bhyunnie.synchronize.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import study.bhyunnie.synchronize.domain.Locks
import study.bhyunnie.synchronize.domain.Stock
import study.bhyunnie.synchronize.facade.NamedLockStockFacade
import study.bhyunnie.synchronize.facade.OptimisticLockStockFacade
import study.bhyunnie.synchronize.repository.LockRepository
import study.bhyunnie.synchronize.repository.StockRepository
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.test.assertEquals

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class StockServiceTest(
	private val stockService: StockService,
	private val stockRepository: StockRepository,
	private val lockRepository: LockRepository,
	private val optimisticLockStockFacade: OptimisticLockStockFacade,
	private val namedLockStockFacade: NamedLockStockFacade
) {
	@BeforeEach
	fun before() {
		stockRepository.saveAndFlush(Stock(productId = 1, quantity = 100))
		lockRepository.saveAndFlush(Locks(keyName ="stock-1"))
	}

	@AfterEach
	fun after() {
		stockRepository.deleteAll()
	}

	@Test
	fun testSynchronized() {
		val threadCount = 100
		val executorService = Executors.newFixedThreadPool(32)
		val latch = CountDownLatch(threadCount)

		for (i in 0 until threadCount) {
			executorService.submit{
				try {
					stockService.synchronizedDecrease(1L, 1L)
				} finally {
					latch.countDown()
				}
			}
		}

		latch.await()

		val stock = stockRepository.findById(1L).orElseThrow {
			RuntimeException("재고를 찾을 수 없습니다")
		}

		assertEquals(0, stock.quantity)
	}

	@Test
	fun testPessimisticLock() {
		val threadCount = 100
		val executorService = Executors.newFixedThreadPool(32)
		val latch = CountDownLatch(threadCount)

		for (i in 0 until threadCount) {
			executorService.submit{
				try {
					stockService.pessimisticLockDecrease(1L, 1L)
				} finally {
					latch.countDown()
				}
			}
		}

		latch.await()

		val stock = stockRepository.findById(1L).orElseThrow {
			RuntimeException("재고를 찾을 수 없습니다")
		}

		assertEquals(0, stock.quantity)
	}

	@Test
	fun testOptimisticLock() {
		val threadCount = 100
		val executorService = Executors.newFixedThreadPool(32)
		val latch = CountDownLatch(threadCount)

		for (i in 0 until threadCount) {
			executorService.submit{
				try {
					optimisticLockStockFacade.decrease(1L, 1L)
				} catch (e: InterruptedException) {
					throw RuntimeException(e)
				} finally {
					latch.countDown()
				}
			}
		}

		latch.await()

		val stock = stockRepository.findById(1L).orElseThrow {
			RuntimeException("재고를 찾을 수 없습니다")
		}

		assertEquals(0, stock.quantity)
	}

	@Test
	fun testNamedLock() {
		val threadCount = 100
		val executorService = Executors.newFixedThreadPool(32)
		val latch = CountDownLatch(threadCount)

		for (i in 0 until threadCount) {
			executorService.submit{
				try {
					namedLockStockFacade.decrease(1L, 1L)
				} finally {
					latch.countDown()
				}
			}
		}

		latch.await()

		val stock = stockRepository.findById(1L).orElseThrow {
			RuntimeException("재고를 찾을 수 없습니다")
		}

		assertEquals(0, stock.quantity)
	}
}