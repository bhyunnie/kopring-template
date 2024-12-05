package study.bhyunnie.synchronize.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import study.bhyunnie.synchronize.domain.Locks

@Repository
interface LockRepository: JpaRepository<Locks, String> {
	@Query(nativeQuery = true, value = "select get_lock(:key, 3000)")
	fun getLock(key: String)

	@Query(nativeQuery = true, value = "select release_lock(:key)")
	fun releaseLock(key: String)
}