package com.bettr.adapters.http.bettr

import com.bettr.adapters.http.bettr.dream.DreamHttpHandler
import com.bettr.adapters.http.bettr.handler.AccountHttpHandler
import com.bettr.adapters.http.bettr.handler.EnrollAccountHttpHandler
import com.bettr.adapters.http.bettr.handler.LoginHttpHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

fun apiRouter(
    enrollAccountHttpHandler: EnrollAccountHttpHandler,
    loginHttpHandler: LoginHttpHandler,
    dreamHttpHandler: DreamHttpHandler,
    accountHttpHandler: AccountHttpHandler
) = coRouter {
    accept(APPLICATION_JSON).nest {
        POST("/accounts") { req ->
            enrollAccountHttpHandler.execute(req)
        }
        GET("/accounts/{accountId}") { req ->
            accountHttpHandler.getAccount(req)
        }
        PATCH("/accounts/{accountId}") { req ->
            accountHttpHandler.updateAccount(req)
        }
        PATCH("/accounts/{accountId}/deactivate") { req ->
            accountHttpHandler.deactivateAccount(req)
        }
        POST("/login") { req ->
            loginHttpHandler.execute(req)
        }
        GET("/dreams-types") { req ->
            dreamHttpHandler.getDreamTypes(req)
        }
        POST("/accounts/{accountId}/dreams") { req ->
            dreamHttpHandler.createDreams(req)
        }
        PATCH("/accounts/{accountId}/dreams/{dreamId}") { req ->
            dreamHttpHandler.updateDream(req)
        }
        GET("/accounts/{accountId}/dreams") { req ->
            dreamHttpHandler.getDreams(req)
        }
        GET("/accounts/{accountId}/dreams/{dreamId}") { req ->
            dreamHttpHandler.getDreams(req)
        }
    }
}
