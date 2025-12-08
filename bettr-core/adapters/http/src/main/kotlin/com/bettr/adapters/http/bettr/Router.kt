package com.bettr.adapters.http.bettr

import com.bettr.adapters.http.bettr.handler.EnrollAccountHttpHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

fun apiRouter(
    enrollAccountHttpHandler: EnrollAccountHttpHandler,
) = coRouter {
    accept(APPLICATION_JSON).nest {
        POST("/accounts") { req ->
            enrollAccountHttpHandler.execute(req)
        }
    }
}
