package study.bhyunnie.synchronize.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Version

@Entity
data class Stock(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	val productId: Long,
	var quantity: Long,
	@Version
	var version: Long = 0
) {
	fun decrease(quantity: Long) {
		if (this.quantity - quantity < 0) {
			throw RuntimeException("재고는 0개 미만이 될 수 없습니다")
		}

		this.quantity -= quantity
	}
}