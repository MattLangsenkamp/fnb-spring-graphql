package com.fnb.locations.configuration

import com.expediagroup.graphql.spring.execution.DataLoaderRegistryFactory
import com.fnb.locations.model.LocationTag
import com.fnb.locations.service.impl.LocationTagService
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.CompletableFuture

@Configuration
class DataLoaderConfiguration {
    @Bean
    fun dataLoaderRegistryFactory(locationTagService: LocationTagService): DataLoaderRegistryFactory {
        return object : DataLoaderRegistryFactory {
            override fun generate(): DataLoaderRegistry {
                val registry = DataLoaderRegistry()
                val companyLoader = DataLoader<Int, LocationTag> { ids ->
                    CompletableFuture.supplyAsync { runBlocking { locationTagService.getTagsByLocation(ids[0]) } }
                }
                registry.register("tagLoader", companyLoader)
                return registry
            }
        }
    }
}
 