package com.seanshubin.iteration.tracker.server

import java.nio.charset.StandardCharsets

import com.seanshubin.http.values.domain._

class DatabaseReceiver extends Receiver {
  override def receive(request: RequestValue): ResponseValue = {
    if (request.uri.path == "/database/tasks" && request.method == "GET") {
      val statusCode = 200
      val charset = StandardCharsets.UTF_8
      val body =
        """[
          |  "Task A",
          |  "Task B",
          |  "Task C"
          |]
        """.stripMargin.getBytes(charset)
      val contentType = ContentType("application/json", Some(charset.name))
      val headers = Headers(Seq("Access-Control-Allow-Origin" -> "*")).setContentType(contentType)
      val responseValue = ResponseValue(statusCode, body, headers.entries)
      responseValue
    } else {
      val statusCode = 404
      val charset = StandardCharsets.UTF_8
      val body = s"Expected (get, database/tasks), but got (${request.method}, ${request.uri.path})".getBytes(charset)
      val contentType = ContentType("text/plain", Some(charset.name))
      val headers = Headers(Seq("Access-Control-Allow-Origin" -> "*")).setContentType(contentType)
      val responseValue = ResponseValue(statusCode, body, headers.entries)
      responseValue
    }
  }
}
