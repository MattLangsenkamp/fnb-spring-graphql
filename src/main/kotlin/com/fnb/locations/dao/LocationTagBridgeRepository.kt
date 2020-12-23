package com.fnb.locations.dao

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationTagBridgeRepository : CoroutineCrudRepository<Pair<Int, Int>, Pair<Int, Int>>