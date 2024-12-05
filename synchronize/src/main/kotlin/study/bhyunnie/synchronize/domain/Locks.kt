package study.bhyunnie.synchronize.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
class Locks(
	@Id
	val keyName: String,
)