package io.futz

import com.fasterxml.jackson.databind.ObjectMapper
import spark.kotlin.get
import spark.kotlin.ignite
import spark.kotlin.port
import java.lang.System.getenv

/*
https://sparktutorials.github.io/2017/01/28/using-spark-with-kotlin.html
 */
fun main(args: Array<String>) {

  ignite()

  port(8080)

  get("/healthcheck") {
    val health = mutableMapOf(
        Pair("app", "ok")
    )
    val prettyObjectWriter = ObjectMapper().writerWithDefaultPrettyPrinter()
    response.type("application/json")
    JacksonJsonTransformer(prettyObjectWriter).render(health)
  }

  get("/info") {
    response.type("application/json")
    val prettyObjectWriter = ObjectMapper().writerWithDefaultPrettyPrinter()
    JacksonJsonTransformer(prettyObjectWriter).render(getenv())
  }
}
