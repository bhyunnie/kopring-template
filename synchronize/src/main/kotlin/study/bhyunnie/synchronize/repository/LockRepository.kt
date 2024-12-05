package study.bhyunnie.synchronize.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import study.bhyunnie.synchronize.domain.Lock

@Repository
interface LockRepository: JpaRepository<Lock, String> {
	@Query(nativeQuery = true, value = "select get_")
	fun getLock()
}