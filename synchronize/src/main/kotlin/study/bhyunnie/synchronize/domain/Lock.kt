package study.bhyunnie.synchronize.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Lock(
	@Id
	val key: String,
	val valid: Boolean
)