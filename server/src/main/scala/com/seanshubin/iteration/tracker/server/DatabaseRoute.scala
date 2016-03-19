package com.seanshubin.iteration.tracker.server

import com.seanshubin.http.values.core.{Receiver, RequestValue, Route}

class DatabaseRoute(name: String, receiver: Receiver) extends Route(name, receiver) {
  override def accept(request: RequestValue): Boolean = {
    val shouldAccept = request.uri.path.startsWith("/database/")
    shouldAccept
  }
}
