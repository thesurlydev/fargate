package io.futz

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import spark.ResponseTransformer
import java.io.StringWriter

class JacksonJsonTransformer(private val objectWriter: ObjectWriter? = ObjectMapper().writer()) : ResponseTransformer {
  override fun render(model: Any?): String {
    val sw = StringWriter()
    objectWriter!!.writeValue(sw, model)
    return sw.toString()
  }
}