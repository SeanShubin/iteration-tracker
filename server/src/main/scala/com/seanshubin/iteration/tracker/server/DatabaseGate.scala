package com.seanshubin.iteration.tracker.server

import com.seanshubin.http.values.core.{Receiver, RequestValue, Gate}

class DatabaseGate(name:String, receiver:Receiver) extends Gate(name, receiver) {
  override def accept(request: RequestValue): Boolean = {
    val shouldAccept = request.uriString.startsWith("/database/")
    shouldAccept
  }
}
