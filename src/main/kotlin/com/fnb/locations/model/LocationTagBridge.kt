package com.fnb.locations.model

import org.springframework.data.relational.core.mapping.Table

@Table("location_tag_bridge")
data class LocationTagBridge(val locationId: Int, val tagId: Int)