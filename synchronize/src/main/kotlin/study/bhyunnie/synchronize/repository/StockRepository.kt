package study.bhyunnie.synchronize.repository

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import study.bhyunnie.synchronize.domain.Stock
import java.util.Optional

@Repository
interface StockRepository: JpaRepository<Stock, Long>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select s from Stock s where s.id = :id")
	fun findByIdWithPessimisticLock(id: Long): Optional<Stock>

	@Lock(LockModeType.OPTIMISTIC)
	@Query("select s from Stock s where s.id = :id")
	fun findByIdWithOptimisticLock(id: Long): Optional<Stock>
}