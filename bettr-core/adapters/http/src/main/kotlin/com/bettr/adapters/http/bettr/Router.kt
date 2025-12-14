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
        GET("/accounts") { req ->
            accountHttpHandler.getAccount(req)
        }
        PATCH("/accounts") { req ->
            accountHttpHandler.updateAccount(req)
        }
        PATCH("/accounts/deactivate") { req ->
            accountHttpHandler.deactivateAccount(req)
        }
        POST("/login") { req ->
            loginHttpHandler.execute(req)
        }
        GET("/dreams-types") { req ->
            dreamHttpHandler.getDreamTypes(req)
        }
        POST("/dreams") { req ->
            dreamHttpHandler.createDreams(req)
        }
        PATCH("/dreams") { req ->
            dreamHttpHandler.updateDream(req)
        }
        GET("/dreams") { req ->
            dreamHttpHandler.getDreams(req)
        }
    }
}
