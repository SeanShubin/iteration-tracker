package com.seanshubin.iteration.tracker.core

class ErrorHandlerImpl(emitLine: String => Unit) extends ErrorHandler {
  override def handleConfigurationError(lines: Seq[String]): Unit = {
    lines.foreach(emitLine)
  }
}
