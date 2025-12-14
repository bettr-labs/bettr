package com.bettr

import com.bettr.adapters.http.bettr.WebFluxConfiguration
import com.bettr.adapters.http.bettr.apiRouter
import com.bettr.adapters.http.bettr.dream.DreamHttpHandler
import com.bettr.adapters.http.bettr.handler.EnrollAccountHttpHandler
import com.bettr.adapters.http.bettr.handler.LoginHttpHandler
import com.bettr.application.EnrollAccountCommandHandler
import com.bettr.application.LoginCommandHandler
import com.bettr.application.dream.CreateDreamsCommandHandler
import com.bettr.inmemory.AccountInMemoryRepository
import com.bettr.inmemory.InMemoryDreamRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter
import java.util.Locale

@SpringBootApplication
@ConfigurationPropertiesScan
class BettrApi

fun rootRouter() =
    coRouter {
        GET("/") { ok().buildAndAwait() }
    }

fun beans() =
    beans {
        bean(::rootRouter)
        bean(::apiRouter)
        bean<WebFluxConfiguration>()

        // Http handlers
        bean<EnrollAccountHttpHandler>()
        bean<LoginHttpHandler>()
        bean<DreamHttpHandler>()

        // Command handlers
        bean<EnrollAccountCommandHandler>()
        bean<LoginCommandHandler>()
        bean<CreateDreamsCommandHandler>()

        // InMemory Repositories TODO - change to r2dbc
        bean<AccountInMemoryRepository>()
        bean<InMemoryDreamRepository>()
    }

fun main(args: Array<String>) {
    Locale.setDefault(Locale.forLanguageTag("pt-BR"))
    runApplication<BettrApi>(*args) {
        addInitializers(
            ApplicationContextInitializer<GenericApplicationContext> { context ->
                beans().initialize(context)
            }
        )
    }
}
