package com.bettr

import com.bettr.adapters.http.bettr.WebFluxConfiguration
import com.bettr.adapters.http.bettr.apiRouter
import com.bettr.adapters.http.bettr.dream.DreamHttpHandler
import com.bettr.adapters.http.bettr.bet_types.BetTypesHttpHandler
import com.bettr.adapters.http.bettr.handler.AccountHttpHandler
import com.bettr.adapters.http.bettr.handler.EnrollAccountHttpHandler
import com.bettr.adapters.http.bettr.handler.LoginHttpHandler
import com.bettr.adapters.r2dbc.account.AccountR2dbcRepository
import com.bettr.application.DeactivateAccountCommandHandler
import com.bettr.application.EnrollAccountCommandHandler
import com.bettr.application.GetAccountQueryHandler
import com.bettr.application.LoginCommandHandler
import com.bettr.application.UpdateAccountCommandHandler
import com.bettr.application.dream.CreateDreamsCommandHandler
import com.bettr.application.dream.UpdateDreamCommandHandler
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
        bean<BetTypesHttpHandler>()
        bean<AccountHttpHandler>()

        // Command handlers
        bean<EnrollAccountCommandHandler>()
        bean<LoginCommandHandler>()
        bean<CreateDreamsCommandHandler>()
        bean<UpdateDreamCommandHandler>()
        bean<GetAccountQueryHandler>()
        bean<UpdateAccountCommandHandler>()
        bean<DeactivateAccountCommandHandler>()

        // InMemory Repositories TODO - change to r2dbc
        bean<InMemoryDreamRepository>()

        // R2DBC Repositories
        bean<AccountR2dbcRepository>()
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
