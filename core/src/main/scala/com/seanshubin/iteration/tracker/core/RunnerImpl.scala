package com.seanshubin.iteration.tracker.core

class RunnerImpl(server: HttpServer) extends Runner {
  override def run(): Unit = {
    server.start()
    server.join()
  }
}
