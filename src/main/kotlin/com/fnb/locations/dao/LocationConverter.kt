package com.fnb.locations.dao

import com.fnb.locations.model.Location
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class LocationReadConverter : Converter<Row, Location> {

    override fun convert(source: Row): Location? {
        return Location(
                id = source.get("id").toString().toInt(),
                locationName = source.get("id").toString(),
                friendlyName = source.get("id").toString(),
                description = source.get("id").toString(),
                latitude = source.get("id").toString().toDouble(),
                longitude = source.get("id").toString().toDouble(),
                locationOwner = source.get("location_owner").toString().toInt(),
                needsCleaning = source.get("id").toString().toBoolean(),
                creationDateTime = source.get("creation_date_time").toString(),
        )
    }
}